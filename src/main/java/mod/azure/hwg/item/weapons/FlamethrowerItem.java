package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.Clientnit;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.util.HWGItems;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class FlamethrowerItem extends Item implements IAnimatable {

	public AnimationFactory factory = new AnimationFactory(this);
	private String controllerName = "controller";

	public FlamethrowerItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(251));
	}

	private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
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
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 3)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {

					FlameFiring abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setProperties(playerentity, playerentity.pitch, playerentity.yaw, 0.0F,
							0.25F * 3.0F, 2.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity.age = 30;
					worldIn.spawnEntity(abstractarrowentity);

					FlameFiring abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.setProperties(playerentity, playerentity.pitch, playerentity.yaw + 10, 0.0F,
							0.25F * 3.0F, 2.0F);
					abstractarrowentity1.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity1.age = 30;
					worldIn.spawnEntity(abstractarrowentity1);

					FlameFiring abstractarrowentity3 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity3.setProperties(playerentity, playerentity.pitch, playerentity.yaw + 5, 0.0F,
							0.25F * 3.0F, 2.0F);
					abstractarrowentity3.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity3.age = 30;
					worldIn.spawnEntity(abstractarrowentity3);

					FlameFiring abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.setProperties(playerentity, playerentity.pitch, playerentity.yaw - 10, 0.0F,
							0.25F * 3.0F, 2.0F);
					abstractarrowentity2.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity2.age = 30;
					worldIn.spawnEntity(abstractarrowentity2);

					FlameFiring abstractarrowentity4 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity4.setProperties(playerentity, playerentity.pitch, playerentity.yaw - 5, 0.0F,
							0.25F * 3.0F, 2.0F);
					abstractarrowentity4.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.5),
							entityLiving.getZ(), 0, 0);
					abstractarrowentity4.age = 30;
					worldIn.spawnEntity(abstractarrowentity4);

					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));

				}
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.PLAYERS, 1.0F,
						1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
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
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof FlamethrowerItem) {
				if (Clientnit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.FLAMETHOWER, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof FlamethrowerItem) {
			while (user.getStackInHand(hand).getDamage() != 0 && user.inventory.count(HWGItems.FUEL_TANK) > 0) {
				removeAmmo(HWGMod.FUEL_TANK.asItem(), user);
				user.getStackInHand(hand).damage(-501, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
			}
		}
	}

	private void removeAmmo(Item ammo, PlayerEntity playerEntity) {
		if (!playerEntity.isCreative()) {
			for (ItemStack item : playerEntity.inventory.main) {
				if (item.getItem() == HWGItems.FUEL_TANK) {
					item.decrement(1);
					break;
				}
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(
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