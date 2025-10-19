package mod.azure.hwg.entity.projectiles;

import mod.azure.azurelib.common.util.CommonUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGProjectiles;
import mod.azure.hwg.util.registry.HWGSounds;

public class BaseFlareEntity extends AbstractArrow {

    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(
        BaseFlareEntity.class,
        EntityDataSerializers.INT
    );

    private int life;

    private int idleTicks = 0;

    public BaseFlareEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(HWGProjectiles.FLARE.get(), world);
        this.pickup = Pickup.DISALLOWED;
    }

    public BaseFlareEntity(Level world, double x, double y, double z) {
        super(HWGProjectiles.FLARE.get(), world);
        this.absMoveTo(x, y, z);
        this.pickup = Pickup.DISALLOWED;
    }

    public BaseFlareEntity(Level world, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z);
        this.pickup = Pickup.DISALLOWED;
    }

    public BaseFlareEntity(Level world, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z, shotAtAngle);
        this.setOwner(entity);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity living) {
        super.doPostHurtEffects(living);
        if (CommonMod.config.gunconfigs.bullets_disable_iframes_on_players || !(living instanceof Player)) {
            living.invulnerableTime = 0;
            living.setDeltaMovement(0, 0, 0);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLOR, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putShort("life", (short) this.tickCount);
        compound.putInt("Variant", this.getColor());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.tickCount = compound.getShort("life");
        this.setColor(compound.getInt("Variant"));
    }

    public int getColor() {
        return Mth.clamp(this.entityData.get(COLOR), 1, 16);
    }

    public void setColor(int color) {
        this.entityData.set(COLOR, color);
    }

    @Override
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01)
            idleTicks++;
        else
            idleTicks = 0;
        if (idleTicks < idleOpt)
            super.tick();
        if (this.tickCount >= 800 || this.isInWater())
            this.remove(RemovalReason.DISCARDED);
        if (this.life == 0 && !this.isSilent())
            this.level()
                .playSound(
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    HWGSounds.FLAREGUN_SHOOT.get(),
                    SoundSource.PLAYERS,
                    6.0F,
                    1.0F
                );
        setNoGravity(false);
        ++this.life;
        var vec3d = this.getDeltaMovement();
        this.setDeltaMovement(vec3d.scale(0.99F));
        if (this.tickCount > 25)
            this.setDeltaMovement(0.0, -0.1, 0.0);
        var isInsideWaterBlock = level().isWaterAt(blockPosition());
        CommonUtils.spawnLightSource(this, isInsideWaterBlock);
        if (this.level().isClientSide) {
            this.level()
                .addParticle(
                    this.particleColor(),
                    true,
                    this.getX(),
                    this.getY() - 0.3D,
                    this.getZ(),
                    0,
                    -this.getDeltaMovement().y * 0.17D,
                    0
                );
        }
    }

    private ParticleOptions particleColor() {
        return switch (this.getColor()) {
            case 15 -> HWGParticles.YELLOW_FLARE.get();
            case 14 -> HWGParticles.RED_FLARE.get();
            case 13 -> HWGParticles.PURPLE_FLARE.get();
            case 12 -> HWGParticles.PINK_FLARE.get();
            case 11 -> HWGParticles.ORANGE_FLARE.get();
            case 10 -> HWGParticles.MAGENTA_FLARE.get();
            case 9 -> HWGParticles.LIME_FLARE.get();
            case 8 -> HWGParticles.LIGHTGRAY_FLARE.get();
            case 7 -> HWGParticles.LIGHTBLUE_FLARE.get();
            case 6 -> HWGParticles.GREEN_FLARE.get();
            case 5 -> HWGParticles.GRAY_FLARE.get();
            case 4 -> HWGParticles.CYAN_FLARE.get();
            case 3 -> HWGParticles.BROWN_FLARE.get();
            case 2 -> HWGParticles.BLUE_FLARE.get();
            case 1 -> HWGParticles.BLACK_FLARE.get();
            default -> HWGParticles.WHITE_FLARE.get();
        };
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.getDefaultHitGroundSoundEvent();
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return HWGSounds.FLAREGUN.get();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide)
            this.remove(RemovalReason.DISCARDED);
        this.setSoundEvent(HWGSounds.FLAREGUN.get());
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    protected boolean tryPickup(@NotNull Player player) {
        return false;
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(HWGItems.WHITE_FLARE.get());
    }
}
