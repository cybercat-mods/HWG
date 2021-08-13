package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mod.azure.hwg.entity.goal.RangedAttackGoal;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.item.ammo.BulletAmmo;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SpyEntity extends HWGEntity implements IAnimatable {

	private final RangedAttackGoal<SpyEntity> bowAttackGoal = new RangedAttackGoal<>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false) {
		public void stop() {
			super.stop();
			SpyEntity.this.setAttacking(false);
		}

		public void start() {
			super.start();
			SpyEntity.this.setAttacking(true);
		}
	};

	private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(SpyEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);

	public SpyEntity(EntityType<SpyEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.isAttacking() && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<SpyEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(9, new SwimGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Environment(EnvType.CLIENT)
	public boolean isShooting() {
		return (Boolean) this.dataTracker.get(SHOOTING);
	}

	@Override
	public void setShooting(boolean shooting) {
		this.dataTracker.set(SHOOTING, shooting);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SHOOTING, false);
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
		this.updateAttackType();
		this.setVariant(tag.getInt("Variant"));
	}

	public void equipStack(EquipmentSlot slot, ItemStack stack) {
		super.equipStack(slot, stack);
		if (!this.world.isClient) {
			this.updateAttackType();
		}

	}

	public void updateAttackType() {
		if (this.world != null && !this.world.isClient) {
			this.goalSelector.remove(this.meleeAttackGoal);
			this.goalSelector.remove(this.bowAttackGoal);
			ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this,
					this.getEquippedStack(EquipmentSlot.MAINHAND).getItem()));
			if (itemStack.getItem() == this.getEquippedStack(EquipmentSlot.MAINHAND).getItem()) {
				int i = 20;
				if (this.world.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}

				this.bowAttackGoal.setAttackInterval(i);
				this.goalSelector.add(4, this.bowAttackGoal);
			} else {
				this.goalSelector.add(4, this.meleeAttackGoal);
			}

		}
	}

	public void attack(LivingEntity target, float pullProgress) {
		ItemStack itemStack = this.getArrowType(this.getStackInHand(
				ProjectileUtil.getHandPossiblyHolding(this, this.getEquippedStack(EquipmentSlot.MAINHAND).getItem())));
		BulletEntity BulletEntity = this.createArrowProjectile(itemStack, pullProgress);
		double d = target.getX() - this.getX();
		double e = target.getBodyY(0.3333333333333333D) - BulletEntity.getY();
		double f = target.getZ() - this.getZ();
		float g = MathHelper.sqrt((float) (d * d + f * f));
		BulletEntity.setVelocity(d, e + g * 0.05F, f, 1.6F, 0.0F);
		this.world.spawnEntity(BulletEntity);
	}

	protected BulletEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
		return SpyEntity.createArrowProjectile(this, arrow, damageModifier);
	}

	public boolean canUseRangedWeapon(Item weapon) {
		return weapon == this.getEquippedStack(EquipmentSlot.MAINHAND).getItem();
	}

	public static BulletEntity createArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
		BulletAmmo arrowItem = (BulletAmmo) ((BulletAmmo) (stack.getItem() instanceof BulletAmmo ? stack.getItem()
				: HWGItems.BULLETS));
		BulletEntity persistentProjectileEntity = arrowItem.createArrow(entity.world, stack, entity);
		persistentProjectileEntity.applyEnchantmentEffects(entity, damageModifier);

		return persistentProjectileEntity;
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 3);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.spy_health).add(EntityAttributes.GENERIC_ARMOR, 3)
				.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
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
		this.updateAttackType();
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public int getLimitPerChunk() {
		return 1;
	}

	private ItemStack makeInitialWeapon() {
		Random rand = new Random();
		List<ItemConvertible> givenList = Arrays.asList(HWGItems.SPISTOL, HWGItems.SNIPER);
		int randomIndex = rand.nextInt(givenList.size());
		ItemConvertible randomElement = givenList.get(randomIndex);
		return rand.nextFloat() <= 0.5 ? new ItemStack(HWGItems.GOLDEN_GUN) : new ItemStack(randomElement);
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