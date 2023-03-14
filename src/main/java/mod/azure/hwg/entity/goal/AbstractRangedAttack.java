package mod.azure.hwg.entity.goal;

import mod.azure.hwg.entity.HWGEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractRangedAttack implements IRangedAttack {

	public HWGEntity parentEntity;
	public double xOffSetModifier = 2;
	public double entityHeightFraction = 0.5;
	public double zOffSetModifier = 2;
	public float damage = 1;
	public double accuracy = 0.95;

	public AbstractRangedAttack(HWGEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public AbstractRangedAttack(HWGEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
			double zOffSetModifier, float damage) {
		this.parentEntity = parentEntity;
		this.xOffSetModifier = xOffSetModifier;
		this.entityHeightFraction = entityHeightFraction;
		this.zOffSetModifier = zOffSetModifier;
		this.damage = damage;
	}

	public AbstractRangedAttack setProjectileOriginOffset(double x, double entityHeightFraction, double z) {
		xOffSetModifier = x;
		this.entityHeightFraction = entityHeightFraction;
		zOffSetModifier = z;
		return this;
	}

	public AbstractRangedAttack setDamage(float damage) {
		this.damage = damage;
		return this;
	}

	private AttackSound sound;

	public AbstractRangedAttack setSound(AttackSound sound) {
		this.sound = sound;
		return this;
	}

	public AbstractRangedAttack setSound(SoundEvent sound, float volume, float pitch) {
		this.sound = new AttackSound(sound, volume, pitch);
		return this;
	}

	public AbstractRangedAttack setAccuracy(double accuracy) {
		this.accuracy = accuracy;
		return this;
	}

	public double rollAccuracy(double directional) {
		return directional + (1.0D - accuracy) * directional * this.parentEntity.getRandom().nextGaussian();
	}

	public void shoot() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		Level world = this.parentEntity.getCommandSenderWorld();
		Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getY(0.5D) - (this.parentEntity.getY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		Projectile projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot(), 0.0F, 0.25F * 3.0F, 1.0F);
		world.addFreshEntity(projectile);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

	public void shoot1() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		Level world = this.parentEntity.getCommandSenderWorld();
		Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getY(0.5D) - (this.parentEntity.getY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		Projectile projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot(), 0.0F, 0.25F * 3.0F, 1.0F);
		projectile.moveTo(parentEntity.getX(), parentEntity.getY(0.85), parentEntity.getZ(), 0,
				0);
		projectile.isNoGravity();

		Projectile projectile1 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile1.shootFromRotation(parentEntity, parentEntity.getXRot() + 2, parentEntity.getYRot(), 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile1.moveTo(parentEntity.getX(), parentEntity.getY(0.85), parentEntity.getZ(), 0,
				0);
		projectile1.isNoGravity();

		Projectile projectile2 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile2.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot() + 2, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile2.moveTo(parentEntity.getX(), parentEntity.getY(0.85), parentEntity.getZ(), 0,
				0);
		projectile2.isNoGravity();

		Projectile projectile3 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile3.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot() - 2, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile3.moveTo(parentEntity.getX(), parentEntity.getY(0.85), parentEntity.getZ(), 0,
				0);
		projectile3.isNoGravity();

		world.addFreshEntity(projectile);
		world.addFreshEntity(projectile1);
		world.addFreshEntity(projectile2);
		world.addFreshEntity(projectile3);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

	public void shoot2() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		Level world = this.parentEntity.getCommandSenderWorld();
		Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getY(0.5D) - (this.parentEntity.getY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		Projectile projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot(), 0.0F, 0.25F * 3.0F, 1.0F);
		projectile.moveTo(parentEntity.getX(), parentEntity.getY(0.5), parentEntity.getZ(), 0, 0);
		projectile.setRemainingFireTicks(100);
		projectile.absMoveTo(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		Projectile projectile1 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile1.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot() + 5, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile1.moveTo(parentEntity.getX(), parentEntity.getY(0.5), parentEntity.getZ(), 0,
				0);
		projectile1.setRemainingFireTicks(100);
		projectile1.absMoveTo(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		Projectile projectile2 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile2.shootFromRotation(parentEntity, parentEntity.getXRot(), parentEntity.getYRot() - 5, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile2.moveTo(parentEntity.getX(), parentEntity.getY(0.5), parentEntity.getZ(), 0,
				0);
		projectile2.setRemainingFireTicks(100);
		projectile2.absMoveTo(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		world.addFreshEntity(projectile);
		world.addFreshEntity(projectile1);
		world.addFreshEntity(projectile2);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

}
