package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BalrogItem extends HWGGunBase {

	public BalrogItem() {
		super(new Item.Properties().stacksTo(1).durability(5));
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof Player) {
			Player playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 4)
					&& !playerentity.getCooldowns().isOnCooldown(this)) {
				playerentity.getCooldowns().addCooldown(this, 25);
				if (!worldIn.isClientSide) {
					BlazeRodEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity.moveTo(entityLiving.getX(), entityLiving.getY(0.85),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity.isNoGravity();
					double r = worldIn.random.nextDouble();
					if (r < 0.1)
						abstractarrowentity.isOnFire();

					BlazeRodEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.shootFromRotation(playerentity, playerentity.getXRot() + 2, playerentity.getYRot(),
							0.0F, 1.0F * 3.0F, 1.0F);
					abstractarrowentity1.moveTo(entityLiving.getX(), entityLiving.getY(0.85),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity1.isNoGravity();
					double a = worldIn.random.nextDouble();
					if (a < 0.1)
						abstractarrowentity1.isOnFire();

					BlazeRodEntity abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 2,
							0.0F, 1.0F * 3.0F, 1.0F);
					abstractarrowentity2.moveTo(entityLiving.getX(), entityLiving.getY(0.85),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity2.isNoGravity();
					double b = worldIn.random.nextDouble();
					if (b < 0.1)
						abstractarrowentity2.isOnFire();

					BlazeRodEntity abstractarrowentity3 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity3.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 2,
							0.0F, 1.0F * 3.0F, 1.0F);
					abstractarrowentity3.moveTo(entityLiving.getX(), entityLiving.getY(0.85),
							entityLiving.getZ(), 0, 0);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					abstractarrowentity3.isNoGravity();
					double c = worldIn.random.nextDouble();
					if (c < 0.1)
						abstractarrowentity3.isOnFire();

					worldIn.addFreshEntity(abstractarrowentity);
					worldIn.addFreshEntity(abstractarrowentity1);
					worldIn.addFreshEntity(abstractarrowentity2);
					worldIn.addFreshEntity(abstractarrowentity3);
					stack.hurtAndBreak(4, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), SoundEvents.SHULKER_SHOOT, SoundSource.PLAYERS, 1.0F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
				}
				boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
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

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof BalrogItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(Items.BLAZE_ROD) > 0) {
				removeAmmo(Items.BLAZE_ROD, user);
				user.getItemInHand(hand).hurtAndBreak(-50, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(),
						SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide) {
			if (((Player) entity).getMainHandItem().getItem() instanceof BalrogItem
					&& ClientInit.reload.isDown() && selected) {
				FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(HWGMod.BALROG, passedData);
				world.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(),
						SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
		if (!(entity instanceof HWGEntity) && selected) {
			((LivingEntity) entity)
					.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 1, false, false, false));
		}
	}

	public BlazeRodEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		BlazeRodEntity arrowentity = new BlazeRodEntity(worldIn, shooter);
		return arrowentity;
	}

	public static float getPullProgress(int useTicks) {
		float f = (float) useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("hwg.ammo.reloadblazerod").withStyle(ChatFormatting.ITALIC));
	}
}