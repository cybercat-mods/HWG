package mod.azure.hwg.entity;

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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.ai.DelayedAttackGoal;
import mod.azure.hwg.entity.ai.DelayedRangedAttackGoal;
import mod.azure.hwg.entity.animation.AnimationDispatcher;
import mod.azure.hwg.util.registry.HWGItems;

public class SpyEntity extends HWGEntity {

    public SpyEntity(EntityType<SpyEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.animationDispatcher = new AnimationDispatcher(this);
        xpReward = CommonMod.config.mobconfigs.spyconfigs.spy_exp;
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes()
            .add(Attributes.FOLLOW_RANGE, 25.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.35D)
            .add(Attributes.MAX_HEALTH, CommonMod.config.mobconfigs.spyconfigs.spy_health)
            .add(Attributes.ARMOR, 3)
            .add(Attributes.ARMOR_TOUGHNESS, 1D)
            .add(Attributes.ATTACK_DAMAGE, 2D)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    public static boolean canSpawn(
        EntityType<? extends HWGEntity> type,
        LevelAccessor world,
        MobSpawnType spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        return (world.getBrightness(LightLayer.BLOCK, pos) <= 8 || world.getDifficulty() == Difficulty.PEACEFUL)
            && checkAnyLightMonsterSpawnRules(type, world, spawnReason, pos, random);
    }

    public static int generateVariants(RandomSource random) {
        if (random.nextInt(500) == 0)
            return 3;
        if (random.nextInt(100) < 5)
            return 2;
        return 1;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new DelayedAttackGoal(this, 1.0F, false, 20, this::runAttackAnimations));
        this.goalSelector.addGoal(
            3,
            new DelayedRangedAttackGoal(this, 1.0F, false, 30, this::runRangeAttackAniamtions)
        );
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0F, true, 4, () -> true));
        this.goalSelector.addGoal(6, new FloatGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, (double) 1.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
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
    public SpawnGroupData finalizeSpawn(
        @NotNull ServerLevelAccessor level,
        @NotNull DifficultyInstance difficulty,
        @NotNull MobSpawnType spawnType,
        @Nullable SpawnGroupData spawnGroupData
    ) {
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
