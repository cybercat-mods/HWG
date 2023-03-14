package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;

import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.goal.RangedStrafeAttackGoal;
import mod.azure.hwg.entity.goal.WeaponGoal;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;

public class SpyEntity extends HWGEntity {

	public SpyEntity(EntityType<SpyEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.xpReward = HWGConfig.spy_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})).add(new AnimationController<>(this, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())
					&& !(this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Minigun))
				return event.setAndContinue(RawAnimation.begin().thenLoop("attacking"));
			return PlayState.CONTINUE;
		}));
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new RangedStrafeAttackGoal(this,
				new WeaponGoal(this).setProjectileOriginOffset(0.8, 0.8, 0.8), 0.85D, 5, 30, 15, 15F));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 3);
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.MAX_HEALTH, HWGConfig.spy_health).add(Attributes.ARMOR, 3)
				.add(Attributes.ARMOR_TOUGHNESS, 1D).add(Attributes.ATTACK_DAMAGE, 10D)
				.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	public static boolean canSpawn(EntityType<? extends HWGEntity> type, LevelAccessor world, MobSpawnType spawnReason,
			BlockPos pos, RandomSource random) {
		return world.getBrightness(LightLayer.BLOCK, pos) > 8 && world.getDifficulty() != Difficulty.PEACEFUL ? false
				: checkAnyLightMonsterSpawnRules(type, world, spawnReason, pos, random);
	}

	public static boolean canSpawnIgnoreLightLevel(EntityType<? extends HWGEntity> type, ServerLevelAccessor world,
			MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
		return world.getDifficulty() != Difficulty.PEACEFUL
				&& Monster.checkMonsterSpawnRules(type, world, spawnReason, pos, random);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 1.85F;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
			SpawnGroupData entityData, CompoundTag entityTag) {
		this.setItemSlot(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
		this.setVariant(generateVariants(world.getRandom()));
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
	}

	private ItemStack makeInitialWeapon() {
		List<ItemLike> givenList = Arrays.asList(HWGItems.SPISTOL, HWGItems.SNIPER);
		int randomIndex = random.nextInt(givenList.size());
		ItemLike randomElement = givenList.get(randomIndex);
		return random.nextFloat() <= 0.5 ? new ItemStack(HWGItems.GOLDEN_GUN) : new ItemStack(randomElement);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	@Override
	public int getVariants() {
		return 4;
	}

	public static int generateVariants(RandomSource random) {
		return random.nextInt(500) == 0 ? 3 : random.nextInt(100) < 5 ? 2 : 1;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PILLAGER_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PILLAGER_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.PILLAGER_HURT;
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

}