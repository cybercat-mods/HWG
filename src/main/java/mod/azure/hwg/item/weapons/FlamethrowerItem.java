package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FlamethrowerItem extends HWGGunBase {

	public FlamethrowerItem() {
		super(new Item.Properties().tab(HWGMod.WeaponItemGroup).stacksTo(1).durability(251));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (!world.isClientSide)
			world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.FLINTANDSTEEL_USE,
					SoundSource.PLAYERS, 1.0F, 1.0F);
		return super.use(world, user, hand);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player) {
			var playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 3)) {
				playerentity.getCooldowns().addCooldown(this, 5);
				if (!worldIn.isClientSide) {
					var flames = createArrow(worldIn, stack, playerentity);
					flames.setProperties(playerentity.getXRot(), playerentity.getYRot(), 0f, 1.5f);
					flames.getEntityData().set(FlameFiring.FORCED_YAW, playerentity.getYRot());
					flames.moveTo(entityLiving.getX() + (switch (playerentity.getDirection()) {
					case WEST -> -0.5F;
					case EAST -> 0.5F;
					default -> 0.0F;
					}), entityLiving.getY() + (switch (playerentity.getDirection()) {
					case DOWN -> 0.5F;
					case UP -> -1.85F;
					default -> 0.75F;
					}), entityLiving.getZ() + (switch (playerentity.getDirection()) {
					case NORTH -> -0.5F;
					case SOUTH -> 0.5F;
					default -> 0.0F;
					}), 0, 0);
					worldIn.addFreshEntity(flames);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
				}
				var isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	public FlameFiring createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		var flames = new FlameFiring(worldIn, shooter);
		return flames;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (((Player) entity).getMainHandItem().getItem() instanceof FlamethrowerItem) {
				if (ClientInit.reload.isDown() && selected) {
					FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.FLAMETHOWER, passedData);
					world.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(),
							SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
				}
			}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof FlamethrowerItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(HWGItems.FUEL_TANK) > 0) {
				removeAmmo(HWGItems.FUEL_TANK, user);
				user.getItemInHand(hand).hurtAndBreak(-501, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable(
				"Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1))
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("hwg.ammo.reloadfuel").withStyle(ChatFormatting.ITALIC));
	}
}