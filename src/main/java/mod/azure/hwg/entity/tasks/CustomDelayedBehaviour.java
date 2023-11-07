package mod.azure.hwg.entity.tasks;

import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.item.weapons.Minigun;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.function.Consumer;

public abstract class CustomDelayedBehaviour<E extends HWGEntity> extends ExtendedBehaviour<E> {
    protected final int delayTime;
    protected long delayFinishedAt = 0;
    protected Consumer<E> delayedCallback = entity -> {
    };

    protected CustomDelayedBehaviour(int delayTicks) {
        this.delayTime = delayTicks;

        runFor(entity -> Math.max(delayTicks, 60));
    }

    public final CustomDelayedBehaviour<E> whenActivating(Consumer<E> callback) {
        this.delayedCallback = callback;

        return this;
    }

    @Override
    protected final void start(ServerLevel level, E entity, long gameTime) {
        if (this.delayTime > 0) {
            this.delayFinishedAt = gameTime + this.delayTime;
            super.start(level, entity, gameTime);
        } else {
            super.start(level, entity, gameTime);
            doDelayedAction(entity);
        }
        if (entity.getTarget() != null) {
            if (!(entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Minigun) && !entity.isWithinMeleeAttackRange(entity.getTarget()))
                entity.triggerAnim("attackController", "ranged");
            entity.lookAt(entity.getTarget(), 30.0f, 30.0f);
        }
    }

    @Override
    protected final void stop(ServerLevel level, E entity, long gameTime) {
        super.stop(level, entity, gameTime);

        this.delayFinishedAt = 0;
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return this.delayFinishedAt >= entity.level().getGameTime();
    }

    @Override
    protected final void tick(ServerLevel level, E entity, long gameTime) {
        super.tick(level, entity, gameTime);

        if (this.delayFinishedAt <= gameTime) {
            doDelayedAction(entity);
            this.delayedCallback.accept(entity);
        }
    }

    protected void doDelayedAction(E entity) {
    }
}
