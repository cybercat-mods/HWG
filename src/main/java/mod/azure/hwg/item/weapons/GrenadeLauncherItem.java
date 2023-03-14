package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import com.google.common.collect.Lists;

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
import mod.azure.hwg.client.render.weapons.GrenadeLauncherRender;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import mod.azure.hwg.item.ammo.GrenadeEmpItem;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class GrenadeLauncherItem extends HWGGunLoadedBase implements GeoItem {

	private boolean charged = false;
	private boolean loaded = false;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

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
		super(new Item.Properties().stacksTo(1).durability(31));
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
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public Predicate<ItemStack> getSupportedHeldProjectiles() {
		return GRENADES;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return GRENADES;
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
		return Tiers.IRON.getRepairIngredient().test(ingredient) || super.isValidRepairItem(stack, ingredient);
	}

	private static void shoot(Level world, LivingEntity shooter, InteractionHand hand, ItemStack stack,
			ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
		if (!world.isClientSide) {
			var emp = projectile.getItem() == HWGItems.G_EMP;
			var frag = projectile.getItem() == HWGItems.G_FRAG;
			var napalm = projectile.getItem() == HWGItems.G_NAPALM;
			var stun = projectile.getItem() == HWGItems.G_STUN;
			var nade = new GrenadeEntity(world, projectile, shooter, shooter.getX(),
					shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			nade.setState(0);
			if (emp) {
				nade.setVariant(1);
			} else if (frag) {
				nade.setVariant(2);
			} else if (napalm) {
				nade.setVariant(3);
			} else if (stun) {
				nade.setVariant(5);
			} else {
				nade.setVariant(4);
			}
			var vec3d = shooter.getUpVector(1.0F);
			var quaternionf = new Quaternionf().setAngleAxis((double) (simulated * ((float) Math.PI / 180)), vec3d.x,
					vec3d.y, vec3d.z);
			var vec3d2 = shooter.getViewVector(1.0f);
			var vector3f = vec3d2.toVector3f().rotate(quaternionf);
			vector3f.rotate(quaternionf);
			((AbstractArrow) nade).shoot((double) vector3f.x, (double) vector3f.y, (double) vector3f.z, speed,
					divergence);

			stack.hurtAndBreak(1, shooter, p -> p.broadcastBreakEvent(shooter.getUsedItemHand()));
			world.addFreshEntity((Entity) nade);

			world.playSound((Player) null, shooter.getX(), shooter.getY(), shooter.getZ(), HWGSounds.GLAUNCHERFIRE,
					SoundSource.PLAYERS, 1.0F, 0.9F);
		}
	}

	@Override
	public int getDefaultProjectileRange() {
		return 16;
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		var itemStack = user.getItemInHand(hand);
		if (isCharged(itemStack) && itemStack.getDamageValue() < (itemStack.getMaxDamage() - 1)
				&& !user.getCooldowns().isOnCooldown(this)) {
			shootAll(world, user, hand, itemStack, getSpeed(itemStack), 1.0F);
			user.getCooldowns().addCooldown(this, 25);
			setCharged(itemStack, false);
			if (!world.isClientSide)
				triggerAnim(user, GeoItem.getOrAssignId(itemStack, (ServerLevel) world), "shoot_controller", "firing");
			var isInsideWaterBlock = user.level.isWaterAt(user.blockPosition());
			spawnLightSource(user, isInsideWaterBlock);
			return InteractionResultHolder.consume(itemStack);
		} else if (!user.getProjectile(itemStack).isEmpty()) {
			if (!isCharged(itemStack)) {
				this.charged = false;
				this.loaded = false;
				user.startUsingItem(hand);
			}
			return InteractionResultHolder.consume(itemStack);
		} else
			return InteractionResultHolder.fail(itemStack);
	}

	public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
		if (!isCharged(stack) && loadProjectiles(user, stack)) {
			setCharged(stack, true);
			var soundCategory = user instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
			world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), HWGSounds.GLAUNCHERRELOAD,
					soundCategory, 0.5F, 1.0F);
			if (!world.isClientSide)
				triggerAnim((Player) user, GeoItem.getOrAssignId(stack, (ServerLevel) world), "shoot_controller",
						"loading");
			((Player) user).getCooldowns().addCooldown(this, 15);
		}
	}

	private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
		var i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, projectile);
		var j = i == 0 ? 1 : 3;
		var bl = shooter instanceof Player && ((Player) shooter).getAbilities().instabuild;
		var itemStack = shooter.getProjectile(projectile);
		var itemStack2 = itemStack.copy();

		for (int k = 0; k < j; ++k) {
			if (k > 0)
				itemStack = itemStack2.copy();

			if (itemStack.isEmpty() && bl) {
				itemStack = new ItemStack(HWGItems.G_SMOKE);
				itemStack2 = itemStack.copy();
			}

			if (!loadProjectile(shooter, projectile, itemStack, k > 0, bl))
				return false;
		}
		return true;
	}

	private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile,
			boolean simulated, boolean creative) {
		if (projectile.isEmpty())
			return false;
		else {
			var bl = creative && projectile.getItem() instanceof GrenadeEmpItem;
			ItemStack itemStack2;
			if (!bl && !creative && !simulated) {
				itemStack2 = projectile.split(1);
				if (projectile.isEmpty() && shooter instanceof Player)
					((Player) shooter).getInventory().removeItem(projectile);
			} else
				itemStack2 = projectile.copy();

			putProjectile(crossbow, itemStack2);
			return true;
		}
	}

	public static boolean isCharged(ItemStack stack) {
		var NbtCompound = stack.getTag();
		return NbtCompound != null && NbtCompound.getBoolean("Charged");
	}

	public static void setCharged(ItemStack stack, boolean charged) {
		var NbtCompound = stack.getOrCreateTag();
		NbtCompound.putBoolean("Charged", charged);
	}

	private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
		var NbtCompound = crossbow.getOrCreateTag();
		ListTag NbtList2;
		if (NbtCompound.contains("ChargedProjectiles", 9))
			NbtList2 = NbtCompound.getList("ChargedProjectiles", 10);
		else
			NbtList2 = new ListTag();

		var NbtCompound2 = new CompoundTag();
		projectile.save(NbtCompound2);
		NbtList2.add(NbtCompound2);
		NbtCompound.put("ChargedProjectiles", NbtList2);
	}

	private static List<ItemStack> getProjectiles(ItemStack crossbow) {
		List<ItemStack> list = Lists.newArrayList();
		var NbtCompound = crossbow.getTag();
		if (NbtCompound != null && NbtCompound.contains("ChargedProjectiles", 9)) {
			var NbtList = NbtCompound.getList("ChargedProjectiles", 10);
			if (NbtList != null)
				for (int i = 0; i < NbtList.size(); ++i) {
					var NbtCompound2 = NbtList.getCompound(i);
					list.add(ItemStack.of(NbtCompound2));
				}
		}
		return list;
	}

	private static void clearProjectiles(ItemStack crossbow) {
		var NbtCompound = crossbow.getTag();
		if (NbtCompound != null) {
			var NbtList = NbtCompound.getList("ChargedProjectiles", 9);
			NbtList.clear();
			NbtCompound.put("ChargedProjectiles", NbtList);
		}
	}

	public static boolean hasProjectile(ItemStack crossbow, Item projectile) {
		return getProjectiles(crossbow).stream().anyMatch((s) -> {
			return s.getItem() == projectile;
		});
	}

	public static void shootAll(Level world, LivingEntity entity, InteractionHand hand, ItemStack stack, float speed,
			float divergence) {
		var list = getProjectiles(stack);
		var fs = getSoundPitches(entity.level.random);

		for (int i = 0; i < list.size(); ++i) {
			var itemStack = (ItemStack) list.get(i);
			var bl = entity instanceof Player && ((Player) entity).getAbilities().instabuild;
			if (!itemStack.isEmpty()) {
				if (i == 0)
					shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 0.0F);
				else if (i == 1)
					shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, -10.0F);
				else if (i == 2)
					shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 10.0F);
			}
		}
		postShoot(world, entity, stack);
	}

	private static float[] getSoundPitches(RandomSource random) {
		var bl = random.nextBoolean();
		return new float[] { 1.0F, getSoundPitch(bl, random), getSoundPitch(!bl, random) };
	}

	private static float getSoundPitch(boolean flag, RandomSource random) {
		var f = flag ? 0.63F : 0.43F;
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
	}

	private static void postShoot(Level world, LivingEntity entity, ItemStack stack) {
		clearProjectiles(stack);
	}

	public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (!world.isClientSide) {
			var f = (float) (stack.getUseDuration() - remainingUseTicks) / (float) getPullTime(stack);
			if (f < 0.2F) {
				this.charged = false;
				this.loaded = false;
			}

			if (f >= 0.2F && !this.charged)
				this.charged = true;

			if (f >= 0.5F && !this.loaded)
				this.loaded = true;
		}
	}

	public int getUseDuration(ItemStack stack) {
		return getPullTime(stack) + 3000;
	}

	public static int getPullTime(ItemStack stack) {
		var i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
		return 25 - 5 * i;
	}

	@Environment(EnvType.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		var list = getProjectiles(stack);
		if (isCharged(stack) && !list.isEmpty()) {
			var itemStack = (ItemStack) list.get(0);
			tooltip.add((Component.translatable("Ammo")).append(" ").append(itemStack.getDisplayName()));
			if (context.isAdvanced() && itemStack.getItem() == GRENADES) {
				List<Component> list2 = Lists.newArrayList();
				HWGItems.G_EMP.appendHoverText(itemStack, world, list2, context);
				if (!list2.isEmpty()) {
					for (int i = 0; i < list2.size(); ++i)
						list2.set(i, (Component.literal("  ")).append((Component) list2.get(i))
								.withStyle(ChatFormatting.GRAY));
					tooltip.addAll(list2);
				}
			}

		}
		tooltip.add(Component.translatable("hwg.ammo.reloadgrenades").withStyle(ChatFormatting.ITALIC));
	}

	private static float getSpeed(ItemStack stack) {
		return stack.getItem() == Items.CROSSBOW && hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final GrenadeLauncherRender renderer = new GrenadeLauncherRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
