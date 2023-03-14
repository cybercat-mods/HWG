package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;

public class TechnodemonEntity extends HWGEntity {

	public TechnodemonEntity(EntityType<TechnodemonEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.xpReward = HWGConfig.lesser_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (event.isMoving() && !this.isSwimming()) 
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
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
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 4);
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 4)
				.add(Attributes.MAX_HEALTH,
						HWGConfig.lesser_health)
				.add(Attributes.ATTACK_DAMAGE, 10D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 2.25F;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
			SpawnGroupData entityData, CompoundTag entityTag) {
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 5);
		this.setVariant(var);
		this.setItemSlot(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
	}

	private ItemStack makeInitialWeapon() {
		Random rand = new Random();
		List<ItemLike> givenList = Arrays.asList(HWGItems.HELLHORSE, HWGItems.FLAMETHROWER, HWGItems.BRIMSTONE);
		int randomIndex = rand.nextInt(givenList.size());
		ItemLike randomElement = givenList.get(randomIndex);
		return new ItemStack(randomElement);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	@Override
	public int getVariants() {
		return 4;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ZOMBIE_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

}