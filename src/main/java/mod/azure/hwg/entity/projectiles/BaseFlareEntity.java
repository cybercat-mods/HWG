package mod.azure.hwg.entity.projectiles;

import dev.architectury.networking.SpawnEntityPacket;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGProjectiles;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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

public class BaseFlareEntity extends AbstractArrow {

    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(BaseFlareEntity.class, EntityDataSerializers.INT);
    private int life;
    private int idleTicks = 0;

    public BaseFlareEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(HWGProjectiles.FLARE, world, ItemStack.EMPTY);
    }

    public BaseFlareEntity(Level world, double x, double y, double z) {
        super(HWGProjectiles.FLARE, world, ItemStack.EMPTY);
        this.absMoveTo(x, y, z);
    }

    public BaseFlareEntity(Level world, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z);
    }

    public BaseFlareEntity(Level world, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z, shotAtAngle);
        this.setOwner(entity);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (HWGMod.config.gunconfigs.bullets_disable_iframes_on_players || !(living instanceof Player)) {
            living.invulnerableTime = 0;
            living.setDeltaMovement(0, 0, 0);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getColor());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setColor(tag.getInt("Variant"));
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
        if (idleOpt <= 0 || idleTicks < idleOpt)
            super.tick();
        if (this.tickCount >= 800 || this.isInWater())
            this.remove(Entity.RemovalReason.DISCARDED);
        if (this.life == 0 && !this.isSilent())
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), HWGSounds.FLAREGUN_SHOOT, SoundSource.PLAYERS, 6.0F, 1.0F);
        setNoGravity(false);
        ++this.life;
        var vec3d = this.getDeltaMovement();
        this.setDeltaMovement(vec3d.scale(0.99F));
        if (this.tickCount > 25)
            this.setDeltaMovement(0.0, -0.1, 0.0);
        var isInsideWaterBlock = level().isWaterAt(blockPosition());
        Helper.spawnLightSource(this, isInsideWaterBlock);
        if (this.level().isClientSide) {
            this.level().addParticle(this.particleColor(), true, this.getX(), this.getY() - 0.3D, this.getZ(), 0, -this.getDeltaMovement().y * 0.17D, 0);
        }
    }

    private ParticleOptions particleColor() {
        return switch (this.getColor()) {
            case 15 -> HWGParticles.YELLOW_FLARE;
            case 14 -> HWGParticles.RED_FLARE;
            case 13 -> HWGParticles.PURPLE_FLARE;
            case 12 -> HWGParticles.PINK_FLARE;
            case 11 -> HWGParticles.ORANGE_FLARE;
            case 10 -> HWGParticles.MAGENTA_FLARE;
            case 9 -> HWGParticles.LIME_FLARE;
            case 8 -> HWGParticles.LIGHTGRAY_FLARE;
            case 7 -> HWGParticles.LIGHTBLUE_FLARE;
            case 6 -> HWGParticles.GREEN_FLARE;
            case 5 -> HWGParticles.GRAY_FLARE;
            case 4 -> HWGParticles.CYAN_FLARE;
            case 3 -> HWGParticles.BROWN_FLARE;
            case 2 -> HWGParticles.BLUE_FLARE;
            case 1 -> HWGParticles.BLACK_FLARE;
            default -> HWGParticles.WHITE_FLARE;
        };
    }

    @Override
    public final void startFalling() {
        this.inGround = false;
        this.setDeltaMovement(0.0, -0.09, 0.0);
        this.life = 0;
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        this.getDefaultHitGroundSoundEvent();
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return HWGSounds.FLAREGUN;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide)
            this.remove(Entity.RemovalReason.DISCARDED);
        this.setSoundEvent(HWGSounds.FLAREGUN);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return SpawnEntityPacket.create(this);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }
}
