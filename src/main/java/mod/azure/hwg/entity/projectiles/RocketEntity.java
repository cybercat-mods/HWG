package mod.azure.hwg.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class RocketEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.FLOAT);

	public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public RocketEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.ROCKETS, owner, world);
	}

	protected RocketEntity(EntityType<? extends RocketEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected RocketEntity(EntityType<? extends RocketEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player)
			this.pickup = AbstractArrow.Pickup.ALLOWED;
	}

	public RocketEntity(Level world, double x, double y, double z) {
		super(ProjectilesEntityRegister.ROCKETS, x, y, z, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FORCED_YAW, 0f);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putShort("life", (short) this.ticksInAir);
		tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.ticksInAir = tag.getShort("life");
		entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
	}

	@Override
	public void tick() {
		super.tick();
		var bl = this.isNoPhysics();
		var vec3d = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			double f = vec3d.horizontalDistance();
			this.setYRot((float) (Mth.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
			this.setXRot((float) (Mth.atan2(vec3d.y, f) * 57.2957763671875D));
			this.yRotO = this.getYRot();
			this.xRotO = this.getXRot();
		}
		if (getOwner()instanceof Player owner)
			setYRot(entityData.get(FORCED_YAW));
		++this.ticksInAir;
		if (this.tickCount >= 80) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.inAir && !bl) {
			this.tickDespawn();
			++this.timeInAir;
		} else {
			this.timeInAir = 0;
			var vec3d3 = this.position();
			var vector3d3 = vec3d3.add(vec3d);
			HitResult hitResult = this.level.clip(new ClipContext(vec3d3, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (((HitResult) hitResult).getType() != HitResult.Type.MISS)
				vector3d3 = ((HitResult) hitResult).getLocation();
			while (!this.isRemoved()) {
				var entityHitResult = this.findHitEntity(vec3d3, vector3d3);
				if (entityHitResult != null) {
					hitResult = entityHitResult;
				}
				if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
					var entity = ((EntityHitResult) hitResult).getEntity();
					var entity2 = this.getOwner();
					if (entity instanceof Player && entity2 instanceof Player && !((Player) entity2).canHarmPlayer((Player) entity)) {
						hitResult = null;
						entityHitResult = null;
					}
				}
				if (hitResult != null && !bl) {
					this.onHit((HitResult) hitResult);
					this.hasImpulse = true;
				}
				if (entityHitResult == null || this.getPierceLevel() <= 0)
					break;
				hitResult = null;
			}
			vec3d = this.getDeltaMovement();
			double d = vec3d.x;
			double e = vec3d.y;
			double g = vec3d.z;
			double h = this.getX() + d;
			double j = this.getY() + e;
			double k = this.getZ() + g;
			double l = vec3d.horizontalDistance();
			if (bl)
				this.setYRot((float) (Mth.atan2(-e, -g) * 57.2957763671875D));
			else
				this.setYRot((float) (Mth.atan2(e, g) * 57.2957763671875D));
			this.setXRot((float) (Mth.atan2(e, l) * 57.2957763671875D));
			this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
			this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
			var m = 0.99F;
			this.setDeltaMovement(vec3d.scale((double) m));
			if (!this.isNoGravity() && !bl)
				this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y - 0.05000000074505806D, this.getDeltaMovement().z);
			this.absMoveTo(h, j, k);
			this.checkInsideBlocks();
		}
	}

	@Override
	public boolean isNoGravity() {
		if (this.isUnderWater())
			return false;
		else
			return true;
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.GENERIC_EXPLODE;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSoundEvent(SoundEvents.GENERIC_EXPLODE);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level.isClientSide) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2.0F, false, HWGMod.config.rocket_breaks == true ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(HWGItems.ROCKET);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	public void setProperties(float pitch, float yaw, float roll, float modifierZ) {
		var f = 0.017453292F;
		var x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
		var y = -Mth.sin((pitch + roll) * f);
		var z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
		this.shoot(x, y, z, modifierZ, 0);
	}

}