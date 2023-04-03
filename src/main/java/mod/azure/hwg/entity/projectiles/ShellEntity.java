package mod.azure.hwg.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ShellEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(ShellEntity.class, EntityDataSerializers.FLOAT);

	public ShellEntity(EntityType<? extends ShellEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public ShellEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.SHELL, owner, world);
	}

	protected ShellEntity(EntityType<? extends ShellEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected ShellEntity(EntityType<? extends ShellEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player)
			this.pickup = AbstractArrow.Pickup.ALLOWED;
	}

	public ShellEntity(Level world, double x, double y, double z) {
		super(ProjectilesEntityRegister.SHELL, x, y, z, world);
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
	public void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40)
			this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (HWGConfig.bullets_disable_iframes_on_players == true || !(living instanceof Player))
			living.invulnerableTime = 0;
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
		++this.ticksInAir;
		if (this.ticksInAir >= 40)
			this.remove(Entity.RemovalReason.DISCARDED);
		if (this.level.isClientSide) {
			double d2 = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			double f2 = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			this.level.addParticle(ParticleTypes.SMOKE, true, d2, this.getY(), f2, 0, 0, 0);
		}
		if (getOwner()instanceof Player owner)
			setYRot(entityData.get(FORCED_YAW));
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
		return SoundEvents.ARMOR_EQUIP_IRON;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide)
			this.remove(Entity.RemovalReason.DISCARDED);
		if (level.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof PointedDripstoneBlock && HWGConfig.bullets_breakdripstone == true)
			level.destroyBlock(blockHitResult.getBlockPos(), true);
		this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		var entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult) entityHitResult).getEntity().is(entity))
			if (!this.level.isClientSide)
				this.remove(Entity.RemovalReason.DISCARDED);
		var entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.arrow(this, this);
		} else {
			damageSource2 = DamageSource.arrow(this, entity2);
			if (entity2 instanceof LivingEntity)
				((LivingEntity) entity2).setLastHurtMob(entity);
		}
		if (entity.hurt(damageSource2, HWGConfig.shotgun_damage)) {
			if (entity instanceof LivingEntity) {
				var livingEntity = (LivingEntity) entity;
				if (!this.level.isClientSide && entity2 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
				}
				this.doPostHurtEffects(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player && entity2 instanceof ServerPlayer && !this.isSilent())
					((ServerPlayer) entity2).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
			}
		} else if (!this.level.isClientSide)
			this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(HWGItems.SHOTGUN_SHELL);
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