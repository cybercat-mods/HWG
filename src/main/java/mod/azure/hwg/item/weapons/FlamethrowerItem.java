package mod.azure.hwg.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.Clientnit;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.ProjectilesEntityRegister;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class FlamethrowerItem extends Item implements IAnimatable {

	public AnimationFactory factory = new AnimationFactory(this);
	private String controllerName = "controller";

	public FlamethrowerItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(7));
	}

	private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
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
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {

//					double e1 = Math.max(entityLiving.getY(), playerentity.getY()) + 1.0D;
//					float f2 = (float) MathHelper.atan2(entityLiving.getZ() - playerentity.getZ(),
//							playerentity.getX() - playerentity.getX());
//					double d = Math.min(entityLiving.getY(), playerentity.getY());
//					int j;
//					for (j = 0; j < 16; ++j) {
//						double l1 = 1.25D * (double) (j + 1);
//						int m = 1 * j;
						FlameFiring lost_soul = ProjectilesEntityRegister.FIRING.create(worldIn);
						lost_soul.updatePosition(entityLiving.getX(), entityLiving.getY(), entityLiving.getZ());
						Vec3d vec3d = lost_soul.getVelocity();
						lost_soul.setVelocity(vec3d.multiply((double) 0.99F));
						worldIn.spawnEntity(lost_soul);
//					}

					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
				}
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F,
						1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
			}
			AnimationController<?> controller = GeckoLibUtil.getControllerForStack(this.factory, stack, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof FlamethrowerItem) {
				if (Clientnit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(HWGMod.PISTOL, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof FlamethrowerItem) {
			while (user.getStackInHand(hand).getDamage() != 0 && user.inventory.count(HWGItems.BULLETS) > 0) {
				removeAmmo(HWGItems.BULLETS, user);
				user.getStackInHand(hand).damage(-1, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
			}
		}
	}

	private void removeAmmo(Item ammo, PlayerEntity playerEntity) {
		if (!playerEntity.isCreative()) {
			for (ItemStack item : playerEntity.inventory.main) {
				if (item.getItem() == HWGItems.BULLETS) {
					item.decrement(1);
					break;
				}
			}
		}
	}

	public FlameFiring spawnFlames(LivingEntity shooter, double x, double z, double maxY, double y, float yaw,
			int warmup) {
		BlockPos blockPos = new BlockPos(x, y, z);
		double d = -0.75D;
		FlameFiring fang = new FlameFiring(shooter.world, x, (double) blockPos.getY() + d, z, yaw, warmup, shooter);
		return fang;
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