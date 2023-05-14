package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.client.render.weapons.ShotgunRender;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ShotgunItem extends AnimatedItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public ShotgunItem() {
		super(new Item.Properties().stacksTo(1).durability(3));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				playerentity.getCooldowns().addCooldown(this, 18);
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					var result = HWGGunBase.hitscanTrace(playerentity, 64, 1.0F);
					if (result != null) {
						if (result.getEntity()instanceof LivingEntity livingEntity) {
							livingEntity.invulnerableTime = 0;
							livingEntity.setDeltaMovement(0, 0, 0);
							livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), HWGMod.config.shotgun_damage);
							livingEntity.invulnerableTime = 0;
							livingEntity.setDeltaMovement(0, 0, 0);
							livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), HWGMod.config.shotgun_damage);
						}
					} else {
						var bullet = createArrow(worldIn, stack, playerentity);
						bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 1, 0.5F, 20.0F * 3.0F, 1.0F);
						var bullet1 = createArrow(worldIn, stack, playerentity);
						bullet1.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 1, 0.5F, 20.0F * 3.0F, 1.0F);
						bullet.tickCount = -15;
						bullet1.tickCount = -15;
						worldIn.addFreshEntity(bullet);
						worldIn.addFreshEntity(bullet1);
					}
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), HWGSounds.SHOTGUN, SoundSource.PLAYERS, 1.25F, 1.3F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
				}
				var isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (((Player) entity).getMainHandItem().getItem() instanceof ShotgunItem) {
				if (ClientInit.reload.isDown() && selected) {
					FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.SHOTGUN, passedData);
				}
			}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof ShotgunItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(HWGItems.SHOTGUN_SHELL) > 0) {
				removeAmmo(HWGItems.SHOTGUN_SHELL, user);
				user.getItemInHand(hand).hurtAndBreak(-1, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(), HWGSounds.SHOTGUNRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	public ShellEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		var bullet = new ShellEntity(worldIn, shooter);
		return bullet;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("hwg.ammo.reloadshells").withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final ShotgunRender renderer = new ShotgunRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}
}