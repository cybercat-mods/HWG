package mod.azure.hwg.item.weapons;

import com.google.common.collect.Lists;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import mod.azure.hwg.item.weapons.animations.GunDispatcher;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.List;
import java.util.function.Predicate;

public class GrenadeLauncherItem extends HWGGunLoadedBase {

    public static final Predicate<ItemStack> EMP = stack -> stack.getItem() == HWGItems.G_EMP;
    public static final Predicate<ItemStack> GRENADES = EMP.or(stack -> stack.getItem() == HWGItems.G_FRAG).or(
            stack -> stack.getItem() == HWGItems.G_NAPALM).or(stack -> stack.getItem() == HWGItems.G_SMOKE).or(
            stack -> stack.getItem() == HWGItems.G_STUN);
    private GunDispatcher animationDispatcher;
    private boolean charged = false;
    private boolean loaded = false;

    public GrenadeLauncherItem() {
        super(new Properties().stacksTo(1).durability(31).component(DataComponents.CHARGED_PROJECTILES,
                ChargedProjectiles.EMPTY));
        this.animationDispatcher = new GunDispatcher();
    }

    @NotNull
    private static GrenadeEntity getGrenadeEntity(Level world, LivingEntity shooter, ItemStack projectile) {
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
        return nade;
    }

    private static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack) {
        List<ItemStack> list = draw(crossbowStack, shooter.getProjectile(crossbowStack), shooter);
        if (!list.isEmpty()) {
            crossbowStack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCharged(ItemStack stack) {
        ChargedProjectiles chargedProjectiles = stack.getOrDefault(DataComponents.CHARGED_PROJECTILES,
                ChargedProjectiles.EMPTY);
        return !chargedProjectiles.isEmpty();
    }

    public static int getPullTime() {
        return 25 - 5;
    }

    public void shootAll(Level level, LivingEntity entity, InteractionHand hand, ItemStack stack, float speed, float divergence) {
        if (!level.isClientSide()) {
            var chargedProjectiles = stack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
            if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
                var bl = entity instanceof Player player && player.getAbilities().instabuild;
                this.shootNade(level, entity, hand, stack, chargedProjectiles.getItems(), bl, speed, divergence);
            }
        }
    }

    private void shootNade(Level level, LivingEntity shooter, InteractionHand hand, ItemStack stack, List<ItemStack> projectile, boolean creative, float speed, float divergence) {
        float i = 1.0F;
        for (ItemStack itemStack : projectile) {
            if (!itemStack.isEmpty()) {
                i = -i;
                if (!creative)
                    stack.hurtAndBreak(this.getDurabilityUse(itemStack), shooter, LivingEntity.getSlotForHand(hand));
                var nade = getGrenadeEntity(level, shooter, itemStack);
                var vec3d = shooter.getUpVector(1.0F);
                var quaternionf = new Quaternionf().setAngleAxis((float) 0.0 * ((float) Math.PI / 180), vec3d.x, vec3d.y,
                        vec3d.z);
                var vec3d2 = shooter.getViewVector(1.0f);
                var vector3f = vec3d2.toVector3f().rotate(quaternionf);
                vector3f.rotate(quaternionf);
                nade.shoot(vector3f.x, vector3f.y, vector3f.z, speed, divergence);
                level.addFreshEntity(nade);
                level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), HWGSounds.GLAUNCHERFIRE.get(),
                        SoundSource.PLAYERS, 1.0F, 0.9F);
            }
        }
    }

    @Override
    public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
        return GRENADES;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return GRENADES;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack ingredient) {
        return Tiers.IRON.getRepairIngredient().test(ingredient) || super.isValidRepairItem(stack, ingredient);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 16;
    }

    @Override
    protected void shootProjectile(@NotNull LivingEntity shooter, @NotNull Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        var itemStack = player.getItemInHand(usedHand);
        ChargedProjectiles chargedProjectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            shootAll(level, player, usedHand, itemStack, 2.6f, 1.0F);
            player.getCooldowns().addCooldown(this, 25);
            var isInsideWaterBlock = player.level().isWaterAt(player.blockPosition());
            Helper.spawnLightSource(player, isInsideWaterBlock);
            if (!level.isClientSide)
                animationDispatcher.sendFiringCommand(player, itemStack);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int remainingUseTicks) {
        if (!isCharged(stack) && tryLoadProjectiles(livingEntity, stack)) {
            var soundCategory = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), HWGSounds.GLAUNCHERRELOAD.get(), soundCategory, 0.5F,
                    1.0F);
            if (!level.isClientSide)
                animationDispatcher.sendLoadingCommand(livingEntity, stack);
            if (livingEntity instanceof Player player)
                player.getCooldowns().addCooldown(this, 15);
        }
    }

    public void onUseTick(Level world, @NotNull LivingEntity user, @NotNull ItemStack stack, int remainingUseTicks) {
        if (!world.isClientSide) {
            var f = (float) (stack.getUseDuration(user) - remainingUseTicks) / (float) getPullTime();
            if (f < 0.2F) {
                this.charged = false;
                this.loaded = false;
            }

            if (f >= 0.2F && !this.charged) this.charged = true;

            if (f >= 0.5F && !this.loaded) this.loaded = true;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            ItemStack itemStack = chargedProjectiles.getItems().getFirst();
            tooltipComponents.add(Component.literal("Ammo").append(CommonComponents.SPACE).append(itemStack.getDisplayName()));
            if (tooltipFlag.isAdvanced() && itemStack.getItem() == GRENADES) {
                List<Component> list = Lists.newArrayList();
                HWGItems.G_EMP.get().appendHoverText(itemStack, context, list, tooltipFlag);
                if (!list.isEmpty()) {
                    list.replaceAll(component -> Component.literal("  ").append(component).withStyle(
                            ChatFormatting.GRAY));
                    tooltipComponents.addAll(list);
                }
            }
        }
        tooltipComponents.add(Component.translatable("hwg.ammo.reloadgrenades").withStyle(ChatFormatting.ITALIC));
    }

}
