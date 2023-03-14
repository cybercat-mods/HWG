package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.projectiles.FireballEntity;
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

public class BrimstoneItem extends HWGGunBase {
	public BrimstoneItem() {
		super(new Item.Properties().stacksTo(1).durability(186));
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof Player) {
			Player playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 6)) {
				playerentity.getCooldowns().addCooldown(this, 5);
				if (!worldIn.isClientSide) {
					FireballEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F,
							0.25F * 3.0F, 1.0F);
					abstractarrowentity.moveTo(entityLiving.getX(), entityLiving.getY(0.5),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity.setRemainingFireTicks(100);
					abstractarrowentity.setKnockback(1);

					FireballEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 5,
							0.0F, 0.25F * 3.0F, 1.0F);
					abstractarrowentity1.moveTo(entityLiving.getX(), entityLiving.getY(0.5),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity1.setRemainingFireTicks(100);
					abstractarrowentity1.setKnockback(1);

					FireballEntity abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 5,
							0.0F, 0.25F * 3.0F, 1.0F);
					abstractarrowentity2.moveTo(entityLiving.getX(), entityLiving.getY(0.5),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity2.setRemainingFireTicks(100);
					abstractarrowentity2.setKnockback(1);

					worldIn.addFreshEntity(abstractarrowentity);
					worldIn.addFreshEntity(abstractarrowentity1);
					worldIn.addFreshEntity(abstractarrowentity2);

					stack.hurtAndBreak(6, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));

					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), SoundEvents.FIREWORK_ROCKET_BLAST_FAR, SoundSource.PLAYERS,
							1.0F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
				}
				boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	public FireballEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		FireballEntity arrowentity = new FireballEntity(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide) {
			if (((Player) entity).getMainHandItem().getItem() instanceof BrimstoneItem) {
				if (ClientInit.reload.isDown() && selected) {
					FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.BRIMSTONE, passedData);
				}
			}
		}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof BrimstoneItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(HWGItems.FUEL_TANK) > 0) {
				removeAmmo(HWGItems.FUEL_TANK, user);
				user.getItemInHand(hand).hurtAndBreak(-186, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(),
						SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component
				.translatable(
						"Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 6) + " / " + (stack.getMaxDamage() - 6))
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("hwg.ammo.reloadfuel").withStyle(ChatFormatting.ITALIC));
	}

	public static float getArrowVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		user.startUsingItem(hand);
		return InteractionResultHolder.consume(itemStack);
	}

	public static float getPullProgress(int useTicks) {
		float f = (float) useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}
}