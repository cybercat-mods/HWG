package mod.azure.hwg.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.util.Cooldown;

public class DelayedRangedAttackGoal extends MeleeAttackGoal {

    private final Runnable attackAnimationRunnable;

    protected final Cooldown attackAnimationCooldown;

    protected boolean ranAttackAnimation;

    public DelayedRangedAttackGoal(
        PathfinderMob mob,
        double speedModifier,
        boolean followingTargetEvenIfNotSeen,
        int delayTicksBeforeAttack,
        Runnable attackAnimationRunnable
    ) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.attackAnimationCooldown = Cooldown.withCooldownTimeInTicks(
            "attackAnimationCooldownInTicks",
            delayTicksBeforeAttack
        );
        this.attackAnimationRunnable = attackAnimationRunnable;

        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public void stop() {
        var livingentity = this.mob.getTarget();

        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget(null);
        }

        this.mob.setAggressive(false);
    }

    @Override
    public boolean canUse() {
        return isAbleToAttack() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return isAbleToAttack() && super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
        attackAnimationCooldown.tick();
        var target = mob.getTarget();

        if (target != null) {
            mob.lookAt(target, 30.0F, 30.0F);
        }

        if (
            // If the target is not null
            target != null
                // AND we ran the attack animation.
                && ranAttackAnimation
                // AND the animation cooldown has finished
                && !attackAnimationCooldown.isActive()
                // AND the target is not within melee range
                && !mob.isWithinMeleeAttackRange(target)
                // AND we still have a line of sight of the target
                && mob.getSensing().hasLineOfSight(target)
        ) {
            resetAttackCooldown();
            mob.swing(InteractionHand.MAIN_HAND);
            if (mob instanceof HWGEntity entity) {
                entity.shoot();
            }
            this.ranAttackAnimation = false;
        }
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity target) {
        if (!ranAttackAnimation && canPerformAttack(target)) {
            // Play the animation.
            attackAnimationRunnable.run();
            this.ranAttackAnimation = true;
            // Reset the cooldown.
            attackAnimationCooldown.reset();
        }
    }

    @Override
    protected boolean canPerformAttack(@NotNull LivingEntity entity) {
        return this.isTimeToAttack() && !this.mob.isWithinMeleeAttackRange(entity) && this.mob.getSensing()
            .hasLineOfSight(entity);
    }

    protected boolean isAbleToAttack() {
        if (this.mob.getTarget() == null) {
            return false;
        }

        return !this.mob.isWithinMeleeAttackRange(this.mob.getTarget());
    }
}
