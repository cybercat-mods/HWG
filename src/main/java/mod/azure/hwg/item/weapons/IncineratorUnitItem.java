package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.projectiles.FlameFiring;
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
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class IncineratorUnitItem extends HWGGunBase {

	public IncineratorUnitItem() {
		super(new Item.Settings().maxCount(1).maxDamage(251));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
					SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
		return super.use(world, user, hand);
	}


	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 3)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {
					FlameFiring abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setProperties(playerentity.getPitch(), playerentity.getYaw(), 0f, 1.0f);
					abstractarrowentity.getDataTracker().set(FlameFiring.FORCED_YAW, playerentity.getYaw());
					abstractarrowentity.refreshPositionAndAngles(
							entityLiving.getX() + (switch (playerentity.getHorizontalFacing()) {
							case WEST -> -0.75F;
							case EAST -> 0.75F;
							default -> 0.0F;
							}), entityLiving.getY() + (switch (playerentity.getHorizontalFacing()) {
							case DOWN -> 0.5F;
							case UP -> -1.85F;
							default -> 0.75F;
							}), entityLiving.getZ() + (switch (playerentity.getHorizontalFacing()) {
							case NORTH -> -0.75F;
							case SOUTH -> 0.75F;
							default -> 0.0F;
							}), 0, 0);
					worldIn.spawnEntity(abstractarrowentity);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
				}
				boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	public FlameFiring createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		FlameFiring arrowentity = new FlameFiring(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof IncineratorUnitItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.FLAMETHOWER, passedData);
					world.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
							SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.5F);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof IncineratorUnitItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(HWGItems.FUEL_TANK) > 0) {
				removeAmmo(HWGItems.FUEL_TANK, user);
				user.getStackInHand(hand).damage(-501, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text
				.translatable(
						"Fuel: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
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
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
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