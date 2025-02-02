package mod.azure.hwg.entity.projectiles;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class BlazeRodEntity extends AbstractArrow {

    public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(BlazeRodEntity.class, EntityDataSerializers.FLOAT);
    private int ticksInAir;
    private int idleTicks = 0;

    public BlazeRodEntity(EntityType<? extends BlazeRodEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public BlazeRodEntity(Level world, LivingEntity owner) {
        super(HWGProjectiles.BLAZEROD.get(), world);
    }

    public BlazeRodEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        this(world, stack, x, y, z, shotAtAngle);
        this.setOwner(entity);
    }

    public BlazeRodEntity(Level world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z, stack);
    }

    public BlazeRodEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(HWGProjectiles.BLAZEROD.get(), world);
        this.absMoveTo(x, y, z);
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity living) {
        super.doPostHurtEffects(living);
        if (CommonMod.config.gunconfigs.bullets_disable_iframes_on_players || !(living instanceof Player)) {
            living.invulnerableTime = 0;
        }
    }

    @Override
    public void tickDespawn() {
        ++this.ticksInAir;
        if (this.ticksInAir >= 40) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float divergence) {
        super.shoot(x, y, z, speed, divergence);
        this.ticksInAir = 0;
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

        ++this.ticksInAir;
        if (this.tickCount >= 190)
            this.remove(RemovalReason.DISCARDED);
        var isInsideWaterBlock = level().isWaterAt(blockPosition());
        Helper.setOnFire(this);
        Helper.spawnLightSource(this, isInsideWaterBlock);
        if (this.level().isClientSide) {
            var x = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * this.getBbWidth() * 0.5D;
            var y = this.getY() + 0.05D + this.random.nextDouble();
            var z = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * this.getBbWidth() * 0.5D;
            this.level().addParticle(ParticleTypes.FLAME, true, x, y, z, 0, 0, 0);
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, x, y, z, 0, 0, 0);
        }
        if (getOwner() instanceof Player) setYRot(entityData.get(FORCED_YAW));
    }

    @Override
    public boolean isNoGravity() {
        return !this.isUnderWater();
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.getDefaultHitGroundSoundEvent();
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.GENERIC_EXPLODE.value();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            this.explode();
            this.remove(RemovalReason.DISCARDED);
        }
        this.setSoundEvent(SoundEvents.GENERIC_EXPLODE.value());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity) && !this.level().isClientSide)
            this.remove(RemovalReason.DISCARDED);
        var entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) damageSource2 = damageSources().arrow(this, this);
        else {
            damageSource2 = damageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity livingEntity) livingEntity.setLastHurtMob(entity);
        }
        if (entity.hurt(damageSource2, CommonMod.config.gunconfigs.balrogconfigs.balrog_damage)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!this.level().isClientSide && entity2 instanceof LivingEntity && this.isOnFire()) livingEntity.setRemainingFireTicks(50);

                this.explode();

                this.doPostHurtEffects(livingEntity);
                if (livingEntity != entity2 && livingEntity instanceof Player player && player instanceof ServerPlayer serverPlayer && !this.isSilent())
                    serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
        } else if (!this.level().isClientSide) this.remove(RemovalReason.DISCARDED);
    }

    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false, CommonMod.config.gunconfigs.balrog_breaks ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
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