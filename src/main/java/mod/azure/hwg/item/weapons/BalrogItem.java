package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.Keybindings;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
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
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < (stack.getMaxDamage() - 4) && !playerentity.getCooldowns().isOnCooldown(this)) {
				playerentity.getCooldowns().addCooldown(this, 25);
				if (!worldIn.isClientSide) {
					var rod = createArrow(worldIn, stack, playerentity);
					rod.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
					rod.moveTo(entityLiving.getX(), entityLiving.getY(0.85), entityLiving.getZ(), 0, 0);
					rod.isNoGravity();
					double r = worldIn.random.nextDouble();
					if (r < 0.1)
						rod.isOnFire();

					var rod1 = createArrow(worldIn, stack, playerentity);
					rod1.shootFromRotation(playerentity, playerentity.getXRot() + 2, playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
					rod1.moveTo(entityLiving.getX(), entityLiving.getY(0.85), entityLiving.getZ(), 0, 0);
					rod1.isNoGravity();
					double a = worldIn.random.nextDouble();
					if (a < 0.1)
						rod1.isOnFire();

					var rod2 = createArrow(worldIn, stack, playerentity);
					rod2.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 2, 0.0F, 1.0F * 3.0F, 1.0F);
					rod2.moveTo(entityLiving.getX(), entityLiving.getY(0.85), entityLiving.getZ(), 0, 0);
					rod2.isNoGravity();
					double b = worldIn.random.nextDouble();
					if (b < 0.1)
						rod2.isOnFire();

					var rod3 = createArrow(worldIn, stack, playerentity);
					rod3.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 2, 0.0F, 1.0F * 3.0F, 1.0F);
					rod3.moveTo(entityLiving.getX(), entityLiving.getY(0.85), entityLiving.getZ(), 0, 0);
					rod3.isNoGravity();
					double c = worldIn.random.nextDouble();
					if (c < 0.1)
						rod3.isOnFire();

					worldIn.addFreshEntity(rod);
					worldIn.addFreshEntity(rod1);
					worldIn.addFreshEntity(rod2);
					worldIn.addFreshEntity(rod3);
					stack.hurtAndBreak(4, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.SHULKER_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
				}
				var isInsideWaterBlock = playerentity.level().isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof BalrogItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Items.BLAZE_ROD) > 0) {
				removeAmmo(Items.BLAZE_ROD, user);
				user.getItemInHand(hand).hurtAndBreak(-50, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				if (!user.getCooldowns().isOnCooldown(user.getItemInHand(hand).getItem()))
					user.level().playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
				user.level().playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (entity instanceof Player player)
				if (player.getMainHandItem().getItem() instanceof BalrogItem)
					if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
						var passedData = new FriendlyByteBuf(Unpooled.buffer());
						passedData.writeBoolean(true);
						ClientPlayNetworking.send(HWGMod.BALROG, passedData);
					}
		if (!(entity instanceof HWGEntity) && selected)
			if (entity instanceof LivingEntity living)
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 1, false, false, false));
	}

	public BlazeRodEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		var rod = new BlazeRodEntity(worldIn, shooter);
		return rod;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("hwg.ammo.reloadblazerod").withStyle(ChatFormatting.ITALIC));
	}
}