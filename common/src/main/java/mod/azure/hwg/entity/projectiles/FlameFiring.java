package mod.azure.hwg.entity.projectiles;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class FlameFiring extends AbstractArrow {

    public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(FlameFiring.class, EntityDataSerializers.FLOAT);
    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
    private LivingEntity shooter;
    private int idleTicks = 0;

    public FlameFiring(EntityType<? extends FlameFiring> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public FlameFiring(Level world, LivingEntity owner) {
        super(HWGProjectiles.FIRING.get(), world);
        this.shooter = owner;
    }

    @Override
    public void tickDespawn() {
        if (this.tickCount >= 40) this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity living) {
        super.doPostHurtEffects(living);
        if (CommonMod.config.gunconfigs.bullets_disable_iframes_on_players || !(living instanceof Player)) {
            living.setDeltaMovement(0, 0, 0);
            living.invulnerableTime = 0;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FORCED_YAW, 0f);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("life", (short)this.tickCount);
        tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.tickCount = tag.getShort("life");
        entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
    }

    @Override
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleTicks < idleOpt) super.tick();
        if (this.tickCount >= 40) this.remove(RemovalReason.DISCARDED);
        var isInsideWaterBlock = level().isWaterAt(blockPosition());
        Helper.spawnLightSource(this, isInsideWaterBlock);
        if (getOwner() instanceof Player) setYRot(entityData.get(FORCED_YAW));
        if (this.tickCount % 16 == 2)
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 0.5F, 1.0F);
        if (this.level().isClientSide) {
            var x = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * this.getBbWidth() * 0.5D;
            var y = this.getY() + 0.05D + this.random.nextDouble();
            var z = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * this.getBbWidth() * 0.5D;
            this.level().addParticle(ParticleTypes.FLAME, true, x, y, z, 0, 0, 0);
            this.level().addParticle(HWGParticles.BRIM_ORANGE.get(), true, x, y, z, 0, 0, 0);
            this.level().addParticle(HWGParticles.BRIM_RED.get(), true, x, y, z, 0, 0, 0);
        }
        this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2)).forEach(e -> {
            if (e.isAlive() && !(e instanceof Player || e instanceof HWGEntity)) {
                e.hurt(damageSources().arrow(this, this.shooter), 3);
                if (!(this.getOwner() instanceof Player)) e.setRemainingFireTicks(90);
            }
        });
    }

    @Override
    public boolean isNoGravity() {
        return !this.isUnderWater();
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.FIRE_AMBIENT;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                var blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
                if (this.level().isEmptyBlock(blockPos))
                    this.level().setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level(), blockPos));
            }
            this.remove(RemovalReason.DISCARDED);
        }
        this.setSoundEvent(SoundEvents.FIRE_AMBIENT);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide) this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    public void setProperties(float pitch, float yaw, float roll, float modifierZ) {
        var f = 0.017453292F;
        var x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
        var y = -Mth.sin((pitch + roll) * f);
        var z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
        this.shoot(x, y, z, modifierZ, 0);
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

}