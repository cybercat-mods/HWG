package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BalrogItem extends HWGGunBase {

	public BalrogItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(5));
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 4)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				playerentity.getItemCooldownManager().set(this, 25);
				if (!worldIn.isClient) {
					BlazeRodEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setProperties(playerentity, playerentity.pitch, playerentity.yaw, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.85),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity.hasNoGravity();
					double r = RANDOM.nextDouble();
					if (r < 0.1)
						abstractarrowentity.isOnFire();

					BlazeRodEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.setProperties(playerentity, playerentity.pitch + 2, playerentity.yaw, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity1.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.85),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity1.hasNoGravity();
					double a = RANDOM.nextDouble();
					if (a < 0.1)
						abstractarrowentity1.isOnFire();

					BlazeRodEntity abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.setProperties(playerentity, playerentity.pitch, playerentity.yaw + 2, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity2.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.85),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity2.hasNoGravity();
					double b = RANDOM.nextDouble();
					if (b < 0.1)
						abstractarrowentity2.isOnFire();

					BlazeRodEntity abstractarrowentity3 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity3.setProperties(playerentity, playerentity.pitch, playerentity.yaw - 2, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity3.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.85),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity3.hasNoGravity();
					double c = RANDOM.nextDouble();
					if (c < 0.1)
						abstractarrowentity3.isOnFire();

					worldIn.spawnEntity(abstractarrowentity);
					worldIn.spawnEntity(abstractarrowentity1);
					worldIn.spawnEntity(abstractarrowentity2);
					worldIn.spawnEntity(abstractarrowentity3);
					stack.damage(4, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
				}
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0F,
						1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
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
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof BalrogItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0 && user.inventory.count(Items.BLAZE_ROD) > 0) {
				removeAmmo(Items.BLAZE_ROD, user);
				user.getStackInHand(hand).damage(-50, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof BalrogItem
					&& ClientInit.reload.isPressed() && selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(HWGMod.BALROG, passedData);
				world.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
						SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.5F);
			}
		}
		if (!(entity instanceof HWGEntity) && selected) {
			((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5, 1));
		}
	}

	public BlazeRodEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
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
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(new TranslatableText("hwg.ammo.reloadblazerod").formatted(Formatting.ITALIC));
	}
}