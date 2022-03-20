package mod.azure.hwg.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.entity.blockentity.TickingLightEntity;
import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.registry.HWGBlocks;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGSounds;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseFlareEntity extends PersistentProjectileEntity {

	public int life;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private static final TrackedData<Integer> COLOR = DataTracker.registerData(BaseFlareEntity.class,
			TrackedDataHandlerRegistry.INTEGER);

	public BaseFlareEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(ProjectilesEntityRegister.FLARE, world);
	}

	public BaseFlareEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.FLARE, world);
		this.updatePosition(x, y, z);
	}

	public BaseFlareEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public BaseFlareEntity(World world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public BaseFlareEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public BaseFlareEntity(World world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getColor());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setColor(tag.getInt("Variant"));
	}

	public int getColor() {
		return MathHelper.clamp((Integer) this.dataTracker.get(COLOR), 1, 16);
	}

	public void setColor(int color) {
		this.dataTracker.set(COLOR, color);
	}

	@Override
	public void tick() {
		int idleOpt = 100;
		if (getVelocity().lengthSquared() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		if (this.age >= 800 || this.isTouchingWater()) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.life == 0 && !this.isSilent()) {
			this.world.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), HWGSounds.FLAREGUN,
					SoundCategory.AMBIENT, 3.0F, 3.0F);
		}
		setNoGravity(false);
		++this.life;
		Vec3d vec3d = this.getVelocity();
		vec3d = this.getVelocity();
		this.setVelocity(vec3d.multiply((double) 0.99F));
		Vec3d vec3d5 = this.getVelocity();
		this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
		boolean isInsideWaterBlock = world.isWater(getBlockPos());
		spawnLightSource(isInsideWaterBlock);
		if (this.world.isClient) {
			this.world.addParticle(this.getColor() == 16 ? HWGParticles.WHITE_FLARE
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
					true, this.getX(), this.getY() - 0.3D, this.getZ(), this.random.nextGaussian() * 0.05D,
					-this.getVelocity().y * 0.07D, this.random.nextGaussian() * 0.05D);
		}
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
		return HWGSounds.FLAREGUN;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSound(HWGSounds.FLAREGUN);
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
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		return false;
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(world, getBlockPos(), 2);
			if (lightBlockPos == null)
				return;
			world.setBlockState(lightBlockPos, HWGBlocks.TICKING_LIGHT_BLOCK.getDefaultState());
		} else if (checkDistance(lightBlockPos, getBlockPos(), 2)) {
			BlockEntity blockEntity = world.getBlockEntity(lightBlockPos);
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

	private BlockPos findFreeSpace(World world, BlockPos blockPos, int maxDistance) {
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
					BlockPos offsetPos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(HWGBlocks.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	@Override
	protected ItemStack asItemStack() {
		return new ItemStack(Items.AIR);
	}

}
