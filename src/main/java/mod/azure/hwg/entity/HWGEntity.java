package mod.azure.hwg.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.entity.projectiles.FireballEntity;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import mod.azure.hwg.item.weapons.Assasult1Item;
import mod.azure.hwg.item.weapons.AssasultItem;
import mod.azure.hwg.item.weapons.BalrogItem;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.GPistolItem;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import mod.azure.hwg.item.weapons.LugerItem;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.item.weapons.ShotgunItem;
import mod.azure.hwg.item.weapons.SniperItem;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public abstract class HWGEntity extends Monster implements GeoEntity, NeutralMob, Enemy {

	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(HWGEntity.class, EntityDataSerializers.INT);
	private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(HWGEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(HWGEntity.class, EntityDataSerializers.INT);
	private UUID targetUuid;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	protected HWGEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		getNavigation().setCanFloat(true);
		noCulling = true;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public static boolean canNetherSpawn(EntityType<? extends HWGEntity> type, LevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
		if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if (spawnReason != MobSpawnType.CHUNK_GENERATION && spawnReason != MobSpawnType.NATURAL)
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
		entityData.define(ANGER_TIME, 0);
		entityData.define(STATE, 0);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityTag) {
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return entityData.get(ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int ticks) {
		entityData.set(ANGER_TIME, ticks);
	}

	public int getAttckingState() {
		return entityData.get(STATE);
	}

	public void setAttackingState(int time) {
		entityData.set(STATE, time);
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return targetUuid;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		targetUuid = uuid;
	}

	@Override
	public void startPersistentAngerTimer() {
		setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(random));
	}

	public abstract int getVariants();

	public Projectile getProjectile(Item item) {
		return item instanceof PistolItem || item instanceof LugerItem || item instanceof AssasultItem || item instanceof Assasult1Item || item instanceof GPistolItem || item instanceof SPistolItem || item instanceof SniperItem || item instanceof HellhorseRevolverItem || item instanceof Minigun
				? new BulletEntity(level(), this,
						item instanceof PistolItem ? HWGMod.config.pistol_damage
								: item instanceof LugerItem ? HWGMod.config.luger_damage
										: item instanceof AssasultItem ? HWGMod.config.ak47_damage : item instanceof Assasult1Item ? HWGMod.config.smg_damage : item instanceof GPistolItem ? HWGMod.config.golden_pistol_damage : item instanceof SPistolItem ? HWGMod.config.silenced_pistol_damage : item instanceof HellhorseRevolverItem ? HWGMod.config.hellhorse_damage : item instanceof Minigun ? HWGMod.config.minigun_damage : HWGMod.config.sniper_damage)
				: item instanceof FlamethrowerItem ? new FlameFiring(level(), this) : item instanceof BrimstoneItem ? new BlazeRodEntity(level(), this) : item instanceof BalrogItem ? new FireballEntity(level(), this) : new ShellEntity(level(), this);
	}

	public void shoot() {
		if (!level().isClientSide) {
			final var world = level();
			final var vector3d = getViewVector(1.0F);
			final var bullet = getProjectile(getItemBySlot(EquipmentSlot.MAINHAND).getItem());
			bullet.setPos(this.getX() + vector3d.x * 2, this.getY(0.5), this.getZ() + vector3d.z * 2);
			bullet.shootFromRotation(this, getXRot(), getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
			if (getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GeoItem weapon)
				weapon.triggerAnim(this, GeoItem.getOrAssignId(getItemBySlot(EquipmentSlot.MAINHAND), (ServerLevel) this.level()), "shoot_controller", "firing");
			world.playSound(this, blockPosition(), getDefaultAttackSound(getItemBySlot(EquipmentSlot.MAINHAND).getItem()), SoundSource.HOSTILE, 1.0F, 1.0f);
			world.addFreshEntity(bullet);
		}
	}

	public SoundEvent getDefaultAttackSound(Item item) {
		return item instanceof GPistolItem ? HWGSounds.SPISTOL
				: item instanceof SPistolItem ? HWGSounds.SPISTOL
						: item instanceof PistolItem ? HWGSounds.PISTOL
								: item instanceof LugerItem ? HWGSounds.LUGER
										: item instanceof AssasultItem ? HWGSounds.AK
												: item instanceof Assasult1Item ? HWGSounds.SMG
														: item instanceof ShotgunItem ? HWGSounds.SHOTGUN : item instanceof ShotgunItem ? HWGSounds.SHOTGUN : item instanceof HellhorseRevolverItem ? HWGSounds.REVOLVER : item instanceof FlamethrowerItem ? SoundEvents.FIREWORK_ROCKET_BLAST_FAR : item instanceof BrimstoneItem ? SoundEvents.FIRECHARGE_USE : item instanceof BalrogItem ? SoundEvents.GENERIC_EXPLODE : item instanceof Minigun ? HWGSounds.MINIGUN : HWGSounds.SNIPER;
	}

}