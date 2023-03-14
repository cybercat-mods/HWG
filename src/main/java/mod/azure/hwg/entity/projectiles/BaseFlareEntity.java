package mod.azure.hwg.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGSounds;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class BaseFlareEntity extends AbstractArrow {

	public int life;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(BaseFlareEntity.class,
			EntityDataSerializers.INT);

	public BaseFlareEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
		super(ProjectilesEntityRegister.FLARE, world);
	}

	public BaseFlareEntity(Level world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.FLARE, world);
		this.absMoveTo(x, y, z);
	}

	public BaseFlareEntity(Level world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public BaseFlareEntity(Level world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public BaseFlareEntity(Level world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public BaseFlareEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
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
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(COLOR, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getColor());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setColor(tag.getInt("Variant"));
	}

	public int getColor() {
		return Mth.clamp((Integer) this.entityData.get(COLOR), 1, 16);
	}

	public void setColor(int color) {
		this.entityData.set(COLOR, color);
	}

	@Override
	public void tick() {
		var idleOpt = 100;
		if (getDeltaMovement().lengthSqr() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		if (this.tickCount >= 800 || this.isInWater())
			this.remove(Entity.RemovalReason.DISCARDED);
		if (this.life == 0 && !this.isSilent())
			this.level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), HWGSounds.FLAREGUN_SHOOT,
					SoundSource.PLAYERS, 6.0F, 1.0F);
		setNoGravity(false);
		++this.life;
		var vec3d = this.getDeltaMovement();
		vec3d = this.getDeltaMovement();
		this.setDeltaMovement(vec3d.scale((double) 0.99F));
		if (this.tickCount > 25)
			this.setDeltaMovement(0.0, -0.1, 0.0);
		var isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		if (this.level.isClientSide) {
			this.level.addParticle(this.getColor() == 16 ? HWGParticles.WHITE_FLARE
					: this.getColor() == 15 ? HWGParticles.YELLOW_FLARE
							: this.getColor() == 14 ? HWGParticles.RED_FLARE
									: this.getColor() == 13 ? HWGParticles.PURPLE_FLARE
											: this.getColor() == 12 ? HWGParticles.PINK_FLARE
													: this.getColor() == 11 ? HWGParticles.ORANGE_FLARE
															: this.getColor() == 10 ? HWGParticles.MAGENTA_FLARE
																	: this.getColor() == 9 ? HWGParticles.LIME_FLARE
																			: this.getColor() == 8
																					? HWGParticles.LIGHTGRAY_FLARE
																					: this.getColor() == 7
																							? HWGParticles.LIGHTBLUE_FLARE
																							: this.getColor() == 6
																									? HWGParticles.GREEN_FLARE
																									: this.getColor() == 5
																											? HWGParticles.GRAY_FLARE
																											: this.getColor() == 4
																													? HWGParticles.CYAN_FLARE
																													: this.getColor() == 3
																															? HWGParticles.BROWN_FLARE
																															: this.getColor() == 2
																																	? HWGParticles.BLUE_FLARE
																																	: this.getColor() == 1
																																			? HWGParticles.BLACK_FLARE
																																			: HWGParticles.WHITE_FLARE,
					true, this.getX(), this.getY() - 0.3D, this.getZ(), 0, -this.getDeltaMovement().y * 0.17D, 0);
		}
	}

	@Override
	public final void startFalling() {
		this.inGround = false;
		this.setDeltaMovement(0.0, -0.09, 0.0);
		this.life = 0;
	}

	@Override
	public void handleEntityEvent(byte status) {
		super.handleEntityEvent(status);
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return HWGSounds.FLAREGUN;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level.isClientSide)
			this.remove(Entity.RemovalReason.DISCARDED);
		this.setSoundEvent(HWGSounds.FLAREGUN);
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	@Override
	protected boolean tryPickup(Player player) {
		return false;
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, AzureLibMod.TICKING_LIGHT_BLOCK.defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			var blockEntity = level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity)
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			else
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

		var offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					var offsetPos = blockPos.offset(x, y, z);
					var state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(AzureLibMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.AIR);
	}

}
