package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.google.common.collect.Lists;

import mod.azure.hwg.client.render.weapons.FlareGunRender;
import mod.azure.hwg.entity.projectiles.BaseFlareEntity;
import mod.azure.hwg.item.ammo.FlareItem;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;

public class FlareGunItem extends HWGGunLoadedBase implements GeoItem {

	private boolean charged = false;
	private boolean loaded = false;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

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
		super(new Item.Settings().maxCount(1).maxDamage(31));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE)
				.triggerableAnim("firing", RawAnimation.begin().then("firing", LoopType.PLAY_ONCE))
				.triggerableAnim("loading", RawAnimation.begin().then("loading", LoopType.PLAY_ONCE)));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
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
			BaseFlareEntity flareEntity = new BaseFlareEntity(world, projectile, shooter, shooter.getX(),
					shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
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
				flareEntity.setColor(1);
			} else if (b2) {
				flareEntity.setColor(2);
			} else if (b3) {
				flareEntity.setColor(3);
			} else if (b4) {
				flareEntity.setColor(4);
			} else if (b5) {
				flareEntity.setColor(5);
			} else if (b6) {
				flareEntity.setColor(6);
			} else if (b7) {
				flareEntity.setColor(7);
			} else if (b8) {
				flareEntity.setColor(8);
			} else if (b9) {
				flareEntity.setColor(9);
			} else if (b10) {
				flareEntity.setColor(10);
			} else if (b11) {
				flareEntity.setColor(11);
			} else if (b12) {
				flareEntity.setColor(12);
			} else if (b13) {
				flareEntity.setColor(13);
			} else if (b14) {
				flareEntity.setColor(14);
			} else if (b15) {
				flareEntity.setColor(15);
			} else {
				flareEntity.setColor(16);
			}

			Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
			Quaternionf quaternionf = new Quaternionf().setAngleAxis((double) (simulated * ((float) Math.PI / 180)),
					vec3d.x, vec3d.y, vec3d.z);
			Vec3d vec3d2 = shooter.getRotationVec(1.0f);
			Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);
			vector3f.rotate(quaternionf);
			((ProjectileEntity) flareEntity).setVelocity((double) vector3f.x, (double) vector3f.y, (double) vector3f.z,
					speed, divergence);
			((PersistentProjectileEntity) flareEntity).setDamage(0.3D);
			((PersistentProjectileEntity) flareEntity).pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
			stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
			world.spawnEntity((Entity) flareEntity);
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
			if (!world.isClient) {
				triggerAnim(user, GeoItem.getOrAssignId(itemStack, (ServerWorld) world), "shoot_controller", "firing");
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
			world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_CHAIN_BREAK,
					SoundCategory.PLAYERS, 1.0F, 1.5F);
			if (!world.isClient) {
				triggerAnim((PlayerEntity) user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "shoot_controller",
						"loading");
			}
			((PlayerEntity) user).getItemCooldownManager().set(this, 15);
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
		NbtCompound NbtCompound = stack.getNbt();
		return NbtCompound != null && NbtCompound.getBoolean("Charged");
	}

	public static void setCharged(ItemStack stack, boolean charged) {
		NbtCompound NbtCompound = stack.getOrCreateNbt();
		NbtCompound.putBoolean("Charged", charged);
	}

	private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
		NbtCompound NbtCompound = crossbow.getOrCreateNbt();
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
		NbtCompound NbtCompound = crossbow.getNbt();
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
		NbtCompound NbtCompound = crossbow.getNbt();
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
		return new float[] { 1.0F, getSoundPitch(bl, random), getSoundPitch(!bl, random) };
	}

	private static float getSoundPitch(boolean flag, Random random) {
		float f = flag ? 0.63F : 0.43F;
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
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
			tooltip.add((Text.translatable("Ammo")).append(" ").append(itemStack.toHoverableText()));
			if (context.isAdvanced() && itemStack.getItem() == FLARE) {
				List<Text> list2 = Lists.newArrayList();
				HWGItems.G_EMP.appendTooltip(itemStack, world, list2, context);
				if (!list2.isEmpty()) {
					for (int i = 0; i < list2.size(); ++i) {
						list2.set(i, (Text.literal("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
					}
					tooltip.addAll(list2);
				}
			}
		}
		tooltip.add(Text.translatable("hwg.ammo.reloadflares").formatted(Formatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final FlareGunRender renderer = new FlareGunRender();

			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
