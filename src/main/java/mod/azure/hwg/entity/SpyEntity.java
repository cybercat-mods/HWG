package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;

import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.goal.RangedStrafeAttackGoal;
import mod.azure.hwg.entity.goal.WeaponGoal;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class SpyEntity extends HWGEntity {

	public SpyEntity(EntityType<SpyEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.experiencePoints = HWGConfig.spy_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})).add(new AnimationController<>(this, event -> {
			if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())
					&& !(this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof Minigun))
				return event.setAndContinue(RawAnimation.begin().thenLoop("attacking"));
			return PlayState.CONTINUE;
		}));
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
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 3);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, HWGConfig.spy_health).add(EntityAttributes.GENERIC_ARMOR, 3)
				.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	public static boolean canSpawn(EntityType<? extends HWGEntity> type, WorldAccess world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		return world.getLightLevel(LightType.BLOCK, pos) > 8 && world.getDifficulty() != Difficulty.PEACEFUL ? false
				: canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random);
	}

	public static boolean canSpawnIgnoreLightLevel(EntityType<? extends HWGEntity> type, ServerWorldAccess world,
			SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getDifficulty() != Difficulty.PEACEFUL
				&& HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1.85F;
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			EntityData entityData, NbtCompound entityTag) {
		this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
		this.setVariant(generateVariants(world.getRandom()));
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	private ItemStack makeInitialWeapon() {
		List<ItemConvertible> givenList = Arrays.asList(HWGItems.SPISTOL, HWGItems.SNIPER);
		int randomIndex = random.nextInt(givenList.size());
		ItemConvertible randomElement = givenList.get(randomIndex);
		return random.nextFloat() <= 0.5 ? new ItemStack(HWGItems.GOLDEN_GUN) : new ItemStack(randomElement);
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	@Override
	public int getVariants() {
		return 4;
	}

	public static int generateVariants(Random random) {
		return random.nextInt(500) == 0 ? 3 : random.nextInt(100) < 5 ? 2 : 1;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_PILLAGER_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_PILLAGER_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_PILLAGER_HURT;
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.ILLAGER;
	}

}