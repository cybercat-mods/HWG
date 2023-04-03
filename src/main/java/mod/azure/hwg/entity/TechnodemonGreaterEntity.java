package mod.azure.hwg.entity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.tasks.RangedShootingAttack;
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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

public class TechnodemonGreaterEntity extends HWGEntity implements SmartBrainOwner<TechnodemonGreaterEntity> {

	public TechnodemonGreaterEntity(EntityType<TechnodemonGreaterEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.xpReward = HWGConfig.greater_exp;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		var isDead = this.dead || this.getHealth() < 0.01 || this.isDeadOrDying();
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && !this.isSwimming())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})).add(new AnimationController<>(this, event -> {
			if ((this.entityData.get(STATE) == 1 || this.swinging) && !isDead && !(this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Minigun))
				return event.setAndContinue(RawAnimation.begin().thenLoop("attacking"));
			return PlayState.STOP;
		}));
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
	}

	@Override
	public List<ExtendedSensor<TechnodemonGreaterEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyPlayersSensor<>(), new NearbyLivingEntitySensor<TechnodemonGreaterEntity>().setPredicate((target, entity) -> target instanceof Player || target instanceof Villager), new HurtBySensor<>(), new UnreachableTargetSensor<TechnodemonGreaterEntity>());
	}

	@Override
	public BrainActivityGroup<TechnodemonGreaterEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new StrafeTarget<>(), new LookAtTarget<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<TechnodemonGreaterEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<TechnodemonGreaterEntity>(new TargetOrRetaliate<>(), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().speedModifier(0.7F).startCondition(entity -> !entity.isAggressive()), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
	}

	@Override
	public BrainActivityGroup<TechnodemonGreaterEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new RangedShootingAttack<>(20).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false)), new AnimatableMeleeAttack<>(0));
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
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 2);
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, HWGConfig.greater_health).add(Attributes.ARMOR, 5).add(Attributes.ATTACK_DAMAGE, 10D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 3.45F;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityTag) {
		var r = random.nextInt(0, 3);
		this.setVariant(r);
		this.setUUID(UUID.randomUUID());
		this.setItemSlot(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
	}

	private ItemStack makeInitialWeapon() {
		var givenList = Arrays.asList(HWGItems.MINIGUN, HWGItems.BRIMSTONE, HWGItems.BALROG);
		var randomIndex = random.nextInt(givenList.size());
		var randomElement = givenList.get(randomIndex);
		return new ItemStack(randomElement);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	@Override
	public int getVariants() {
		return 2;
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