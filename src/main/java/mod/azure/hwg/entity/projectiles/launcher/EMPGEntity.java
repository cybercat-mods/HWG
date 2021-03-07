package mod.azure.hwg.entity.projectiles.launcher;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EMPGEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;

	public EMPGEntity(EntityType<? extends EMPGEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public EMPGEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.EMP_GRENADE_S, owner, world);
	}

	protected EMPGEntity(EntityType<? extends EMPGEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	public EMPGEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.EMP_GRENADE_S, world);
		this.updatePosition(x, y, z);
	}

	public EMPGEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public EMPGEntity(World world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public EMPGEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public EMPGEntity(World world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	protected EMPGEntity(EntityType<? extends EMPGEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("bullet", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<EMPGEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public void remove() {
		AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(),
				this.getZ());
		areaeffectcloudentity.setParticleType(ParticleTypes.END_ROD);
		areaeffectcloudentity.setRadius(2.0F);
		areaeffectcloudentity.setDuration(2);
		areaeffectcloudentity.updatePosition(this.getX(), this.getEyeY(), this.getZ());
		this.world.spawnEntity(areaeffectcloudentity);
		super.remove();
	}

	@Override
	public void dealDamage(LivingEntity attacker, Entity target) {
		if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
			super.dealDamage(attacker, target);
	}

	@Override
	protected void onHit(LivingEntity target) {
		if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
			super.onHit(target);
	}

	@Override
	public void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.explode();
			this.remove();
		}
	}

	@Override
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.ticksInAir = tag.getShort("life");
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 80) {
			this.explode();
			this.remove();
		}

		setNoGravity(false);
		Vec3d vec3d = this.getVelocity();
		vec3d = this.getVelocity();
		this.setVelocity(vec3d.multiply((double) 0.99F));
		Vec3d vec3d5 = this.getVelocity();
		this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);

	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HWGItems.G_EMP) {
		}
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
		if (!this.world.isClient) {
			this.explode();
			this.remove();
		}
		this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			this.explode();
			this.remove();
		}
	}

	protected void explode() {
		int k = MathHelper.floor(this.getX() - 8 - 1.0D);
		int l = MathHelper.floor(this.getX() + 8 + 1.0D);
		int t = MathHelper.floor(this.getY() - 8 - 1.0D);
		int u = MathHelper.floor(this.getY() + 8 + 1.0D);
		int v = MathHelper.floor(this.getZ() - 8 - 1.0D);
		int w = MathHelper.floor(this.getZ() + 8 + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
		Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (double) (MathHelper.sqrt(entity.squaredDistanceTo(vec3d)) / 8);
			if (entity instanceof TechnodemonEntity || entity instanceof TechnodemonGreaterEntity) {
				if (y <= 1.0D) {
					entity.damage(DamageSource.magic(this, this), 10);
				}
			}
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(HWGItems.G_EMP);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}