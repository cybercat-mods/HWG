package mod.azure.hwg.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.util.AzureLibUtil;

public abstract class HWGEntity extends Monster implements GeoEntity, NeutralMob, Enemy {

	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(HWGEntity.class,
			EntityDataSerializers.INT);
	private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(HWGEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(HWGEntity.class,
			EntityDataSerializers.INT);
	private UUID targetUuid;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	protected HWGEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		this.getNavigation().setCanFloat(true);
		this.noCulling = true;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(9, new FloatGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}

	public static boolean canNetherSpawn(EntityType<? extends HWGEntity> type, LevelAccessor serverWorldAccess,
			MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
		if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if ((spawnReason != MobSpawnType.CHUNK_GENERATION && spawnReason != MobSpawnType.NATURAL))
			return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
		return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public void checkDespawn() {
		super.checkDespawn();
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	public void setShooting(boolean attacking) {

	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGER_TIME, 0);
		this.entityData.define(STATE, 0);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
			SpawnGroupData entityData, CompoundTag entityTag) {
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int ticks) {
		this.entityData.set(ANGER_TIME, ticks);
	}

	public int getAttckingState() {
		return this.entityData.get(STATE);
	}

	public void setAttackingState(int time) {
		this.entityData.set(STATE, time);
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.targetUuid;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		this.targetUuid = uuid;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
	}

	public abstract int getVariants();

}