package mod.azure.hwg.entity.projectiles;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class GrenadeEntity extends AbstractArrow {

    public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
    protected String type;
    public GrenadeDispatcher grenadeDispatcher;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
        this.grenadeDispatcher = new GrenadeDispatcher(this);
    }

    public GrenadeEntity(Level world, LivingEntity owner) {
        super(HWGProjectiles.GRENADE.get(), world);
    }

    public GrenadeEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        this(world, stack, x, y, z, shotAtAngle);
        this.setOwner(entity);
    }

    public GrenadeEntity(Level world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z, stack);
    }

    public GrenadeEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(HWGProjectiles.GRENADE.get(), world);
        this.absMoveTo(x, y, z);
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FORCED_YAW, 0f);
        builder.define(VARIANT, 0);
        builder.define(STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("life", (short)this.tickCount);
        tag.putInt("Variant", this.getVariant());
        tag.putInt("State", this.getVariant());
        tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.tickCount = tag.getShort("life");
        this.setVariant(tag.getInt("Variant"));
        this.setVariant(tag.getInt("State"));
        entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.entityData.get(STATE) == 1) {
                grenadeDispatcher.sendSpinAnimation();
            } else {
                grenadeDispatcher.sendBulletAnimation();
            }
        }
        if (getOwner() instanceof Player)
            setYRot(entityData.get(FORCED_YAW));
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(VARIANT), 1, 5);
    }

    public void setVariant(int color) {
        this.entityData.set(VARIANT, color);
    }

    public void setState(int color) {
        this.entityData.set(STATE, color);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        var areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        if (this.getVariant() == 1)
            areaeffectcloudentity.setParticle(this.getParticle());
        areaeffectcloudentity.setRadius(this.getVariant() == 4 ? 5.0F : 2.0F);
        areaeffectcloudentity.setDuration(this.getVariant() == 4 ? 120 : 2);
        if (this.getVariant() == 4)
            areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
        areaeffectcloudentity.absMoveTo(this.getX(), this.getEyeY(), this.getZ());
        this.level().addFreshEntity(areaeffectcloudentity);
        super.remove(reason);
    }

    private ParticleOptions getParticle() {
        return switch (this.getVariant()) {
            case 1, 2, 3 -> ParticleTypes.EXPLOSION;
            case 4 -> ParticleTypes.LARGE_SMOKE;
            case 5 -> ParticleTypes.FLASH;
            default -> ParticleTypes.END_ROD;
        };
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity target) {
        if (this.getVariant() == 1)
            if (target instanceof TechnodemonEntity || target instanceof TechnodemonGreaterEntity)
                super.doPostHurtEffects(target);
            else
                super.doPostHurtEffects(target);
    }

    @Override
    public void tickDespawn() {
        if (this.tickCount >= 80) {
            if (this.getVariant() == 1)
                this.emp();
            else if (this.getVariant() == 2)
                this.frag();
            else if (this.getVariant() == 3)
                this.naplam();
            else if (this.getVariant() == 5)
                this.stun();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.GENERIC_EXPLODE.value();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            if (this.getVariant() == 1)
                this.emp();
            else if (this.getVariant() == 2)
                this.frag();
            else if (this.getVariant() == 3)
                this.naplam();
            else if (this.getVariant() == 5)
                this.stun();
            this.remove(RemovalReason.DISCARDED);
        }
        this.setSoundEvent(SoundEvents.GENERIC_EXPLODE.value());
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide) {
            if (this.getVariant() == 1)
                this.emp();
            else if (this.getVariant() == 2)
                this.frag();
            else if (this.getVariant() == 3)
                this.naplam();
            else if (this.getVariant() == 5)
                this.stun();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected void stun() {
        this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3)).forEach(e -> {
            if (e.isAlive()) {
                e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
                e.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 1));
            }
        });
    }

    protected void frag() {
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2.0F, false, CommonMod.config.gunconfigs.grenades_breaks ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
    }

    protected void naplam() {
        this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3)).forEach(e -> {
            if (e.isAlive()) {
                e.setRemainingFireTicks(200);
            }
        });
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, true, Level.ExplosionInteraction.NONE);
    }

    protected void emp() {
        this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8)).forEach(e -> {
            if (e.isAlive() && (e instanceof TechnodemonEntity || e instanceof TechnodemonGreaterEntity))
                e.hurt(damageSources().arrow(this, this), 10);
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.LEVER)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.LEVER))
                    this.level().setBlockAndUpdate(testPos, state.setValue(LeverBlock.POWERED, false));
            }
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.REDSTONE_WIRE)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.REDSTONE_WIRE))
                    this.level().setBlockAndUpdate(testPos, state.setValue(RedStoneWireBlock.POWER, 0));
            }
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.COMPARATOR)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.COMPARATOR))
                    this.level().setBlockAndUpdate(testPos, state.setValue(DiodeBlock.POWERED, false));
            }
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.REPEATER)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.REPEATER))
                    this.level().setBlockAndUpdate(testPos, state.setValue(DiodeBlock.POWERED, false));
            }
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.REDSTONE_TORCH)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.REDSTONE_TORCH))
                    this.level().destroyBlock(testPos, true, null, 512);
            }
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.REDSTONE_WALL_TORCH)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.REDSTONE_WALL_TORCH))
                    this.level().destroyBlock(testPos, true, null, 512);
            }
        });
        this.level().getBlockStatesIfLoaded(this.getBoundingBox().inflate(8)).filter(state -> state.is(Blocks.REDSTONE_LAMP)).forEach(state -> {
            for (var testPos : BlockPos.betweenClosed(this.blockPosition().offset(new Vec3i(-8, -8, -8)), this.blockPosition().offset(new Vec3i(8, 8, 8)))) {
                if (this.level().getBlockState(testPos).is(Blocks.REDSTONE_LAMP))
                    this.level().setBlockAndUpdate(testPos, state.setValue(RedstoneTorchBlock.LIT, false));
            }
        });
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

}