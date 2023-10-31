package mod.azure.hwg.entity.tasks;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.platform.Services;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.item.weapons.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class RangedShootingAttack<E extends HWGEntity> extends CustomDelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

    protected Function<E, Integer> attackIntervalSupplier = entity -> (entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof AssasultItem || entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Assasult1Item || entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Assasult2Item) ? 1 : entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof FlamethrowerItem ? 3 : 20;

    @Nullable
    protected LivingEntity target = null;

    public RangedShootingAttack(int delayTicks) {
        super(delayTicks);
    }

    public RangedShootingAttack<E> attackInterval(Function<E, Integer> supplier) {
        this.attackIntervalSupplier = supplier;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);

        return entity.getSensing().hasLineOfSight(this.target) && this.target.isAlive() && !entity.isWithinMeleeAttackRange(this.target);
    }

    @Override
    protected void start(E entity) {
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void stop(E entity) {
        this.target = null;
        if (!(entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Minigun))
            entity.triggerAnim("attackController", "idle");
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

        if (this.target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(this.target) || entity.isWithinMeleeAttackRange(this.target))
            return;

        entity.lookAt(this.target, 30.0f, 30.0f);
        if (entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Minigun)
            for (var j = 0; j < 3; ++j)
                entity.shoot();
        else
            entity.shoot();
        entity.level().setBlockAndUpdate(entity.blockPosition(), Services.PLATFORM.getTickingLightBlock().defaultBlockState());
    }

}
