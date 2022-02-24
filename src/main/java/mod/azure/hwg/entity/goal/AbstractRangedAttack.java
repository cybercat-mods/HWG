package mod.azure.hwg.entity.goal;

import mod.azure.hwg.entity.HWGEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
		World world = this.parentEntity.getEntityWorld();
		Vec3d vector3d = this.parentEntity.getRotationVec(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getBodyY(0.5D) - (this.parentEntity.getBodyY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw(), 0.0F, 0.25F * 3.0F, 1.0F);
		world.spawnEntity(projectile);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

	public void shoot1() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		World world = this.parentEntity.getEntityWorld();
		Vec3d vector3d = this.parentEntity.getRotationVec(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getBodyY(0.5D) - (this.parentEntity.getBodyY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw(), 0.0F, 0.25F * 3.0F, 1.0F);
		projectile.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.85), parentEntity.getZ(), 0,
				0);
		projectile.hasNoGravity();

		ProjectileEntity projectile1 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile1.setVelocity(parentEntity, parentEntity.getPitch() + 2, parentEntity.getYaw(), 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile1.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.85), parentEntity.getZ(), 0,
				0);
		projectile1.hasNoGravity();

		ProjectileEntity projectile2 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile2.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw() + 2, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile2.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.85), parentEntity.getZ(), 0,
				0);
		projectile2.hasNoGravity();

		ProjectileEntity projectile3 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile3.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw() - 2, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile3.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.85), parentEntity.getZ(), 0,
				0);
		projectile3.hasNoGravity();

		world.spawnEntity(projectile);
		world.spawnEntity(projectile1);
		world.spawnEntity(projectile2);
		world.spawnEntity(projectile3);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

	public void shoot2() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		World world = this.parentEntity.getEntityWorld();
		Vec3d vector3d = this.parentEntity.getRotationVec(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getBodyY(0.5D) - (this.parentEntity.getBodyY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw(), 0.0F, 0.25F * 3.0F, 1.0F);
		projectile.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.5), parentEntity.getZ(), 0, 0);
		projectile.setFireTicks(100);
		projectile.updatePosition(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getBodyY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile1 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile1.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw() + 5, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile1.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.5), parentEntity.getZ(), 0,
				0);
		projectile1.setFireTicks(100);
		projectile1.updatePosition(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getBodyY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile2 = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile2.setVelocity(parentEntity, parentEntity.getPitch(), parentEntity.getYaw() - 5, 0.0F, 0.25F * 3.0F,
				1.0F);
		projectile2.refreshPositionAndAngles(parentEntity.getX(), parentEntity.getBodyY(0.5), parentEntity.getZ(), 0,
				0);
		projectile2.setFireTicks(100);
		projectile2.updatePosition(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getBodyY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		world.spawnEntity(projectile);
		world.spawnEntity(projectile1);
		world.spawnEntity(projectile2);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

}
