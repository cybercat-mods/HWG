package mod.azure.hwg.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.util.packet.EntityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.IntRange;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public abstract class HWGEntity extends PathAwareEntity implements Angerable, RangedAttackMob {

	private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(HWGEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(HWGEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private UUID targetUuid;

	protected HWGEntity(EntityType<? extends PathAwareEntity> type, World worldIn) {
		super(type, worldIn);
		this.getNavigation().setCanSwim(true);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected boolean isDisallowedInPeaceful() {
		return true;
	}
	
	@Override
	public void setUuid(UUID uuid) {
		super.setUuid(UUID.randomUUID());
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	public void setShooting(boolean attacking) {

	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ANGER_TIME, 0);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			EntityData entityData, CompoundTag entityTag) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public int getAngerTime() {
		return this.dataTracker.get(ANGER_TIME);
	}

	@Override
	public void setAngerTime(int ticks) {
		this.dataTracker.set(ANGER_TIME, ticks);
	}

	@Override
	public UUID getAngryAt() {
		return this.targetUuid;
	}

	@Override
	public void setAngryAt(@Nullable UUID uuid) {
		this.targetUuid = uuid;
	}

	@Override
	public void chooseRandomAngerTime() {
		this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
	}

	public abstract int getVariants();

}