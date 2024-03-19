package mod.azure.hwg.entity.projectiles;

import mod.azure.azurelib.common.internal.common.network.packet.EntityPacket;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class BulletEntity extends AbstractArrow {

    protected float bulletdamage;
    public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(BulletEntity.class, EntityDataSerializers.FLOAT);

    public BulletEntity(EntityType<? extends BulletEntity> entityType, Level world) {
        super(entityType, world, new ItemStack(HWGItems.BULLETS));
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public BulletEntity(Level world, LivingEntity owner, Float damage) {
        super(HWGProjectiles.BULLETS, owner, world, new ItemStack(HWGItems.BULLETS));
        this.bulletdamage = damage;
    }

    protected BulletEntity(EntityType<? extends BulletEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected BulletEntity(EntityType<? extends BulletEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) EntityPacket.createPacket(this);
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
    public void tickDespawn() {
        if (this.tickCount >= 40) this.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(FORCED_YAW, 0f);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= 40) this.remove(Entity.RemovalReason.DISCARDED);
        if (this.level().isClientSide) {
            double x = this.getX() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D;
            double z = this.getZ() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D;
            this.level().addParticle(ParticleTypes.SMOKE, true, x, this.getY(0.5), z, 0, 0, 0);
        }
        if (getOwner() instanceof Player) setYRot(entityData.get(FORCED_YAW));
        Helper.setOnFire(this);
    }

    @Override
    public boolean isNoGravity() {
        return !this.isUnderWater();
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        this.getDefaultHitGroundSoundEvent();
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) this.remove(Entity.RemovalReason.DISCARDED);
        if (level().getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof PointedDripstoneBlock && HWGMod.config.gunconfigs.bullets_breakdripstone)
            level().destroyBlock(blockHitResult.getBlockPos(), true);
        if (level().getBlockState(blockHitResult.getBlockPos()).getBlock().defaultBlockState().is(Blocks.TNT))
            level().getBlockState(blockHitResult.getBlockPos()).setValue(TntBlock.UNSTABLE, true);
        if (level().getBlockState(blockHitResult.getBlockPos()).getBlock().defaultBlockState().is(Blocks.GLASS_PANE) || level().getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof StainedGlassPaneBlock)
            level().destroyBlock(blockHitResult.getBlockPos(), true);
        this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity) && !this.level().isClientSide)
            this.remove(Entity.RemovalReason.DISCARDED);
        var entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) damageSource2 = damageSources().arrow(this, this);
        else {
            damageSource2 = damageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity livingEntity) livingEntity.setLastHurtMob(entity);
        }
        if (entity.hurt(damageSource2, bulletdamage)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!this.level().isClientSide && entity2 instanceof LivingEntity livingEntity1) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
                    EnchantmentHelper.doPostDamageEffects(livingEntity1, livingEntity);
                    if (this.isOnFire()) livingEntity.setSecondsOnFire(50);
                }
                this.doPostHurtEffects(livingEntity);
                if (livingEntity != entity2 && livingEntity instanceof Player && entity2 instanceof ServerPlayer serverPlayer && !this.isSilent())
                    serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
        } else if (!this.level().isClientSide) this.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

}