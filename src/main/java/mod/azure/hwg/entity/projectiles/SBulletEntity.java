package mod.azure.hwg.entity.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.compat.BWCompat;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SBulletEntity extends BulletEntity {

	public SBulletEntity(EntityType<? extends BulletEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public SBulletEntity(Level world, LivingEntity owner, Float damage) {
		super(BWCompat.SILVERBULLETS, owner, world);
		bulletdamage = damage;
	}

	protected SBulletEntity(EntityType<? extends BulletEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected SBulletEntity(EntityType<? extends BulletEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.ALLOWED;
		}

	}

	public SBulletEntity(Level world, double x, double y, double z) {
		super(BWCompat.SILVERBULLETS, x, y, z, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}
	
	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		Entity entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.indirectMagic(this, this);
		} else {
			damageSource2 = DamageSource.indirectMagic(this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity) entity2).setLastHurtMob(entity);
			}
		}
		if (entity.getType()
				.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, "vulnerable_to_silver")))) {
			if (entity.hurt(damageSource2, bulletdamage * 3)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (!this.level.isClientSide && entity2 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
					}

					this.doPostHurtEffects(livingEntity);
					if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player
							&& entity2 instanceof ServerPlayer && !this.isSilent()) {
						((ServerPlayer) entity2).connection.send(
								new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
					}
				}
			} else {
				if (!this.level.isClientSide) {
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		} else {
			if (entity.hurt(damageSource2, bulletdamage)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (!this.level.isClientSide && entity2 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
					}

					this.doPostHurtEffects(livingEntity);
					if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player
							&& entity2 instanceof ServerPlayer && !this.isSilent()) {
						((ServerPlayer) entity2).connection.send(
								new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
					}
				}
			} else {
				if (!this.level.isClientSide) {
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(HWGItems.BULLETS);
	}

}