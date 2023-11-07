package mod.azure.hwg.item.weapons;

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
import mod.azure.hwg.client.render.weapons.FlareGunRender;
import mod.azure.hwg.entity.projectiles.BaseFlareEntity;
import mod.azure.hwg.item.ammo.FlareItem;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FlareGunItem extends HWGGunLoadedBase implements GeoItem {

    private static final Predicate<ItemStack> BLACK_FLARE = stack -> stack.getItem() == HWGItems.BLACK_FLARE;
    public static final Predicate<ItemStack> FLARE = BLACK_FLARE.or(stack -> stack.getItem() == HWGItems.BLUE_FLARE).or(stack -> stack.getItem() == HWGItems.BROWN_FLARE).or(stack -> stack.getItem() == HWGItems.CYAN_FLARE).or(stack -> stack.getItem() == HWGItems.GRAY_FLARE).or(stack -> stack.getItem() == HWGItems.GREEN_FLARE).or(stack -> stack.getItem() == HWGItems.LIGHTBLUE_FLARE).or(stack -> stack.getItem() == HWGItems.LIGHTGRAY_FLARE).or(stack -> stack.getItem() == HWGItems.LIME_FLARE).or(stack -> stack.getItem() == HWGItems.MAGENTA_FLARE).or(stack -> stack.getItem() == HWGItems.ORANGE_FLARE).or(stack -> stack.getItem() == HWGItems.PINK_FLARE).or(stack -> stack.getItem() == HWGItems.PURPLE_FLARE).or(stack -> stack.getItem() == HWGItems.RED_FLARE).or(stack -> stack.getItem() == HWGItems.WHITE_FLARE).or(stack -> stack.getItem() == HWGItems.YELLOW_FLARE);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private boolean charged = false;
    private boolean loaded = false;

    public FlareGunItem() {
        super(new Item.Properties().stacksTo(1).durability(31));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    private static void shoot(Level world, LivingEntity shooter, InteractionHand hand, ItemStack stack, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
        if (!world.isClientSide) {
            var flareEntity = new BaseFlareEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
            var black = projectile.getItem() == HWGItems.BLACK_FLARE;
            var blue = projectile.getItem() == HWGItems.BLUE_FLARE;
            var brown = projectile.getItem() == HWGItems.BROWN_FLARE;
            var cyan = projectile.getItem() == HWGItems.CYAN_FLARE;
            var gray = projectile.getItem() == HWGItems.GRAY_FLARE;
            var green = projectile.getItem() == HWGItems.GREEN_FLARE;
            var lightblue = projectile.getItem() == HWGItems.LIGHTBLUE_FLARE;
            var lightgray = projectile.getItem() == HWGItems.LIGHTGRAY_FLARE;
            var lime = projectile.getItem() == HWGItems.LIME_FLARE;
            var magenta = projectile.getItem() == HWGItems.MAGENTA_FLARE;
            var orange = projectile.getItem() == HWGItems.ORANGE_FLARE;
            var pink = projectile.getItem() == HWGItems.PINK_FLARE;
            var purple = projectile.getItem() == HWGItems.PURPLE_FLARE;
            var red = projectile.getItem() == HWGItems.RED_FLARE;
            var yellow = projectile.getItem() == HWGItems.YELLOW_FLARE;
            if (black)
                flareEntity.setColor(1);
            else if (blue)
                flareEntity.setColor(2);
            else if (brown)
                flareEntity.setColor(3);
            else if (cyan)
                flareEntity.setColor(4);
            else if (gray)
                flareEntity.setColor(5);
            else if (green)
                flareEntity.setColor(6);
            else if (lightblue)
                flareEntity.setColor(7);
            else if (lightgray)
                flareEntity.setColor(8);
            else if (lime)
                flareEntity.setColor(9);
            else if (magenta)
                flareEntity.setColor(10);
            else if (orange)
                flareEntity.setColor(11);
            else if (pink)
                flareEntity.setColor(12);
            else if (purple)
                flareEntity.setColor(13);
            else if (red)
                flareEntity.setColor(14);
            else if (yellow)
                flareEntity.setColor(15);
            else
                flareEntity.setColor(16);

            var vec3d = shooter.getUpVector(1.0F);
            var quaternionf = new Quaternionf().setAngleAxis(simulated * ((float) Math.PI / 180), vec3d.x, vec3d.y, vec3d.z);
            var vec3d2 = shooter.getViewVector(1.0f);
            var vector3f = vec3d2.toVector3f().rotate(quaternionf);
            vector3f.rotate(quaternionf);
            ((Projectile) flareEntity).shoot(vector3f.x, vector3f.y, vector3f.z, speed, divergence);
            flareEntity.setBaseDamage(0.3D);
            flareEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            stack.hurtAndBreak(1, shooter, p -> p.broadcastBreakEvent(shooter.getUsedItemHand()));
            world.addFreshEntity(flareEntity);
        }
    }

    private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
        var i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, projectile);
        var j = i == 0 ? 1 : 3;
        var bl = shooter instanceof Player player && player.getAbilities().instabuild;
        var itemStack = shooter.getProjectile(projectile);
        var itemStack2 = itemStack.copy();

        for (int k = 0; k < j; ++k) {
            if (k > 0)
                itemStack = itemStack2.copy();

            if (itemStack.isEmpty() && bl) {
                itemStack = new ItemStack((ItemLike) FLARE);
                itemStack2 = itemStack.copy();
            }

            if (!loadProjectile(shooter, projectile, itemStack, k > 0, bl))
                return false;
        }
        return true;
    }

    private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        if (projectile.isEmpty())
            return false;
        else {
            var bl = creative && projectile.getItem() instanceof FlareItem;
            ItemStack itemStack2;
            if (!bl && !creative && !simulated) {
                itemStack2 = projectile.split(1);
                if (projectile.isEmpty() && shooter instanceof Player player)
                    player.getInventory().removeItem(projectile);
            } else
                itemStack2 = projectile.copy();

            putProjectile(crossbow, itemStack2);
            return true;
        }
    }

    public static boolean isCharged(ItemStack stack) {
        var nbt = stack.getTag();
        return nbt != null && nbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        var nbt = stack.getOrCreateTag();
        nbt.putBoolean("Charged", charged);
    }

    private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
        var nbt = crossbow.getOrCreateTag();
        ListTag list;
        if (nbt.contains("ChargedProjectiles", 9))
            list = nbt.getList("ChargedProjectiles", 10);
        else
            list = new ListTag();

        var nbt2 = new CompoundTag();
        projectile.save(nbt2);
        list.add(nbt2);
        nbt.put("ChargedProjectiles", list);
    }

    private static List<ItemStack> getProjectiles(ItemStack crossbow) {
        List<ItemStack> list = Lists.newArrayList();
        var nbt = crossbow.getTag();
        if (nbt != null && nbt.contains("ChargedProjectiles", 9)) {
            var list2 = nbt.getList("ChargedProjectiles", 10);
            if (list2 != null)
                for (int i = 0; i < list2.size(); ++i) {
                    var ntb2 = list2.getCompound(i);
                    list.add(ItemStack.of(ntb2));
                }
        }
        return list;
    }

    private static void clearProjectiles(ItemStack crossbow) {
        var nbt = crossbow.getTag();
        if (nbt != null) {
            var list = nbt.getList("ChargedProjectiles", 9);
            list.clear();
            nbt.put("ChargedProjectiles", list);
        }
    }

    public static boolean hasProjectile(ItemStack crossbow, Item projectile) {
        return getProjectiles(crossbow).stream().anyMatch(s -> s.getItem() == projectile);
    }

    public static void shootAll(Level world, LivingEntity entity, InteractionHand hand, ItemStack stack, float speed, float divergence) {
        var list = getProjectiles(stack);
        var fs = getSoundPitches(entity.level().random);

        for (int i = 0; i < list.size(); ++i) {
            var itemStack = list.get(i);
            var bl = entity instanceof Player player && player.getAbilities().instabuild;
            shoot(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 0.0F);
        }
        postShoot(world, entity, stack);
    }

    private static float[] getSoundPitches(RandomSource random) {
        boolean bl = random.nextBoolean();
        return new float[]{1.0F, getSoundPitch(bl, random), getSoundPitch(!bl, random)};
    }

    private static float getSoundPitch(boolean flag, RandomSource random) {
        var f = flag ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void postShoot(Level world, LivingEntity entity, ItemStack stack) {
        clearProjectiles(stack);
    }

    public static int getPullTime(ItemStack stack) {
        var i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? 25 : 25 - 5 * i;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE).triggerableAnim("firing", RawAnimation.begin().then("firing", LoopType.PLAY_ONCE)).triggerableAnim("loading", RawAnimation.begin().then("loading", LoopType.PLAY_ONCE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return Tiers.IRON.getRepairIngredient().test(ingredient) || super.isValidRepairItem(stack, ingredient);
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return FLARE;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return FLARE;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 16;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        var itemStack = user.getItemInHand(hand);
        if (isCharged(itemStack) && itemStack.getDamageValue() < (itemStack.getMaxDamage() - 1) && !user.getCooldowns().isOnCooldown(this)) {
            shootAll(world, user, hand, itemStack, 2.6F, 1.0F);
            user.getCooldowns().addCooldown(this, 25);
            setCharged(itemStack, false);
            if (!world.isClientSide)
                triggerAnim(user, GeoItem.getOrAssignId(itemStack, (ServerLevel) world), "shoot_controller", "firing");
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
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CHAIN_BREAK, SoundSource.PLAYERS, 1.0F, 1.5F);
            if (!world.isClientSide)
                triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerLevel) world), "shoot_controller", "loading");
            ((Player) user).getCooldowns().addCooldown(this, 15);
        }
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

    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        var list = getProjectiles(stack);
        if (isCharged(stack) && !list.isEmpty()) {
            var itemStack = list.get(0);
            tooltip.add((Component.translatable("Ammo")).append(" ").append(itemStack.getDisplayName()));
            if (context.isAdvanced() && itemStack.getItem() == FLARE) {
                List<Component> list2 = Lists.newArrayList();
                HWGItems.G_EMP.appendHoverText(itemStack, world, list2, context);
                if (!list2.isEmpty()) {
                    for (int i = 0; i < list2.size(); ++i)
                        list2.set(i, (Component.literal("  ")).append(list2.get(i)).withStyle(ChatFormatting.GRAY));
                    tooltip.addAll(list2);
                }
            }
        }
        tooltip.add(Component.translatable("hwg.ammo.reloadflares").withStyle(ChatFormatting.ITALIC));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final FlareGunRender renderer = new FlareGunRender();

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
