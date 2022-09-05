package mod.azure.hwg.entity.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.compat.BWCompat;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SBulletEntity extends BulletEntity {

	public SBulletEntity(EntityType<? extends BulletEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public SBulletEntity(World world, LivingEntity owner, Float damage) {
		super(BWCompat.SILVERBULLETS, owner, world);
		bulletdamage = damage;
	}

	protected SBulletEntity(EntityType<? extends BulletEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected SBulletEntity(EntityType<? extends BulletEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}
	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().isPartOf(entity)) {
			if (!this.world.isClient) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		Entity entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.magic(this, this);
		} else {
			damageSource2 = DamageSource.magic(this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity) entity2).onAttacking(entity);
			}
		}
		if (entity.getType()
				.isIn(TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(HWGMod.MODID, "vulnerable_to_silver")))) {
			if (entity.damage(damageSource2, bulletdamage * 3)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (!this.world.isClient && entity2 instanceof LivingEntity) {
						EnchantmentHelper.onUserDamaged(livingEntity, entity2);
						EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
					}

					this.onHit(livingEntity);
					if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity
							&& entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity2).networkHandler.sendPacket(
								new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
					}
				}
			} else {
				if (!this.world.isClient) {
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		} else {
			if (entity.damage(damageSource2, bulletdamage)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (!this.world.isClient && entity2 instanceof LivingEntity) {
						EnchantmentHelper.onUserDamaged(livingEntity, entity2);
						EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
					}

					this.onHit(livingEntity);
					if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity
							&& entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity2).networkHandler.sendPacket(
								new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
					}
				}
			} else {
				if (!this.world.isClient) {
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(HWGItems.BULLETS);
	}

}