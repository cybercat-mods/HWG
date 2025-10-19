package mod.azure.hwg.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

import mod.azure.hwg.util.Cooldown;

public class DelayedAttackGoal extends MeleeAttackGoal {

    private final Runnable attackAnimationRunnable;

    protected final Cooldown attackAnimationCooldown;

    protected boolean ranAttackAnimation;

    public DelayedAttackGoal(
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

        if (
            // If the target is not null
            target != null
                // AND we ran the attack animation.
                && ranAttackAnimation
                // AND the animation cooldown has finished
                && !attackAnimationCooldown.isActive()
                // AND the target is still within melee range
                && this.mob.isWithinMeleeAttackRange(target)
                // AND we still have a line of sight of the target
                && this.mob.getSensing().hasLineOfSight(target)
        ) {
            resetAttackCooldown();

            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);

            this.ranAttackAnimation = false;
        }
    }

    protected boolean isAbleToAttack() {
        if (this.mob.getTarget() == null) {
            return false;
        }

        return this.mob.isWithinMeleeAttackRange(this.mob.getTarget());
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
}
