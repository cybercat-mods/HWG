package mod.azure.hwg.entity.projectiles;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SBulletEntity extends BulletEntity {

    public SBulletEntity(EntityType<? extends BulletEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public SBulletEntity(Level world, LivingEntity owner, Float damage) {
        super(HWGProjectiles.SILVERBULLETS.get(), world);
        bulletdamage = damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity) && !this.level().isClientSide)
            this.remove(RemovalReason.DISCARDED);
        var entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) damageSource2 = damageSources().indirectMagic(this, this);
        else {
            damageSource2 = damageSources().indirectMagic(this, entity2);
            if (entity2 instanceof LivingEntity livingEntity) livingEntity.setLastHurtMob(entity);
        }
        if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, CommonMod.modResource("vulnerable_to_silver")))) {
            if (entity.hurt(damageSource2, bulletdamage * 3)) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (!this.level().isClientSide && entity2 instanceof LivingEntity && this.isOnFire()) livingEntity.setRemainingFireTicks(50);


                    this.doPostHurtEffects(livingEntity);
                    if (livingEntity != entity2 && livingEntity instanceof Player && entity2 instanceof ServerPlayer && !this.isSilent())
                        ((ServerPlayer) entity2).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            } else if (!this.level().isClientSide) this.remove(RemovalReason.DISCARDED);
        } else {
            if (entity.hurt(damageSource2, bulletdamage)) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (!this.level().isClientSide && entity2 instanceof LivingEntity && this.isOnFire()) livingEntity.setRemainingFireTicks(50);

                    this.doPostHurtEffects(livingEntity);
                    if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player && entity2 instanceof ServerPlayer && !this.isSilent())
                        ((ServerPlayer) entity2).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            } else if (!this.level().isClientSide) this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return new ItemStack(HWGItems.BULLETS.get());
    }

}