package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mod.azure.hwg.entity.goal.RangedAttackGoal;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.entity.projectiles.FireballEntity;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.item.ammo.BulletAmmo;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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

public class TechnodemonEntity extends HWGEntity implements IAnimatable {

	private final RangedAttackGoal<TechnodemonEntity> bowAttackGoal = new RangedAttackGoal<>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false) {
		public void stop() {
			super.stop();
			TechnodemonEntity.this.setAttacking(false);
		}

		public void start() {
			super.start();
			TechnodemonEntity.this.setAttacking(true);
		}
	};

	private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(TechnodemonEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);

	public TechnodemonEntity(EntityType<TechnodemonEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.experiencePoints = config.lesser_exp;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isSwimming() && !this.dataTracker.get(SHOOTING)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (this.isAttacking() && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<TechnodemonEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public static boolean canSpawn(EntityType<? extends HWGEntity> type, WorldAccess world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		return world.getDifficulty() != Difficulty.PEACEFUL;
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

	protected BulletEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
		return TechnodemonEntity.createArrowProjectile(this, arrow, damageModifier);
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

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.updateAttackType();
		this.setVariant(tag.getInt("Variant"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 4);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_ARMOR, 4)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.lesser_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 2.25F;
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			EntityData entityData, NbtCompound entityTag) {
		this.setVariant(this.random.nextInt(5));
		this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
		this.updateAttackType();
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	private ItemStack makeInitialWeapon() {
		Random rand = new Random();
		List<ItemConvertible> givenList = Arrays.asList(HWGItems.HELLHORSE, HWGItems.FLAMETHROWER, HWGItems.BRIMSTONE);
		int randomIndex = rand.nextInt(givenList.size());
		ItemConvertible randomElement = givenList.get(randomIndex);
		return new ItemStack(randomElement);
	}

	public void attack(LivingEntity target, float pullProgress) {
		ItemStack itemStack = this.getArrowType(this.getStackInHand(
				ProjectileUtil.getHandPossiblyHolding(this, this.getEquippedStack(EquipmentSlot.MAINHAND).getItem())));
		if (this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof HellhorseRevolverItem) {
			BulletEntity projectile = this.createArrowProjectile(itemStack, pullProgress);
			double d = target.getX() - this.getX();
			double e = target.getBodyY(0.3333333333333333D) - projectile.getY();
			double f = target.getZ() - this.getZ();
			double g = (double) MathHelper.sqrt(d * d + f * f);
			projectile.setVelocity(d, e + g * 0.05F, f, 1.6F, 0.0F);
			this.world.spawnEntity(projectile);
		}
		if (this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof FlamethrowerItem) {

			FlameFiring abstractarrowentity = createFlame(world, itemStack, this);
			abstractarrowentity.setProperties(this, this.pitch, this.yaw, 0.0F, 0.25F * 3.0F, 2.0F);
			abstractarrowentity.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity.age = 30;
			world.spawnEntity(abstractarrowentity);

			FlameFiring abstractarrowentity1 = createFlame(world, itemStack, this);
			abstractarrowentity1.setProperties(this, this.pitch, this.yaw + 10, 0.0F, 0.25F * 3.0F, 2.0F);
			abstractarrowentity1.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity1.age = 30;
			world.spawnEntity(abstractarrowentity1);

			FlameFiring abstractarrowentity3 = createFlame(world, itemStack, this);
			abstractarrowentity3.setProperties(this, this.pitch, this.yaw + 5, 0.0F, 0.25F * 3.0F, 2.0F);
			abstractarrowentity3.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity3.age = 30;
			world.spawnEntity(abstractarrowentity3);

			FlameFiring abstractarrowentity2 = createFlame(world, itemStack, this);
			abstractarrowentity2.setProperties(this, this.pitch, this.yaw - 10, 0.0F, 0.25F * 3.0F, 2.0F);
			abstractarrowentity2.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity2.age = 30;
			world.spawnEntity(abstractarrowentity2);

			FlameFiring abstractarrowentity4 = createFlame(world, itemStack, this);
			abstractarrowentity4.setProperties(this, this.pitch, this.yaw - 5, 0.0F, 0.25F * 3.0F, 2.0F);
			abstractarrowentity4.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity4.age = 30;
			world.spawnEntity(abstractarrowentity4);
		}
		if (this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof BrimstoneItem) {
			FireballEntity abstractarrowentity = createArrow(world, itemStack, this);
			abstractarrowentity.setProperties(this, this.pitch, this.yaw, 0.0F, 0.25F * 3.0F, 1.0F);
			abstractarrowentity.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity.setFireTicks(100);
			abstractarrowentity.setDamage(6.5);
			abstractarrowentity.setPunch(1);

			FireballEntity abstractarrowentity1 = createArrow(world, itemStack, this);
			abstractarrowentity1.setProperties(this, this.pitch, this.yaw + 5, 0.0F, 0.25F * 3.0F, 1.0F);
			abstractarrowentity1.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity1.setFireTicks(100);
			abstractarrowentity1.setDamage(6.5);
			abstractarrowentity1.setPunch(1);

			FireballEntity abstractarrowentity2 = createArrow(world, itemStack, this);
			abstractarrowentity2.setProperties(this, this.pitch, this.yaw - 5, 0.0F, 0.25F * 3.0F, 1.0F);
			abstractarrowentity2.refreshPositionAndAngles(this.getX(), this.getBodyY(0.5), this.getZ(), 0, 0);
			abstractarrowentity2.setFireTicks(100);
			abstractarrowentity2.setDamage(6.5);
			abstractarrowentity2.setPunch(1);

			world.spawnEntity(abstractarrowentity);
			world.spawnEntity(abstractarrowentity1);
			world.spawnEntity(abstractarrowentity2);
		}
	}

	public FlameFiring createFlame(World worldIn, ItemStack stack, LivingEntity shooter) {
		FlameFiring arrowentity = new FlameFiring(worldIn, shooter);
		return arrowentity;
	}

	public FireballEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		FireballEntity arrowentity = new FireballEntity(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public int getLimitPerChunk() {
		return 1;
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	@Override
	public int getVariants() {
		return 4;
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