package mod.azure.hwg.entity.tasks;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGItems;
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
import java.util.function.ToIntFunction;

public class RangedShootingAttack<E extends HWGEntity> extends CustomDelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

    protected ToIntFunction<E> attackIntervalSupplier = entity -> (entity.getItemBySlot(EquipmentSlot.MAINHAND).is(HWGItems.AK47) || entity.getItemBySlot(EquipmentSlot.MAINHAND).is(HWGItems.SMG) || entity.getItemBySlot(EquipmentSlot.MAINHAND).is(HWGItems.TOMMYGUN)) ? 1 : entity.getItemBySlot(EquipmentSlot.MAINHAND).is(HWGItems.FLAMETHROWER)? 3 : 20;

    @Nullable
    protected LivingEntity target = null;

    public RangedShootingAttack(int delayTicks) {
        super(delayTicks);
    }

    public RangedShootingAttack<E> attackInterval(ToIntFunction<E> supplier) {
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

        if (!entity.getSensing().hasLineOfSight(this.target)) return false;
        assert this.target != null;
        return this.target.isAlive() && !entity.isWithinMeleeAttackRange(this.target);
    }

    @Override
    protected void start(E entity) {
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void stop(E entity) {
        this.target = null;
        if (!(entity.getItemBySlot(EquipmentSlot.MAINHAND).is(HWGItems.MINIGUN)))
            entity.triggerAnim("attackController", "idle");
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.applyAsInt(entity));

        if (this.target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(this.target) || entity.isWithinMeleeAttackRange(this.target))
            return;

        entity.lookAt(this.target, 30.0f, 30.0f);
        if (entity.getItemBySlot(EquipmentSlot.MAINHAND).is(HWGItems.MINIGUN))
            for (var j = 0; j < 3; ++j)
                entity.shoot();
        else
            entity.shoot();
        var isInsideWaterBlock = entity.level().isWaterAt(entity.blockPosition());
        Helper.spawnLightSource(entity, isInsideWaterBlock);
    }

}
