package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.projectiles.FireballEntity;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BrimstoneItem extends HWGGunBase {
	public BrimstoneItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(186));
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 6)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {
					FireballEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setProperties(playerentity, playerentity.pitch, playerentity.yaw, 0.0F,
							0.25F * 3.0F, 1.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity.setFireTicks(100);
					abstractarrowentity.setPunch(1);

					FireballEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.setProperties(playerentity, playerentity.pitch, playerentity.yaw + 5, 0.0F,
							0.25F * 3.0F, 1.0F);
					abstractarrowentity1.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity1.setFireTicks(100);
					abstractarrowentity1.setPunch(1);

					FireballEntity abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.setProperties(playerentity, playerentity.pitch, playerentity.yaw - 5, 0.0F,
							0.25F * 3.0F, 1.0F);
					abstractarrowentity2.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity2.setFireTicks(100);
					abstractarrowentity2.setPunch(1);

					worldIn.spawnEntity(abstractarrowentity);
					worldIn.spawnEntity(abstractarrowentity1);
					worldIn.spawnEntity(abstractarrowentity2);

					stack.damage(6, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));

				}
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.PLAYERS, 1.0F,
						1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
			}
		}
	}

	public FireballEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		FireballEntity arrowentity = new FireballEntity(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof BrimstoneItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.BRIMSTONE, passedData);
					world.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
							SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.5F);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof BrimstoneItem) {
			while (user.getStackInHand(hand).getDamage() != 0 && user.inventory.count(HWGItems.FUEL_TANK) > 0) {
				removeAmmo(HWGItems.FUEL_TANK, user);
				user.getStackInHand(hand).damage(-186, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(
				"Fuel: " + (stack.getMaxDamage() - stack.getDamage() - 6) + " / " + (stack.getMaxDamage() - 6))
						.formatted(Formatting.ITALIC));
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

	public static float getPullProgress(int useTicks) {
		float f = (float) useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}
}