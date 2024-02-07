package mod.azure.hwg.item.weapons;

import com.google.common.collect.Lists;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.Animation;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.animation.RawAnimation;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.hwg.client.render.GunRender;
import mod.azure.hwg.entity.projectiles.BaseFlareEntity;
import mod.azure.hwg.item.ammo.FlareItem;
import mod.azure.hwg.item.enums.GunTypeEnum;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FlareGunItem extends HWGGunLoadedBase implements GeoItem {

    private boolean loaded = false;
    private boolean charged = false;
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private static final Predicate<ItemStack> BLACK_FLARE = stack -> stack.getItem() == HWGItems.BLACK_FLARE;
    public static final Predicate<ItemStack> FLARE = BLACK_FLARE.or(stack -> stack.getItem() == HWGItems.BLUE_FLARE).or(stack -> stack.getItem() == HWGItems.BROWN_FLARE).or(stack -> stack.getItem() == HWGItems.CYAN_FLARE).or(stack -> stack.getItem() == HWGItems.GRAY_FLARE).or(stack -> stack.getItem() == HWGItems.GREEN_FLARE).or(stack -> stack.getItem() == HWGItems.LIGHTBLUE_FLARE).or(stack -> stack.getItem() == HWGItems.LIGHTGRAY_FLARE).or(stack -> stack.getItem() == HWGItems.LIME_FLARE).or(stack -> stack.getItem() == HWGItems.MAGENTA_FLARE).or(stack -> stack.getItem() == HWGItems.ORANGE_FLARE).or(stack -> stack.getItem() == HWGItems.PINK_FLARE).or(stack -> stack.getItem() == HWGItems.PURPLE_FLARE).or(stack -> stack.getItem() == HWGItems.RED_FLARE).or(stack -> stack.getItem() == HWGItems.WHITE_FLARE).or(stack -> stack.getItem() == HWGItems.YELLOW_FLARE);

    public FlareGunItem() {
        super(new Item.Properties().stacksTo(1).durability(31));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    private static void shoot(Level world, LivingEntity shooter, ItemStack stack, ItemStack projectile, float speed, float divergence) {
        if (!world.isClientSide) {
            var flareEntity = new BaseFlareEntity(world, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
            var black = projectile.getItem() == HWGItems.BLACK_FLARE;
            var blue = projectile.getItem() == HWGItems.BLUE_FLARE;
            var brown = projectile.getItem() == HWGItems.BROWN_FLARE;
            var cyan = projectile.getItem() == HWGItems.CYAN_FLARE;
            var gray = projectile.getItem() == HWGItems.GRAY_FLARE;
            var green = projectile.getItem() == HWGItems.GREEN_FLARE;
            var lightBlue = projectile.getItem() == HWGItems.LIGHTBLUE_FLARE;
            var lightGray = projectile.getItem() == HWGItems.LIGHTGRAY_FLARE;
            var lime = projectile.getItem() == HWGItems.LIME_FLARE;
            var magenta = projectile.getItem() == HWGItems.MAGENTA_FLARE;
            var orange = projectile.getItem() == HWGItems.ORANGE_FLARE;
            var pink = projectile.getItem() == HWGItems.PINK_FLARE;
            var purple = projectile.getItem() == HWGItems.PURPLE_FLARE;
            var red = projectile.getItem() == HWGItems.RED_FLARE;
            var yellow = projectile.getItem() == HWGItems.YELLOW_FLARE;
            if (black) flareEntity.setColor(1);
            else if (blue) flareEntity.setColor(2);
            else if (brown) flareEntity.setColor(3);
            else if (cyan) flareEntity.setColor(4);
            else if (gray) flareEntity.setColor(5);
            else if (green) flareEntity.setColor(6);
            else if (lightBlue) flareEntity.setColor(7);
            else if (lightGray) flareEntity.setColor(8);
            else if (lime) flareEntity.setColor(9);
            else if (magenta) flareEntity.setColor(10);
            else if (orange) flareEntity.setColor(11);
            else if (pink) flareEntity.setColor(12);
            else if (purple) flareEntity.setColor(13);
            else if (red) flareEntity.setColor(14);
            else if (yellow) flareEntity.setColor(15);
            else flareEntity.setColor(16);
            var vec3d = shooter.getUpVector(1.0F);
            var quaternionf = new Quaternionf().setAngleAxis((float) 0.0 * ((float) Math.PI / 180), vec3d.x, vec3d.y, vec3d.z);
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
        var bl = shooter instanceof Player player && player.isCreative();
        var itemStack = shooter.getProjectile(projectile);

        for (var k = 0; k < 1; ++k) {
            if (itemStack.isEmpty() && bl) {
                itemStack = new ItemStack((ItemLike) FLARE);
            }
            if (!loadProjectile(shooter, projectile, itemStack, bl)) return false;
        }
        return true;
    }

    private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean creative) {
        if (projectile.isEmpty()) return false;
        else {
            var bl = creative && projectile.getItem() instanceof FlareItem;
            ItemStack itemStack2;
            if (!bl && !creative) {
                itemStack2 = projectile.split(1);
                if (projectile.isEmpty() && shooter instanceof Player player)
                    player.getInventory().removeItem(projectile);
            } else itemStack2 = projectile.copy();
            putProjectile(crossbow, itemStack2);
            return true;
        }
    }

    public static boolean isCharged(ItemStack stack) {
        var nbt = stack.getTag();
        return nbt != null && nbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        stack.getOrCreateTag().putBoolean("Charged", charged);
    }

    private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
        var nbt = crossbow.getOrCreateTag();
        ListTag list;
        if (nbt.contains("ChargedProjectiles", 9)) list = nbt.getList("ChargedProjectiles", 10);
        else list = new ListTag();
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
            for (var i = 0; i < list2.size(); ++i) {
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

    public static void shootAll(Level world, LivingEntity entity, ItemStack stack, float speed, float divergence) {
        for (var itemStack : getProjectiles(stack)) {
            shoot(world, entity, stack, itemStack, speed, divergence);
        }
        postShoot(stack);
    }

    private static void postShoot(ItemStack stack) {
        clearProjectiles(stack);
    }

    public static int getPullTime(ItemStack stack) {
        var enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return enchantmentLevel == 0 ? 25 : 25 - 5 * enchantmentLevel;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", event -> PlayState.CONTINUE).triggerableAnim("firing", RawAnimation.begin().then("firing", Animation.LoopType.PLAY_ONCE)).triggerableAnim("loading", RawAnimation.begin().then("loading", Animation.LoopType.PLAY_ONCE)));
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
    public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
        return FLARE;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return FLARE;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 16;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        var itemStack = user.getItemInHand(hand);
        if (isCharged(itemStack) && itemStack.getDamageValue() < (itemStack.getMaxDamage() - 1) && !user.getCooldowns().isOnCooldown(this)) {
            shootAll(world, user, itemStack, 2.6F, 1.0F);
            user.getCooldowns().addCooldown(this, 25);
            setCharged(itemStack, false);
            if (!world.isClientSide)
                triggerAnim(user, GeoItem.getOrAssignId(itemStack, (ServerLevel) world), "controller", "firing");
            return InteractionResultHolder.consume(itemStack);
        } else if (!user.getProjectile(itemStack).isEmpty()) {
            if (!isCharged(itemStack)) {
                this.charged = false;
                this.loaded = false;
                user.startUsingItem(hand);
            }
            return InteractionResultHolder.consume(itemStack);
        } else return InteractionResultHolder.fail(itemStack);
    }

    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!isCharged(stack) && loadProjectiles(user, stack)) {
            setCharged(stack, true);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CHAIN_BREAK, SoundSource.PLAYERS, 1.0F, 1.5F);
            if (!world.isClientSide)
                triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerLevel) world), "controller", "loading");
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
            if (f >= 0.2F && !this.charged) this.charged = true;
            if (f >= 0.5F && !this.loaded) this.loaded = true;
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
            tooltip.add((Component.literal("Ammo")).append(" ").append(itemStack.getDisplayName()));
            if (context.isAdvanced() && itemStack.getItem() == FLARE) {
                List<Component> list2 = Lists.newArrayList();
                HWGItems.FLARE_GUN.appendHoverText(itemStack, world, list2, context);
                if (!list2.isEmpty()) {
                    list2.replaceAll(component -> (Component.literal("  ")).append(component).withStyle(ChatFormatting.GRAY));
                    tooltip.addAll(list2);
                }
            }
        }
        tooltip.add(Component.translatable("hwg.ammo.reloadflares").withStyle(ChatFormatting.ITALIC));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GunRender<FlareGunItem> renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new GunRender<FlareGunItem>("flare_gun", GunTypeEnum.FLARE);
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

}
