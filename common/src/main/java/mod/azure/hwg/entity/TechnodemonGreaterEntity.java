package mod.azure.hwg.entity;

import mod.azure.azurelib.common.util.MoveAnalysis;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.UUID;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.ai.DelayedAttackGoal;
import mod.azure.hwg.entity.ai.DelayedRangedAttackGoal;
import mod.azure.hwg.entity.animation.AnimationDispatcher;
import mod.azure.hwg.util.registry.HWGItems;

public class TechnodemonGreaterEntity extends HWGEntity {

    public TechnodemonGreaterEntity(EntityType<TechnodemonGreaterEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.animationDispatcher = new AnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
        xpReward = CommonMod.config.mobconfigs.greatconfigs.greater_exp;
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes()
            .add(Attributes.FOLLOW_RANGE, 25.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.MAX_HEALTH, CommonMod.config.mobconfigs.greatconfigs.greater_health)
            .add(Attributes.ARMOR, 5)
            .add(Attributes.ATTACK_DAMAGE, 10D)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();
        if (this.level().isClientSide()) {
            this.handleAnimations();
        }
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
        return Mth.clamp(entityData.get(VARIANT), 1, 2);
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
        setVariant(random.nextInt(0, 3));
        setUUID(UUID.randomUUID());
        setItemSlot(EquipmentSlot.MAINHAND, makeInitialWeapon());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    private ItemStack makeInitialWeapon() {
        final var givenList = Arrays.asList(HWGItems.MINIGUN.get(), HWGItems.BRIMSTONE.get(), HWGItems.BALROG.get());
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
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(getStepSound(), 0.15F, 1.0F);
    }

}
