package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.flare.BlackFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.BlueFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.BrownFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.CyanFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.GrayFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.GreenFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.LightblueFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.LightgrayFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.LimeFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.MagentaFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.OrangeFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.PinkFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.PurpleFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.RedFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.WhiteFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.YellowFlareEntity;
import mod.azure.hwg.item.ammo.FlareItem;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class FlareGunItem extends HWGGunLoadedBase {

	private boolean charged = false;
	private boolean loaded = false;
	protected static final Random RANDOM = new Random();

	public static final Predicate<ItemStack> BLACK_FLARE = (stack) -> {
		return stack.getItem() == HWGItems.BLACK_FLARE;
	};

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	public static final Predicate<ItemStack> FLARE = BLACK_FLARE.or((stack) -> {
		return stack.getItem() == HWGItems.BLUE_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.BROWN_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.CYAN_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.GRAY_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.GREEN_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.LIGHTBLUE_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.LIGHTGRAY_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.LIME_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.MAGENTA_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.ORANGE_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.PINK_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.PURPLE_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.RED_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.WHITE_FLARE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.YELLOW_FLARE;
	});

	public FlareGunItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(31));
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return ToolMaterials.IRON.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public Predicate<ItemStack> getHeldProjectiles() {
		return FLARE;
	}

	@Override
	public Predicate<ItemStack> getProjectiles() {
		return FLARE;
	}

	private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack stack, ItemStack projectile,
			float soundPitch, boolean creative, float speed, float divergence, float simulated) {
		if (!world.isClient) {
			Object projectileEntity2;
			boolean bl = projectile.getItem() == HWGItems.BLACK_FLARE;
			boolean b2 = projectile.getItem() == HWGItems.BLUE_FLARE;
			boolean b3 = projectile.getItem() == HWGItems.BROWN_FLARE;
			boolean b4 = projectile.getItem() == HWGItems.CYAN_FLARE;
			boolean b5 = projectile.getItem() == HWGItems.GRAY_FLARE;
			boolean b6 = projectile.getItem() == HWGItems.GREEN_FLARE;
			boolean b7 = projectile.getItem() == HWGItems.LIGHTBLUE_FLARE;
			boolean b8 = projectile.getItem() == HWGItems.LIGHTGRAY_FLARE;
			boolean b9 = projectile.getItem() == HWGItems.LIME_FLARE;
			boolean b10 = projectile.getItem() == HWGItems.MAGENTA_FLARE;
			boolean b11 = projectile.getItem() == HWGItems.ORANGE_FLARE;
			boolean b12 = projectile.getItem() == HWGItems.PINK_FLARE;
			boolean b13 = projectile.getItem() == HWGItems.PURPLE_FLARE;
			boolean b14 = projectile.getItem() == HWGItems.RED_FLARE;
			boolean b15 = projectile.getItem() == HWGItems.YELLOW_FLARE;
			if (bl) {
				projectileEntity2 = new BlackFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b2) {
				projectileEntity2 = new BlueFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b3) {
				projectileEntity2 = new BrownFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b4) {
				projectileEntity2 = new CyanFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b5) {
				projectileEntity2 = new GrayFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b6) {
				projectileEntity2 = new GreenFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b7) {
				projectileEntity2 = new LightblueFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b8) {
				projectileEntity2 = new LightgrayFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b9) {
				projectileEntity2 = new LimeFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b10) {
				projectileEntity2 = new MagentaFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b11) {
				projectileEntity2 = new OrangeFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b12) {
				projectileEntity2 = new PinkFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b13) {
				projectileEntity2 = new PurpleFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b14) {
				projectileEntity2 = new RedFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b15) {
				projectileEntity2 = new YellowFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else {
				projectileEntity2 = new WhiteFlareEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			}

			Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
			Quaternion quaternion = new Quaternion(new Vec3f(vec3d), simulated, true);
			Vec3d vec3d2 = shooter.getRotationVec(1.0F);
			Vec3f vector3f = new Vec3f(vec3d2);
			vector3f.rotate(quaternion);
			((ProjectileEntity) projectileEntity2).setVelocity((double) vector3f.getX(), (double) vector3f.getY(),
					(double) vector3f.getZ(), speed, divergence);
			((PersistentProjectileEntity) projectileEntity2).setDamage(0.3D);
			((PersistentProjectileEntity) projectileEntity2).pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
			stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
			world.spawnEntity((Entity) projectileEntity2);
		}
	}

	@Override
	public int getRange() {
		return 16;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (isCharged(itemStack) && itemStack.getDamage() < (itemStack.getMaxDamage() - 1)
				&& !user.getItemCooldownManager().isCoolingDown(this)) {
			shootAll(world, user, hand, itemStack, 2.6F, 1.0F);
			user.getItemCooldownManager().set(this, 25);
			setCharged(itemStack, false);
			return TypedActionResult.consume(itemStack);
		} else if (!user.getArrowType(itemStack).isEmpty()) {
			if (!isCharged(itemStack)) {
				this.charged = false;
				this.loaded = false;
				user.setCurrentHand(hand);
			}
			return TypedActionResult.consume(itemStack);
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (!isCharged(stack) && loadProjectiles(user, stack)) {
			setCharged(stack, true);
			world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_CHAIN_BREAK,
					SoundCategory.PLAYERS, 1.0F, 1.5F);
		}
	}

	private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
		int i = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
		int j = i == 0 ? 1 : 3;
		boolean bl = shooter instanceof PlayerEntity && ((PlayerEntity) shooter).getAbilities().creativeMode;
		ItemStack itemStack = shooter.getArrowType(projectile);
		ItemStack itemStack2 = itemStack.copy();

		for (int k = 0; k < j; ++k) {
			if (k > 0) {
				itemStack = itemStack2.copy();
			}

			if (itemStack.isEmpty() && bl) {
				itemStack = new ItemStack((ItemConvertible) FLARE);
				itemStack2 = itemStack.copy();
			}

			if (!loadProjectile(shooter, projectile, itemStack, k > 0, bl)) {
				return false;
			}
		}
		return true;
	}

	private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile,
			boolean simulated, boolean creative) {
		if (projectile.isEmpty()) {
			return false;
		} else {
			boolean bl = creative && projectile.getItem() instanceof FlareItem;
			ItemStack itemStack2;
			if (!bl && !creative && !simulated) {
				itemStack2 = projectile.split(1);
				if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
					((PlayerEntity) shooter).getInventory().removeOne(projectile);
				}
			} else {
				itemStack2 = projectile.copy();
			}

			putProjectile(crossbow, itemStack2);
			return true;
		}
	}

	public static boolean isCharged(ItemStack stack) {
		NbtCompound NbtCompound = stack.getTag();
		return NbtCompound != null && NbtCompound.getBoolean("Charged");
	}

	public static void setCharged(ItemStack stack, boolean charged) {
		NbtCompound NbtCompound = stack.getOrCreateTag();
		NbtCompound.putBoolean("Charged", charged);
	}

	private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
		NbtCompound NbtCompound = crossbow.getOrCreateTag();
		NbtList NbtList2;
		if (NbtCompound.contains("ChargedProjectiles", 9)) {
			NbtList2 = NbtCompound.getList("ChargedProjectiles", 10);
		} else {
			NbtList2 = new NbtList();
		}

		NbtCompound NbtCompound2 = new NbtCompound();
		projectile.writeNbt(NbtCompound2);
		NbtList2.add(NbtCompound2);
		NbtCompound.put("ChargedProjectiles", NbtList2);
	}

	private static List<ItemStack> getProjectiles(ItemStack crossbow) {
		List<ItemStack> list = Lists.newArrayList();
		NbtCompound NbtCompound = crossbow.getTag();
		if (NbtCompound != null && NbtCompound.contains("ChargedProjectiles", 9)) {
			NbtList NbtList = NbtCompound.getList("ChargedProjectiles", 10);
			if (NbtList != null) {
				for (int i = 0; i < NbtList.size(); ++i) {
					NbtCompound NbtCompound2 = NbtList.getCompound(i);
					list.add(ItemStack.fromNbt(NbtCompound2));
				}
			}
		}
		return list;
	}

	private static void clearProjectiles(ItemStack crossbow) {
		NbtCompound NbtCompound = crossbow.getTag();
		if (NbtCompound != null) {
			NbtList NbtList = NbtCompound.getList("ChargedProjectiles", 9);
			NbtList.clear();
			NbtCompound.put("ChargedProjectiles", NbtList);
		}
	}

	public static boolean hasProjectile(ItemStack crossbow, Item projectile) {
		return getProjectiles(crossbow).stream().anyMatch((s) -> {
			return s.getItem() == projectile;
		});
	}

	public static void shootAll(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed,
			float divergence) {
		List<ItemStack> list = getProjectiles(stack);
		float[] fs = getSoundPitches(entity.world.random);

		for (int i = 0; i < list.size(); ++i) {
			ItemStack itemStack = (ItemStack) list.get(i);
			boolean bl = entity instanceof PlayerEntity && ((PlayerEntity) entity).getAbilities().creativeMode;
			shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 0.0F);
		}
		postShoot(world, entity, stack);
	}

	private static float[] getSoundPitches(Random random) {
		boolean bl = random.nextBoolean();
		return new float[] { 1.0F, getSoundPitch(bl), getSoundPitch(!bl) };
	}

	private static float getSoundPitch(boolean flag) {
		float f = flag ? 0.63F : 0.43F;
		return 1.0F / (RANDOM.nextFloat() * 0.5F + 1.8F) + f;
	}

	private static void postShoot(World world, LivingEntity entity, ItemStack stack) {
		clearProjectiles(stack);
	}

	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (!world.isClient) {
			float f = (float) (stack.getMaxUseTime() - remainingUseTicks) / (float) getPullTime(stack);
			if (f < 0.2F) {
				this.charged = false;
				this.loaded = false;
			}

			if (f >= 0.2F && !this.charged) {
				this.charged = true;
			}

			if (f >= 0.5F && !this.loaded) {
				this.loaded = true;
			}
		}
	}

	public int getMaxUseTime(ItemStack stack) {
		return getPullTime(stack) + 3000;
	}

	public static int getPullTime(ItemStack stack) {
		int i = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
		return i == 0 ? 25 : 25 - 5 * i;
	}

	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		List<ItemStack> list = getProjectiles(stack);
		if (isCharged(stack) && !list.isEmpty()) {
			ItemStack itemStack = (ItemStack) list.get(0);
			tooltip.add((new TranslatableText("Ammo")).append(" ").append(itemStack.toHoverableText()));
			if (context.isAdvanced() && itemStack.getItem() == FLARE) {
				List<Text> list2 = Lists.newArrayList();
				HWGItems.G_EMP.appendTooltip(itemStack, world, list2, context);
				if (!list2.isEmpty()) {
					for (int i = 0; i < list2.size(); ++i) {
						list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
					}
					tooltip.addAll(list2);
				}
			}
		}
	}

}
