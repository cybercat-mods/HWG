package mod.azure.hwg.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.sblforked.api.SmartBrainOwner;
import mod.azure.azurelib.sblforked.api.core.BrainActivityGroup;
import mod.azure.azurelib.sblforked.api.core.SmartBrainProvider;
import mod.azure.azurelib.sblforked.api.core.behaviour.FirstApplicableBehaviour;
import mod.azure.azurelib.sblforked.api.core.behaviour.OneRandomBehaviour;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.look.LookAtTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.misc.Idle;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.move.MoveToWalkTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.move.StrafeTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.path.SetRandomWalkTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.target.InvalidateAttackTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.target.SetPlayerLookTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.target.SetRandomLookTarget;
import mod.azure.azurelib.sblforked.api.core.behaviour.custom.target.TargetOrRetaliate;
import mod.azure.azurelib.sblforked.api.core.sensor.ExtendedSensor;
import mod.azure.azurelib.sblforked.api.core.sensor.custom.UnreachableTargetSensor;
import mod.azure.azurelib.sblforked.api.core.sensor.vanilla.HurtBySensor;
import mod.azure.azurelib.sblforked.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import mod.azure.azurelib.sblforked.api.core.sensor.vanilla.NearbyPlayersSensor;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.animation.AnimationDispatcher;
import mod.azure.hwg.entity.tasks.HWGMeleeAttackTask;
import mod.azure.hwg.entity.tasks.RangedShootingAttack;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SpyEntity extends HWGEntity implements SmartBrainOwner<SpyEntity> {

    public SpyEntity(EntityType<SpyEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.animationDispatcher = new AnimationDispatcher(this);
        xpReward = CommonMod.config.mobconfigs.spyconfigs.spy_exp;
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.35D).add(Attributes.MAX_HEALTH, CommonMod.config.mobconfigs.spyconfigs.spy_health).add(Attributes.ARMOR, 3).add(Attributes.ARMOR_TOUGHNESS, 1D).add(Attributes.ATTACK_DAMAGE, 2D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    public static boolean canSpawn(EntityType<? extends HWGEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return (world.getBrightness(LightLayer.BLOCK, pos) <= 8 || world.getDifficulty() == Difficulty.PEACEFUL) && checkAnyLightMonsterSpawnRules(type, world, spawnReason, pos, random);
    }

    public static int generateVariants(RandomSource random) {
        if (random.nextInt(500) == 0)
            return 3;
        if (random.nextInt(100) < 5)
            return 2;
        return 1;
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
    public List<ExtendedSensor<SpyEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyPlayersSensor<>(), new NearbyLivingEntitySensor<SpyEntity>().setPredicate((target, entity) -> target instanceof Player || target instanceof Villager), new HurtBySensor<>(), new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<SpyEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new StrafeTarget<>(), new LookAtTarget<>(), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<SpyEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<SpyEntity>(new TargetOrRetaliate<>(),
                new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || (target instanceof Player player && !(player.isCreative() || player.isSpectator()))),
                new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().speedModifier(1).startCondition(entity -> !entity.isAggressive()), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<SpyEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopIf(target -> !target.isAlive() || (target instanceof Player player && (player.isCreative() || player.isSpectator()))),
                new RangedShootingAttack<>(5).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false)),
                new HWGMeleeAttackTask<>(3));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(tag.getInt("Variant"));
    }

    public int getVariant() {
        return Mth.clamp(entityData.get(VARIANT), 1, 3);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        setVariant(generateVariants(getRandom()));
        setItemSlot(EquipmentSlot.MAINHAND, makeInitialWeapon());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    private ItemStack makeInitialWeapon() {
        final var givenList = Arrays.asList(HWGItems.SPISTOL.get(), HWGItems.SNIPER.get());
        final var randomIndex = random.nextInt(givenList.size());
        final var randomElement = givenList.get(randomIndex);
        return random.nextFloat() <= 0.5 ? new ItemStack(HWGItems.GOLDEN_GUN.get()) : new ItemStack(randomElement);
    }

    @Override
    public int getVariants() {
        return 4;
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
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.PILLAGER_HURT;
    }

}