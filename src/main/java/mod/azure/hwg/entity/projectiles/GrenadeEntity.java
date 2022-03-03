package mod.azure.hwg.entity.projectiles;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.config.HWGConfig.Weapons;
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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrenadeEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final TrackedData<Integer> VARIANT = DataTracker.registerData(GrenadeEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> STATE = DataTracker.registerData(GrenadeEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private AnimationFactory factory = new AnimationFactory(this);
	private static Weapons config = HWGMod.config.weapons;

	public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public GrenadeEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.GRENADE, owner, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	public GrenadeEntity(World world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	public GrenadeEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public GrenadeEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.GRENADE, world);
		this.updatePosition(x, y, z);
	}

	public GrenadeEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
		this.dataTracker.startTracking(STATE, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
		tag.putInt("State", this.getVariant());
		tag.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setVariant(tag.getInt("Variant"));
		this.setVariant(tag.getInt("State"));
		this.ticksInAir = tag.getShort("life");
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 5);
	}

	public void setVariant(int color) {
		this.dataTracker.set(VARIANT, color);
	}

	public int getState() {
		return this.dataTracker.get(STATE);
	}

	public void setState(int color) {
		this.dataTracker.set(STATE, color);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("spin", true));
			return PlayState.CONTINUE;
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("bullet", true));
			return PlayState.CONTINUE;
		}
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GrenadeEntity>(this, "controller", 0, this::predicate));
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
	public void remove(RemovalReason reason) {
		AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(),
				this.getZ());
		if (this.getVariant() == 1)
			areaeffectcloudentity.setParticleType(this.getVariant() == 1 ? ParticleTypes.END_ROD
					: this.getVariant() == 2 ? ParticleTypes.EXPLOSION
							: this.getVariant() == 3 ? ParticleTypes.EXPLOSION
									: this.getVariant() == 4 ? ParticleTypes.LARGE_SMOKE
											: this.getVariant() == 5 ? ParticleTypes.FLASH : ParticleTypes.END_ROD);
		areaeffectcloudentity.setRadius(this.getVariant() == 4 ? 5.0F : 2.0F);
		areaeffectcloudentity.setDuration(this.getVariant() == 4 ? 120 : 2);
		if (this.getVariant() == 4) {
			areaeffectcloudentity.addEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 1));
		}
		areaeffectcloudentity.updatePosition(this.getX(), this.getEyeY(), this.getZ());
		this.world.spawnEntity(areaeffectcloudentity);
		super.remove(reason);
	}

	@Override
	public void applyDamageEffects(LivingEntity attacker, Entity target) {
		if (this.getVariant() == 1) {
			if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
				super.applyDamageEffects(attacker, target);
		} else {
			super.applyDamageEffects(attacker, target);
		}
	}

	@Override
	protected void onHit(LivingEntity target) {
		if (this.getVariant() == 1) {
			if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
				super.onHit(target);
		} else {
			super.onHit(target);
		}
	}

	@Override
	public void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			if (this.getVariant() == 1) {
				this.emp();
			} else if (this.getVariant() == 2) {
				this.frag();
			} else if (this.getVariant() == 3) {
				this.naplam();
			} else if (this.getVariant() == 5) {
				this.stun();
			}
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.ticksInAir = 0;
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
			if (this.getVariant() == 1) {
				this.emp();
			} else if (this.getVariant() == 2) {
				this.frag();
			} else if (this.getVariant() == 3) {
				this.naplam();
			} else if (this.getVariant() == 5) {
				this.stun();
			}
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			if (this.getVariant() == 1) {
				this.emp();
			} else if (this.getVariant() == 2) {
				this.frag();
			} else if (this.getVariant() == 3) {
				this.naplam();
			} else if (this.getVariant() == 5) {
				this.stun();
			}
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	protected void stun() {
		int k = MathHelper.floor(this.getX() - 2 - 1.0D);
		int l = MathHelper.floor(this.getX() + 2 + 1.0D);
		int t = MathHelper.floor(this.getY() - 2 - 1.0D);
		int u = MathHelper.floor(this.getY() + 2 + 1.0D);
		int v = MathHelper.floor(this.getZ() - 2 - 1.0D);
		int w = MathHelper.floor(this.getZ() + 2 + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
		Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d)) / 8);
			if (entity instanceof LivingEntity) {
				if (y <= 1.0D) {
					((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1));
					((LivingEntity) entity)
							.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 1));
				}
			}
		}
	}

	protected void frag() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 2.0F, false,
				config.grenades_breaks == true ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
	}

	protected void naplam() {
		int k = MathHelper.floor(this.getX() - 2 - 1.0D);
		int l = MathHelper.floor(this.getX() + 2 + 1.0D);
		int t = MathHelper.floor(this.getY() - 2 - 1.0D);
		int u = MathHelper.floor(this.getY() + 2 + 1.0D);
		int v = MathHelper.floor(this.getZ() - 2 - 1.0D);
		int w = MathHelper.floor(this.getZ() + 2 + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
		Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d)) / 8);
			if (entity instanceof LivingEntity) {
				if (y <= 1.0D) {
					((LivingEntity) entity).setFireTicks(200);
				}
			}
		}
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, true,
				Explosion.DestructionType.NONE);
	}

	protected void emp() {
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
			double y = (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d)) / 8);
			if (entity instanceof TechnodemonEntity || entity instanceof TechnodemonGreaterEntity) {
				if (y <= 1.0D) {
					entity.damage(DamageSource.arrow(this, this), 10);
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