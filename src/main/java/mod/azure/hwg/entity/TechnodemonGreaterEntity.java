package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.UUID;

import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.goal.RangedStrafeAttackGoal;
import mod.azure.hwg.entity.goal.WeaponGoal;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class TechnodemonGreaterEntity extends HWGEntity {

	public TechnodemonGreaterEntity(EntityType<TechnodemonGreaterEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.experiencePoints = HWGConfig.greater_exp;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		super.registerControllers(data);
		data.addAnimationController(
				new AnimationController<TechnodemonGreaterEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(4, new RangedStrafeAttackGoal(this,
				new WeaponGoal(this).setProjectileOriginOffset(0.8, 0.8, 0.8), 0.85D, 5, 30, 15, 15F));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 2);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, HWGConfig.greater_health)
				.add(EntityAttributes.GENERIC_ARMOR, 5).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 3.45F;
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			EntityData entityData, NbtCompound entityTag) {
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 3);
		this.setVariant(var);
		this.setUuid(UUID.randomUUID());
		this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	private ItemStack makeInitialWeapon() {
		Random rand = new Random();
		List<ItemConvertible> givenList = Arrays.asList(HWGItems.MINIGUN, HWGItems.BRIMSTONE, HWGItems.BALROG);
		int randomIndex = rand.nextInt(givenList.size());
		ItemConvertible randomElement = givenList.get(randomIndex);
		return new ItemStack(randomElement);
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	@Override
	public int getVariants() {
		return 2;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

}