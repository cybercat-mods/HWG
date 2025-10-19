package mod.azure.hwg.entity.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGProjectiles;

public class RocketEntity extends AbstractArrow {

    public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(
        RocketEntity.class,
        EntityDataSerializers.FLOAT
    );

    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public RocketEntity(Level world, LivingEntity owner) {
        super(HWGProjectiles.ROCKETS.get(), world);
    }

    @Override
    public void tickDespawn() {
        if (this.tickCount >= 80) {
            this.explode();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FORCED_YAW, 0f);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("life", (short) this.tickCount);
        tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.tickCount = tag.getShort("life");
        super.readAdditionalSaveData(tag);
        entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
    }

    @Override
    public void tick() {
        super.tick();
        Helper.setOnFire(this);
        if (this.level().isClientSide) {
            double x = this.getX() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D;
            double z = this.getZ() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D;
            this.level().addParticle(ParticleTypes.SMOKE, true, x, this.getY(0.5), z, 0, 0, 0);
        }
        var bl = this.isNoPhysics();
        var vec3d = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double f = vec3d.horizontalDistance();
            this.setYRot((float) (Mth.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
            this.setXRot((float) (Mth.atan2(vec3d.y, f) * 57.2957763671875D));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        if (getOwner() instanceof Player)
            setYRot(entityData.get(FORCED_YAW));
        if (this.tickCount >= 190) {
            this.explode();
            this.remove(RemovalReason.DISCARDED);
        }
        if (this.tickCount > 80 && !bl) {
            this.tickDespawn();
        } else {
            this.tickCount = 0;
            var vec3d3 = this.position();
            var vector3d3 = vec3d3.add(vec3d);
            HitResult hitResult = this.level()
                .clip(new ClipContext(vec3d3, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS)
                vector3d3 = hitResult.getLocation();
            while (!this.isRemoved()) {
                var entityHitResult = this.findHitEntity(vec3d3, vector3d3);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }
                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    var entity = ((EntityHitResult) hitResult).getEntity();
                    var entity2 = this.getOwner();
                    if (
                        entity instanceof Player player && entity2 instanceof Player player1 && !player1.canHarmPlayer(
                            player
                        )
                    ) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }
                if (hitResult != null && !bl) {
                    this.onHit(hitResult);
                    this.hasImpulse = true;
                }
                if (entityHitResult == null || this.getPierceLevel() <= 0)
                    break;
                hitResult = null;
            }
            vec3d = this.getDeltaMovement();
            double d = vec3d.x;
            double e = vec3d.y;
            double g = vec3d.z;
            double h = this.getX() + d;
            double j = this.getY() + e;
            double k = this.getZ() + g;
            double l = vec3d.horizontalDistance();
            if (bl)
                this.setYRot((float) (Mth.atan2(-e, -g) * 57.2957763671875D));
            else
                this.setYRot((float) (Mth.atan2(e, g) * 57.2957763671875D));
            this.setXRot((float) (Mth.atan2(e, l) * 57.2957763671875D));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            var m = 0.99F;
            this.setDeltaMovement(vec3d.scale(m));
            if (!this.isNoGravity() && !bl)
                this.setDeltaMovement(
                    this.getDeltaMovement().x,
                    this.getDeltaMovement().y - 0.05000000074505806D,
                    this.getDeltaMovement().z
                );
            this.absMoveTo(h, j, k);
            this.checkInsideBlocks();
        }
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
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide) {
            this.explode();
            if (this.isOnFire() && entityHitResult.getEntity() instanceof LivingEntity)
                entityHitResult.getEntity().setRemainingFireTicks(50);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected void explode() {
        this.level()
            .explode(
                this,
                this.getX(),
                this.getY(0.0625D),
                this.getZ(),
                2.0F,
                false,
                CommonMod.config.gunconfigs.rocket_breaks
                    ? Level.ExplosionInteraction.BLOCK
                    : Level.ExplosionInteraction.NONE
            );
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
