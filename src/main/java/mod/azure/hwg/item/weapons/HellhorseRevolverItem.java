package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.entity.projectiles.BulletEntity;
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
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class HellhorseRevolverItem extends HWGGunBase {

	public HellhorseRevolverItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(7));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return super.canRepair(stack, ingredient);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {
					BulletEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setProperties(playerentity, playerentity.pitch, playerentity.yaw, 0.0F,
							0.25F * 3.0F, 1.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.85),
							entityLiving.getZ(), 0, 0);

					abstractarrowentity.setDamage(4.5);
					abstractarrowentity.age = 30;

					double r = RANDOM.nextDouble();
					if (r < 0.1)
						abstractarrowentity.isOnFire();

					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
				}
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0F,
						1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof HellhorseRevolverItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.PISTOL, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof HellhorseRevolverItem) {
			while (user.getStackInHand(hand).getDamage() != 0 && user.inventory.count(HWGItems.BULLETS) > 0) {
				removeAmmo(HWGItems.BULLETS, user);
				user.getStackInHand(hand).damage(-1, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
			}
		}
	}

	private void removeAmmo(Item ammo, PlayerEntity playerEntity) {
		// if (!playerEntity.isCreative()) {
		for (ItemStack item : playerEntity.inventory.main) {
			if (item.getItem() == HWGItems.BULLETS) {
				item.decrement(1);
				break;
			}
		}
		// }
	}

	public BulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
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

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
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