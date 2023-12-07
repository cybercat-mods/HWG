package mod.azure.hwg.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.tasks.HWGMeleeAttackTask;
import mod.azure.hwg.entity.tasks.RangedShootingAttack;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TechnodemonGreaterEntity extends HWGEntity implements SmartBrainOwner<TechnodemonGreaterEntity> {

    public TechnodemonGreaterEntity(EntityType<TechnodemonGreaterEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        xpReward = HWGMod.config.mobconfigs.greatconfigs.greater_exp;
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, HWGMod.config.mobconfigs.greatconfigs.greater_health).add(Attributes.ARMOR, 5).add(Attributes.ATTACK_DAMAGE, 10D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (event.isMoving() && !isSwimming())
                return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).triggerableAnim("ranged", RawAnimation.begin().then("attacking", LoopType.LOOP)).triggerableAnim("melee", RawAnimation.begin().then("melee", LoopType.PLAY_ONCE)).triggerableAnim("idle", RawAnimation.begin().thenWait(5).then("idle", LoopType.LOOP)));
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    @Override
    public List<ExtendedSensor<TechnodemonGreaterEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyPlayersSensor<>(), new NearbyLivingEntitySensor<TechnodemonGreaterEntity>().setPredicate((target, entity) -> target instanceof Player || target instanceof Villager), new HurtBySensor<>(), new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<TechnodemonGreaterEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new StrafeTarget<>(), new LookAtTarget<>(), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<TechnodemonGreaterEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<>(new TargetOrRetaliate<>(), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || (target instanceof Player player && !(player.isCreative() || player.isSpectator()))), new SetRandomLookTarget<>()), new OneRandomBehaviour<TechnodemonEntity>(new SetRandomWalkTarget<>().speedModifier(0.7F).startCondition(entity -> !entity.isAggressive()), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<TechnodemonGreaterEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopIf(target -> !target.isAlive() || (target instanceof Player player && (player.isCreative() || player.isSpectator()))),
                new RangedShootingAttack<>(5).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false)),
                new HWGMeleeAttackTask<>(3));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(tag.getInt("Variant"));
    }

    public int getVariant() {
        return Mth.clamp(entityData.get(VARIANT), 1, 2);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 3.45F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityTag) {
        setVariant(random.nextInt(0, 3));
        setUUID(UUID.randomUUID());
        setItemSlot(EquipmentSlot.MAINHAND, makeInitialWeapon());
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityTag);
    }

    private ItemStack makeInitialWeapon() {
        final var givenList = Arrays.asList(HWGItems.MINIGUN, HWGItems.BRIMSTONE, HWGItems.BALROG);
        final var randomIndex = random.nextInt(givenList.size());
        final var randomElement = givenList.get(randomIndex);
        return new ItemStack(randomElement);
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
        this.playSound(getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

}