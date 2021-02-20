package mod.azure.hwg.entity.projectiles.flare;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.HWGParticles;
import mod.azure.hwg.util.ProjectilesEntityRegister;
import mod.azure.hwg.util.packet.EntityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class YellowFlareEntity extends PersistentProjectileEntity {

	public int life;

	public YellowFlareEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.YELLOW_FLARE, world);
		this.updatePosition(x, y, z);
	}

	public YellowFlareEntity(EntityType<? extends YellowFlareEntity> entityType, World world) {
		super(entityType, world);
	}

	public YellowFlareEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public YellowFlareEntity(World world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public YellowFlareEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public YellowFlareEntity(World world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 800) {
			this.remove();
		}
		if (this.life == 0 && !this.isSilent()) {
			this.world.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(),
					SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 3.0F, 3.0F);
		}
		setNoGravity(false);

		++this.life;
		if (this.world.isClient) {
			this.world.addParticle(HWGParticles.YELLOW_FLARE, true, this.getX(), this.getY() - 0.3D, this.getZ(),
					this.random.nextGaussian() * 0.05D, -this.getVelocity().y * 0.5D,
					this.random.nextGaussian() * 0.05D);
		}

		Vec3d vec3d = this.getVelocity();
		vec3d = this.getVelocity();
		this.setVelocity(vec3d.multiply((double) 0.99F));
		Vec3d vec3d5 = this.getVelocity();
		this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
	}

	@Override
	public void handleStatus(byte status) {
		super.handleStatus(status);
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.ENTITY_GENERIC_EXPLODE;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			this.remove();
		}
		this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected ItemStack asItemStack() {
		return new ItemStack(HWGItems.YELLOW_FLARE);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}