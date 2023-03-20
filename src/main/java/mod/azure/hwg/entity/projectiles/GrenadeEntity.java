package mod.azure.hwg.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class GrenadeEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(GrenadeEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(GrenadeEntity.class,
			EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(GrenadeEntity.class,
			EntityDataSerializers.FLOAT);

	public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public GrenadeEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.GRENADE, owner, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public GrenadeEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	public GrenadeEntity(Level world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public GrenadeEntity(Level world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.GRENADE, world);
		this.absMoveTo(x, y, z);
	}

	public GrenadeEntity(Level world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public GrenadeEntity(Level world, double x, double y, double z) {
		super(ProjectilesEntityRegister.GRENADE, x, y, z, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(FORCED_YAW, 0f);
		this.entityData.define(VARIANT, 0);
		this.entityData.define(STATE, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
		tag.putInt("State", this.getVariant());
		tag.putShort("life", (short) this.ticksInAir);
		tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setVariant(tag.getInt("Variant"));
		this.setVariant(tag.getInt("State"));
		this.ticksInAir = tag.getShort("life");
		entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
	}

	@Override
	public void tick() {
		super.tick();
		if (getOwner()instanceof Player owner)
			setYRot(entityData.get(FORCED_YAW));
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 5);
	}

	public void setVariant(int color) {
		this.entityData.set(VARIANT, color);
	}

	public int getState() {
		return this.entityData.get(STATE);
	}

	public void setState(int color) {
		this.entityData.set(STATE, color);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.entityData.get(STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenLoop("spin"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("bullet"));
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
	public void remove(RemovalReason reason) {
		var areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
		if (this.getVariant() == 1)
			areaeffectcloudentity.setParticle(this.getVariant() == 1 ? ParticleTypes.END_ROD
					: this.getVariant() == 2 ? ParticleTypes.EXPLOSION
							: this.getVariant() == 3 ? ParticleTypes.EXPLOSION
									: this.getVariant() == 4 ? ParticleTypes.LARGE_SMOKE
											: this.getVariant() == 5 ? ParticleTypes.FLASH : ParticleTypes.END_ROD);
		areaeffectcloudentity.setRadius(this.getVariant() == 4 ? 5.0F : 2.0F);
		areaeffectcloudentity.setDuration(this.getVariant() == 4 ? 120 : 2);
		if (this.getVariant() == 4) 
			areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
		areaeffectcloudentity.absMoveTo(this.getX(), this.getEyeY(), this.getZ());
		this.level.addFreshEntity(areaeffectcloudentity);
		super.remove(reason);
	}

	@Override
	public void doEnchantDamageEffects(LivingEntity attacker, Entity target) {
		if (this.getVariant() == 1) 
			if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
				super.doEnchantDamageEffects(attacker, target);
		else 
			super.doEnchantDamageEffects(attacker, target);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity target) {
		if (this.getVariant() == 1) 
			if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
				super.doPostHurtEffects(target);
		else 
			super.doPostHurtEffects(target);
	}

	@Override
	public void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			if (this.getVariant() == 1)
				this.emp();
			else if (this.getVariant() == 2)
				this.frag();
			else if (this.getVariant() == 3)
				this.naplam();
			else if (this.getVariant() == 5)
				this.stun();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float divergence) {
		super.shoot(x, y, z, speed, divergence);
		this.ticksInAir = 0;
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
			if (this.getVariant() == 1)
				this.emp();
			else if (this.getVariant() == 2)
				this.frag();
			else if (this.getVariant() == 3)
				this.naplam();
			else if (this.getVariant() == 5)
				this.stun();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSoundEvent(SoundEvents.GENERIC_EXPLODE);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level.isClientSide) {
			if (this.getVariant() == 1)
				this.emp();
			else if (this.getVariant() == 2)
				this.frag();
			else if (this.getVariant() == 3)
				this.naplam();
			else if (this.getVariant() == 5)
				this.stun();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	protected void stun() {
		var aabb = new AABB(this.blockPosition().above()).inflate(3D, 3D, 3D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e.isAlive() && e instanceof LivingEntity) {
				((LivingEntity) e).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
				((LivingEntity) e).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 1));
			}
		});
	}

	protected void frag() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2.0F, false,
				HWGConfig.grenades_breaks == true ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
	}

	protected void naplam() {
		var aabb = new AABB(this.blockPosition().above()).inflate(3D, 3D, 3D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e.isAlive() && e instanceof LivingEntity) {
				((LivingEntity) e).setRemainingFireTicks(200);
			}
		});
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, true,
				Level.ExplosionInteraction.NONE);
	}

	protected void emp() {
		var aabb = new AABB(this.blockPosition().above()).inflate(8D, 8D, 8D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e.isAlive() && (e instanceof TechnodemonEntity || e instanceof TechnodemonGreaterEntity)) 
				e.hurt(DamageSource.arrow(this, this), 10);
		});
//		this.getCommandSenderWorld().getBlockStatesIfLoaded(aabb).forEach(state -> {
//			if (state.is(Blocks.REDSTONE_WIRE))
//				this.level.destroyBlock(portalEntrancePos, true);
//		});
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(Items.AIR);
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