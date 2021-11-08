package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.launcher.EMPGEntity;
import mod.azure.hwg.entity.projectiles.launcher.FragGEntity;
import mod.azure.hwg.entity.projectiles.launcher.NapalmGEntity;
import mod.azure.hwg.entity.projectiles.launcher.SmokeGEntity;
import mod.azure.hwg.entity.projectiles.launcher.StunGEntity;
import mod.azure.hwg.item.ammo.GrenadeEmpItem;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GrenadeLauncherItem extends HWGGunLoadedBase implements IAnimatable, ISyncable {

	private boolean charged = false;
	private boolean loaded = false;
	public AnimationFactory factory = new AnimationFactory(this);
	public String controllerName = "controller";
	public static final int ANIM_OPEN = 0;
	public static final int ANIM_CLOSE = 1;

	public static final Predicate<ItemStack> EMP = (stack) -> {
		return stack.getItem() == HWGItems.G_EMP;
	};
	public static final Predicate<ItemStack> GRENADES = EMP.or((stack) -> {
		return stack.getItem() == HWGItems.G_FRAG;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.G_NAPALM;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.G_SMOKE;
	}).or((stack) -> {
		return stack.getItem() == HWGItems.G_STUN;
	});

	public GrenadeLauncherItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup).maxCount(1).maxDamage(31));
		GeckoLibNetwork.registerSyncable(this);
	}

	public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
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
	public void onAnimationSync(int id, int state) {
		if (state == ANIM_OPEN) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
			}
		}
		if (state == ANIM_CLOSE) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("loading", false));
			}
		}
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public Predicate<ItemStack> getHeldProjectiles() {
		return GRENADES;
	}

	@Override
	public Predicate<ItemStack> getProjectiles() {
		return GRENADES;
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return ToolMaterials.IRON.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack stack, ItemStack projectile,
			float soundPitch, boolean creative, float speed, float divergence, float simulated) {
		if (!world.isClient) {
			boolean bl = projectile.getItem() == HWGItems.G_EMP;
			boolean b2 = projectile.getItem() == HWGItems.G_FRAG;
			boolean b3 = projectile.getItem() == HWGItems.G_NAPALM;
			boolean b4 = projectile.getItem() == HWGItems.G_STUN;
			Object projectileEntity2;
			if (bl) {
				projectileEntity2 = new EMPGEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b2) {
				projectileEntity2 = new FragGEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b3) {
				projectileEntity2 = new NapalmGEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else if (b4) {
				projectileEntity2 = new StunGEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else {
				projectileEntity2 = new SmokeGEntity(world, projectile, shooter, shooter.getX(),
						shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			}
			Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
			Quaternion quaternion = new Quaternion(new Vec3f(vec3d), simulated, true);
			Vec3d vec3d2 = shooter.getRotationVec(1.0F);
			Vec3f vector3f = new Vec3f(vec3d2);
			vector3f.rotate(quaternion);
			((PersistentProjectileEntity) projectileEntity2).setVelocity((double) vector3f.getX(),
					(double) vector3f.getY(), (double) vector3f.getZ(), speed, divergence);

			stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
			world.spawnEntity((Entity) projectileEntity2);

			world.playSound((PlayerEntity) null, shooter.getX(), shooter.getY(), shooter.getZ(),
					HWGSounds.GLAUNCHERFIRE, SoundCategory.PLAYERS, 0.5F, soundPitch);
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
			shootAll(world, user, hand, itemStack, getSpeed(itemStack), 1.0F);
			user.getItemCooldownManager().set(this, 25);
			setCharged(itemStack, false);
			if (!world.isClient) {
				final int id = GeckoLibUtil.guaranteeIDForStack(itemStack, (ServerWorld) world);
				GeckoLibNetwork.syncAnimation(user, this, id, ANIM_OPEN);
				for (PlayerEntity otherPlayer : PlayerLookup.tracking(user)) {
					GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
				}
			}
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
			SoundCategory soundCategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
			world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), HWGSounds.GLAUNCHERRELOAD,
					soundCategory, 0.5F, 1.0F);
			if (!world.isClient) {
				final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
				GeckoLibNetwork.syncAnimation((PlayerEntity) user, this, id, ANIM_CLOSE);
				for (PlayerEntity otherPlayer : PlayerLookup.tracking((PlayerEntity) user)) {
					GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_CLOSE);
				}
			}
			((PlayerEntity) user).getItemCooldownManager().set(this, 15);
		}
	}

	private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
		int i = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
		int j = i == 0 ? 1 : 3;
		boolean bl = shooter instanceof PlayerEntity && ((PlayerEntity) shooter).abilities.creativeMode;
		ItemStack itemStack = shooter.getArrowType(projectile);
		ItemStack itemStack2 = itemStack.copy();

		for (int k = 0; k < j; ++k) {
			if (k > 0) {
				itemStack = itemStack2.copy();
			}

			if (itemStack.isEmpty() && bl) {
				itemStack = new ItemStack(HWGItems.G_SMOKE);
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
			boolean bl = creative && projectile.getItem() instanceof GrenadeEmpItem;
			ItemStack itemStack2;
			if (!bl && !creative && !simulated) {
				itemStack2 = projectile.split(1);
				if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
					((PlayerEntity) shooter).inventory.removeOne(projectile);
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
		NbtList listTag2;
		if (NbtCompound.contains("ChargedProjectiles", 9)) {
			listTag2 = NbtCompound.getList("ChargedProjectiles", 10);
		} else {
			listTag2 = new NbtList();
		}

		NbtCompound NbtCompound2 = new NbtCompound();
		projectile.writeNbt(NbtCompound2);
		listTag2.add(NbtCompound2);
		NbtCompound.put("ChargedProjectiles", listTag2);
	}

	private static List<ItemStack> getProjectiles(ItemStack crossbow) {
		List<ItemStack> list = Lists.newArrayList();
		NbtCompound NbtCompound = crossbow.getTag();
		if (NbtCompound != null && NbtCompound.contains("ChargedProjectiles", 9)) {
			NbtList listTag = NbtCompound.getList("ChargedProjectiles", 10);
			if (listTag != null) {
				for (int i = 0; i < listTag.size(); ++i) {
					NbtCompound NbtCompound2 = listTag.getCompound(i);
					list.add(ItemStack.fromNbt(NbtCompound2));
				}
			}
		}
		return list;
	}

	private static void clearProjectiles(ItemStack crossbow) {
		NbtCompound NbtCompound = crossbow.getTag();
		if (NbtCompound != null) {
			NbtList listTag = NbtCompound.getList("ChargedProjectiles", 9);
			listTag.clear();
			NbtCompound.put("ChargedProjectiles", listTag);
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
		float[] fs = getSoundPitches(entity.getRandom());

		for (int i = 0; i < list.size(); ++i) {
			ItemStack itemStack = (ItemStack) list.get(i);
			boolean bl = entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.creativeMode;
			if (!itemStack.isEmpty()) {
				if (i == 0) {
					shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 0.0F);
				} else if (i == 1) {
					shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, -10.0F);
				} else if (i == 2) {
					shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 10.0F);
				}
			}
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
		return 25 - 5 * i;
	}

	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		List<ItemStack> list = getProjectiles(stack);
		if (isCharged(stack) && !list.isEmpty()) {
			ItemStack itemStack = (ItemStack) list.get(0);
			tooltip.add((new TranslatableText("Ammo")).append(" ").append(itemStack.toHoverableText()));
			if (context.isAdvanced() && itemStack.getItem() == GRENADES) {
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
		tooltip.add(new TranslatableText("hwg.ammo.reloadgrenades").formatted(Formatting.ITALIC));
	}

	private static float getSpeed(ItemStack stack) {
		return stack.getItem() == Items.CROSSBOW && hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
	}

}
