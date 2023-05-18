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
import mod.azure.hwg.client.render.weapons.SPistolRender;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SPistolItem extends AnimatedItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public SPistolItem() {
		super(new Item.Properties().stacksTo(1).durability(7));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				playerentity.getCooldowns().addCooldown(this, 5);
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					var result = HWGGunBase.hitscanTrace(playerentity, 64, 1.0F);
					if (result != null) {
						if (result.getEntity()instanceof LivingEntity livingEntity)
							livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), HWGMod.config.silenced_pistol_damage);
					} else {
						var bullet = createArrow(worldIn, stack, playerentity);
						bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 20.0F * 3.0F, 1.0F);
						bullet.tickCount = -15;
						worldIn.addFreshEntity(bullet);
					}
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), HWGSounds.SPISTOL, SoundSource.PLAYERS, 0.5F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
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
			if (((Player) entity).getMainHandItem().getItem() instanceof SPistolItem) {
				if (ClientInit.reload.isDown() && selected) {
					FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.SPISTOL, passedData);
					world.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 3.0F, 1.5F);
				}
			}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SPistolItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(HWGItems.BULLETS) > 0) {
				removeAmmo(HWGItems.BULLETS, user);
				user.getItemInHand(hand).hurtAndBreak(-1, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(), HWGSounds.PISTOLRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
				if (!user.getLevel().isClientSide)
					triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.getCommandSenderWorld()), "shoot_controller", "reload");
			}
		}
	}

	public BulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		var bullet = new BulletEntity(worldIn, shooter, HWGMod.config.silenced_pistol_damage);
		return bullet;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("hwg.ammo.reloadbullets").withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final SPistolRender renderer = new SPistolRender();

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