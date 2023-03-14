package mod.azure.hwg.entity.projectiles;

import java.util.List;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FireballEntity extends AbstractArrow {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private LivingEntity shooter;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(FireballEntity.class,
			EntityDataSerializers.FLOAT);

	public FireballEntity(EntityType<? extends FireballEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public FireballEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.FIREBALL, owner, world);
		this.shooter = owner;
	}

	protected FireballEntity(EntityType<? extends FireballEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected FireballEntity(EntityType<? extends FireballEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.ALLOWED;
		}

	}

	public FireballEntity(Level world, double x, double y, double z) {
		super(ProjectilesEntityRegister.FIREBALL, x, y, z, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	@Override
	public boolean displayFireAnimation() {
		return true;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (HWGConfig.bullets_disable_iframes_on_players == true || !(living instanceof Player)) {
			living.invulnerableTime = 0;
			living.setDeltaMovement(0, 0, 0);
		}
	}

	@Override
	public void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
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
		int idleOpt = 100;
		if (getDeltaMovement().lengthSqr() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (getOwner()instanceof Player owner)
			setYRot(entityData.get(FORCED_YAW));
		boolean isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		float q = 4.0F;
		int k2 = Mth.floor(this.getX() - (double) q - 1.0D);
		int l2 = Mth.floor(this.getX() + (double) q + 1.0D);
		int t = Mth.floor(this.getY() - (double) q - 1.0D);
		int u = Mth.floor(this.getY() + (double) q + 1.0D);
		int v = Mth.floor(this.getZ() - (double) q - 1.0D);
		int w = Mth.floor(this.getZ() + (double) q + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AABB((double) k2, (double) t, (double) v, (double) l2, (double) u, (double) w));
		Vec3 vec3d2 = new Vec3(this.getX(), this.getY(), this.getZ());
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (Mth.sqrt((float) entity.distanceToSqr(vec3d2)) / q);
			if (y <= 1.0D) {
				if (this.level.isClientSide) {
					double d2 = this.getX()
							+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
					double e2 = this.getY() + 0.05D + this.random.nextDouble();
					double f2 = this.getZ()
							+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
					this.level.addParticle(ParticleTypes.FLAME, true, d2, e2, f2, 0, 0, 0);
					this.level.addParticle(HWGParticles.BRIM_ORANGE, true, d2, e2, f2, 0, 0, 0);
					this.level.addParticle(HWGParticles.BRIM_RED, true, d2, e2, f2, 0, 0, 0);
				}
			}
		}

		List<Entity> list1 = this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(1D, 5D, 1D));
		for (int x = 0; x < list1.size(); ++x) {
			Entity entity = (Entity) list1.get(x);
			double y = (double) (Mth.sqrt(entity.distanceTo(this)));
			if (y <= 1.0D) {
				if (entity.isAlive()) {
					entity.hurt(DamageSource.arrow(this, this.shooter), 3);
					if (!(entity instanceof FireballEntity && this.getOwner() instanceof Player)) {
						entity.setRemainingFireTicks(90);
					}
				}
			}
		}
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, AzureLibMod.TICKING_LIGHT_BLOCK.defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			BlockEntity blockEntity = level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.offset(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(AzureLibMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HWGItems.BULLETS) {
		}
	}

	@Override
	public boolean isNoGravity() {
		if (this.isUnderWater()) {
			return false;
		} else {
			return true;
		}
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.FIRE_AMBIENT;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide) {
			Entity entity = this.getOwner();
			if (entity == null || !(entity instanceof Mob)
					|| this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
				if (this.level.isEmptyBlock(blockPos)) {
					this.level.setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level, blockPos));
				}
			}
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSoundEvent(SoundEvents.FIRE_AMBIENT);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		Entity entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.arrow(this, this);
		} else {
			damageSource2 = DamageSource.arrow(this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity) entity2).setLastHurtMob(entity);
			}
		}
		if (entity.hurt(damageSource2, HWGConfig.brimstone_damage)) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				if (!this.level.isClientSide && entity2 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
				}
				this.doPostHurtEffects(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player
						&& entity2 instanceof ServerPlayer && !this.isSilent()) {
					((ServerPlayer) entity2).connection.send(
							new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}
			}
		} else {
			if (!this.level.isClientSide) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(HWGItems.BULLETS);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	public void setProperties(float pitch, float yaw, float roll, float modifierZ) {
		float f = 0.017453292F;
		float x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
		float y = -Mth.sin((pitch + roll) * f);
		float z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
		this.shoot(x, y, z, modifierZ, 0);
	}

}