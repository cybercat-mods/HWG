package mod.azure.hwg.item.weapons;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.common.api.client.helper.ClientUtils;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.Animation;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.animation.RawAnimation;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.render.GunRender;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.item.enums.ProjectileEnum;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.Helper;
import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AzureAnimatedGunItem extends Item implements GeoItem {
    protected Item ammoType;
    protected final String id;
    protected final SoundEvent firingSound;
    protected final SoundEvent reloadSound;
    protected final GunTypeEnum gunTypeEnum;
    private static final String firing = "firing";
    private static final String controller = "controller";
    protected final ProjectileEnum projectileTypeEnum;
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public AzureAnimatedGunItem(String id, ProjectileEnum projectileTypeEnum, GunTypeEnum gunTypeEnum, int maxClipSize, SoundEvent reloadSound, SoundEvent firingSound) {
        super(new Item.Properties().stacksTo(1).durability(maxClipSize + 1));
        this.id = id;
        this.projectileTypeEnum = projectileTypeEnum;
        this.gunTypeEnum = gunTypeEnum;
        this.reloadSound = reloadSound;
        this.firingSound = firingSound;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public String getItemID() {
        return this.id;
    }

    public ProjectileEnum getProjectileTypeEnum() {
        return this.projectileTypeEnum;
    }

    public GunTypeEnum getGunTypeEnum() {
        return this.gunTypeEnum;
    }

    public Item getAmmoType() {
        switch (this.getProjectileTypeEnum()) {
            case BULLET, HELL -> this.ammoType = HWGItems.BULLETS;
            case BLAZE -> this.ammoType = Items.BLAZE_ROD;
            case MEANIE -> this.ammoType = Items.REDSTONE;
            case SHELL -> this.ammoType = HWGItems.SHOTGUN_SHELL;
            case ROCKET -> this.ammoType = HWGItems.ROCKET;
            case FLAMES, FIREBALL -> this.ammoType = HWGItems.FUEL_TANK;
            case SILVER_BULLET -> this.ammoType = HWGItems.SILVERBULLET;
        }
        return this.ammoType;
    }

    public SoundEvent getReloadSound() {
        return this.reloadSound;
    }

    public SoundEvent getFiringSound() {
        return this.firingSound;
    }

    public int getReloadAmount() {
        if (this.gunTypeEnum == GunTypeEnum.FLAMETHROWER) return HWGMod.config.gunconfigs.flammerconfigs.flammer_cap;
        if (this.gunTypeEnum == GunTypeEnum.BRIMSTONE) return HWGMod.config.gunconfigs.brimstoneconfigs.brimstone_cap;
        return 1;
    }

    public int getCoolDown() {
        switch (this.gunTypeEnum) {
            case FLAMETHROWER -> {
                return HWGMod.config.gunconfigs.flammerconfigs.flammer_cooldown;
            }
            case SMG -> {
                return HWGMod.config.gunconfigs.smgconfigs.smg_cooldown;
            }
            case AK7 -> {
                return HWGMod.config.gunconfigs.ak47configs.ak47_cooldown;
            }
            case TOMMYGUN -> {
                return HWGMod.config.gunconfigs.tommyconfigs.tommy_cooldown;
            }
            case MINIGUN -> {
                return HWGMod.config.gunconfigs.minigunconfigs.minigun_cooldown;
            }
            case GOLDEN_PISTOL -> {
                return HWGMod.config.gunconfigs.gpistolconfigs.golden_pistol_cooldown;
            }
            case BALROG -> {
                return HWGMod.config.gunconfigs.balrogconfigs.balrog_cooldown;
            }
            case BRIMSTONE -> {
                return HWGMod.config.gunconfigs.brimstoneconfigs.brimstone_cooldown;
            }
            case HELLHORSE, SILVER_HELL -> {
                return HWGMod.config.gunconfigs.hellhorseconfigs.hellhorse_cooldown;
            }
            case LUGER -> {
                return HWGMod.config.gunconfigs.lugerconfigs.luger_cooldown;
            }
            case MEANIE -> {
                return HWGMod.config.gunconfigs.meanieconfigs.meanie_cooldown;
            }
            case PISTOL, SILVER_PISTOL -> {
                return HWGMod.config.gunconfigs.pistolconfigs.pistol_cooldown;
            }
            case SIL_PISTOL -> {
                return HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_cooldown;
            }
            case ROCKETLAUNCHER -> {
                return HWGMod.config.gunconfigs.rocketlauncherconfigs.rocketlauncherCooldown;
            }
            case SHOTGUN -> {
                return HWGMod.config.gunconfigs.shotgunconfigs.shotgun_cooldown;
            }
            case SNIPER -> {
                return HWGMod.config.gunconfigs.sniperconfigs.sniper_cooldown;
            }
        }
        return 10;
    }

    public int getReloadCoolDown() {
        switch (this.gunTypeEnum) {
            case FLAMETHROWER -> {
                return HWGMod.config.gunconfigs.flammerconfigs.flammerReloadCooldown;
            }
            case SMG -> {
                return HWGMod.config.gunconfigs.smgconfigs.smgReloadCooldown;
            }
            case AK7 -> {
                return HWGMod.config.gunconfigs.ak47configs.ak47ReloadCooldown;
            }
            case TOMMYGUN -> {
                return HWGMod.config.gunconfigs.tommyconfigs.tommyReloadCooldown;
            }
            case MINIGUN -> {
                return HWGMod.config.gunconfigs.minigunconfigs.minigunReloadCooldown;
            }
            case GOLDEN_PISTOL -> {
                return HWGMod.config.gunconfigs.gpistolconfigs.goldenPistolReloadCooldown;
            }
            case BALROG -> {
                return HWGMod.config.gunconfigs.balrogconfigs.balrogReloadCooldown;
            }
            case BRIMSTONE -> {
                return HWGMod.config.gunconfigs.brimstoneconfigs.brimstoneReloadCooldown;
            }
            case HELLHORSE, SILVER_HELL -> {
                return HWGMod.config.gunconfigs.hellhorseconfigs.hellhorseReloadCooldown;
            }
            case LUGER -> {
                return HWGMod.config.gunconfigs.lugerconfigs.lugerReloadCooldown;
            }
            case MEANIE -> {
                return HWGMod.config.gunconfigs.meanieconfigs.meanieReloadCooldown;
            }
            case PISTOL, SILVER_PISTOL -> {
                return HWGMod.config.gunconfigs.pistolconfigs.pistolReloadCooldown;
            }
            case SIL_PISTOL -> {
                return HWGMod.config.gunconfigs.silencedpistolconfigs.silencedPistolReloadCooldown;
            }
            case ROCKETLAUNCHER -> {
                return HWGMod.config.gunconfigs.rocketlauncherconfigs.rocketlauncherReloadCooldown;
            }
            case SHOTGUN -> {
                return HWGMod.config.gunconfigs.shotgunconfigs.shotgunReloadCooldown;
            }
            case SNIPER -> {
                return HWGMod.config.gunconfigs.sniperconfigs.sniperReloadCooldown;
            }
        }
        return 10;
    }

    public float getAttackDamage() {
        switch (this.gunTypeEnum) {
            case FLAMETHROWER, BALROG, ROCKETLAUNCHER -> {
                return 0;
            }
            case SMG -> {
                return HWGMod.config.gunconfigs.smgconfigs.smg_damage;
            }
            case AK7 -> {
                return HWGMod.config.gunconfigs.ak47configs.ak47_damage;
            }
            case TOMMYGUN -> {
                return HWGMod.config.gunconfigs.tommyconfigs.tommy_damage;
            }
            case MINIGUN -> {
                return HWGMod.config.gunconfigs.minigunconfigs.minigun_damage;
            }
            case GOLDEN_PISTOL -> {
                return HWGMod.config.gunconfigs.gpistolconfigs.golden_pistol_damage;
            }
            case BRIMSTONE -> {
                return HWGMod.config.gunconfigs.brimstoneconfigs.brimstone_damage;
            }
            case HELLHORSE, SILVER_HELL -> {
                return HWGMod.config.gunconfigs.hellhorseconfigs.hellhorse_damage;
            }
            case LUGER -> {
                return HWGMod.config.gunconfigs.lugerconfigs.luger_damage;
            }
            case MEANIE -> {
                return HWGMod.config.gunconfigs.meanieconfigs.meanie_damage;
            }
            case PISTOL, SILVER_PISTOL -> {
                return HWGMod.config.gunconfigs.pistolconfigs.pistol_damage;
            }
            case SIL_PISTOL -> {
                return HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_damage;
            }
            case SHOTGUN -> {
                return HWGMod.config.gunconfigs.shotgunconfigs.shotgun_damage;
            }
            case SNIPER -> {
                return HWGMod.config.gunconfigs.sniperconfigs.sniper_damage;
            }
        }
        return 0;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    private void hitScanDamage(LivingEntity livingEntity, Player player, ItemStack itemStack) {
        if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
            livingEntity.setSecondsOnFire(100);
        if (getProjectileTypeEnum() != ProjectileEnum.SHELL)
            livingEntity.hurt(player.damageSources().playerAttack(player), this.getAttackDamage());
        else {
            for (var y = 0; y < 3; ++y) {
                livingEntity.invulnerableTime = 0;
                livingEntity.setDeltaMovement(0, 0, 0);
                livingEntity.hurt(player.damageSources().playerAttack(player), this.getAttackDamage());
            }
        }
    }

    private void singleFire(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        var result = Helper.hitscanTrace(player, 64, 1.0F);
        player.getCooldowns().addCooldown(this, this.getCoolDown());
        switch (this.getProjectileTypeEnum()) {
            case BULLET -> {
                if (result != null) {
                    if (result.getEntity() instanceof LivingEntity livingEntity)
                        this.hitScanDamage(livingEntity, player, itemStack);
                } else {
                    var bullet = Helper.createBullet(level, player, this.getAttackDamage());
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 20.0F, 1.0F);
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bullet.setSecondsOnFire(100);
                    bullet.tickCount = -15;
                    level.addFreshEntity(bullet);
                }
            }
            case HELL -> {
                if (result != null) {
                    if (result.getEntity() instanceof LivingEntity livingEntity)
                        this.hitScanDamage(livingEntity, player, itemStack);
                } else {
                    var bullet = Helper.createBullet(level, player, getAttackDamage());
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 20.0F * 3.0F, 1.0F);
                    bullet.tickCount = -15;
                    if (player.getRandom().nextInt(1, 101) <= 20 || EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bullet.setSecondsOnFire(100);
                    level.addFreshEntity(bullet);
                }
            }
            case BLAZE -> {
                var rod = Helper.createBlazeRod(level, player);
                rod.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                rod.moveTo(player.getX(), player.getY(0.85), player.getZ(), 0, 0);
                rod.isNoGravity();
                rod.isOnFire();
                var rod1 = Helper.createBlazeRod(level, player);
                rod1.shootFromRotation(player, player.getXRot() + 2, player.getYRot(), 0.0F, 3.0F, 1.0F);
                rod1.moveTo(player.getX(), player.getY(0.85), player.getZ(), 0, 0);
                rod1.isNoGravity();
                rod1.isOnFire();
                var rod2 = Helper.createBlazeRod(level, player);
                rod2.shootFromRotation(player, player.getXRot(), player.getYRot() + 2, 0.0F, 3.0F, 1.0F);
                rod2.moveTo(player.getX(), player.getY(0.85), player.getZ(), 0, 0);
                rod2.isNoGravity();
                rod2.isOnFire();
                var rod3 = Helper.createBlazeRod(level, player);
                rod3.shootFromRotation(player, player.getXRot(), player.getYRot() - 2, 0.0F, 3.0F, 1.0F);
                rod3.moveTo(player.getX(), player.getY(0.85), player.getZ(), 0, 0);
                rod3.isNoGravity();
                rod3.isOnFire();
                level.addFreshEntity(rod);
                level.addFreshEntity(rod1);
                level.addFreshEntity(rod2);
                level.addFreshEntity(rod3);
            }
            case FIREBALL -> {
                var fireball = Helper.createFireball(level, player);
                fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.25F * 3.0F, 1.0F);
                fireball.moveTo(player.getX(), player.getY(0.5), player.getZ(), 0, 0);
                fireball.setRemainingFireTicks(100);
                fireball.setKnockback(1);
                var fireball1 = Helper.createFireball(level, player);
                fireball1.shootFromRotation(player, player.getXRot(), player.getYRot() + 5, 0.0F, 0.25F * 3.0F, 1.0F);
                fireball1.moveTo(player.getX(), player.getY(0.5), player.getZ(), 0, 0);
                fireball1.setRemainingFireTicks(100);
                fireball1.setKnockback(1);
                var fireball2 = Helper.createFireball(level, player);
                fireball2.shootFromRotation(player, player.getXRot(), player.getYRot() - 5, 0.0F, 0.25F * 3.0F, 1.0F);
                fireball2.moveTo(player.getX(), player.getY(0.5), player.getZ(), 0, 0);
                fireball2.setRemainingFireTicks(100);
                fireball2.setKnockback(1);
                level.addFreshEntity(fireball);
                level.addFreshEntity(fireball1);
                level.addFreshEntity(fireball2);
            }
            case MEANIE -> {
                if (result != null) {
                    if (result.getEntity() instanceof LivingEntity livingEntity)
                        this.hitScanDamage(livingEntity, player, itemStack);
                } else {
                    var bullet = Helper.createMeanieBullet(level, player);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 20.0F * 3.0F, 1.0F);
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bullet.setSecondsOnFire(100);
                    bullet.tickCount = -15;
                    level.addFreshEntity(bullet);
                }
            }
            case SHELL -> {
                if (result != null) {
                    if (result.getEntity() instanceof LivingEntity livingEntity)
                        this.hitScanDamage(livingEntity, player, itemStack);
                } else {
                    var bullet = Helper.createShell(level, player);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot() + 1, 0.5F, 20.0F, 1.0F);
                    var bullet1 = Helper.createShell(level, player);
                    bullet1.shootFromRotation(player, player.getXRot(), player.getYRot() - 1, 0.5F, 20.0F, 1.0F);
                    bullet.tickCount = -15;
                    bullet1.tickCount = -15;
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bullet.setSecondsOnFire(100);
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bullet1.setSecondsOnFire(100);
                    level.addFreshEntity(bullet);
                    level.addFreshEntity(bullet1);
                }
            }
            case ROCKET -> {
                var rocket = Helper.createRocket(level, player);
                rocket.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
                rocket.moveTo(player.getX(), player.getY(0.5), player.getZ(), 0, 0);
                rocket.setBaseDamage(2.5);
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    rocket.setSecondsOnFire(100);
                level.addFreshEntity(rocket);
            }
            case SILVER_BULLET -> {
                if (result != null) {
                    if (result.getEntity() instanceof LivingEntity livingEntity)
                        this.hitScanDamage(livingEntity, player, itemStack);
                } else {
                    var bullet = Helper.createSilverBullet(level, player);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 20.0F, 1.0F);
                    bullet.tickCount = -15;
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bullet.setSecondsOnFire(100);
                    level.addFreshEntity(bullet);
                }
            }
            case FLAMES -> {
                var flames = Helper.createFlame(level, player);
                flames.setProperties(player.getXRot(), player.getYRot(), 0f, 1.5f);
                flames.getEntityData().set(FlameFiring.FORCED_YAW, player.getYRot());
                flames.moveTo(player.getX() + (switch (player.getDirection()) {
                    case WEST -> -0.5F;
                    case EAST -> 0.5F;
                    default -> 0.0F;
                }), player.getY() + (switch (player.getDirection()) {
                    case DOWN -> 0.5F;
                    case UP -> -1.85F;
                    default -> 0.75F;
                }), player.getZ() + (switch (player.getDirection()) {
                    case NORTH -> -0.5F;
                    case SOUTH -> 0.5F;
                    default -> 0.0F;
                }), 0, 0);
                level.addFreshEntity(flames);
            }
        }
    }

    public void fireWeapon(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        if (itemStack.getItem() instanceof AzureAnimatedGunItem gunItem && !player.getCooldowns().isOnCooldown(gunItem)) {
            Helper.spawnLightSource(player, player.level().isWaterAt(player.blockPosition()));
            itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            if (this.getFiringSound() != null)
                level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(), SoundSource.PLAYERS, 0.25F, 1.3F);
            if (!level.isClientSide) {
                this.singleFire(itemStack, level, player);
                gunItem.triggerAnim(player, GeoItem.getOrAssignId(player.getMainHandItem(), (ServerLevel) player.level()), AzureAnimatedGunItem.controller, AzureAnimatedGunItem.firing);
            }
        }
    }

    public void autoFire(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        if (itemStack.getItem() instanceof AzureAnimatedGunItem gunItem && !player.getCooldowns().isOnCooldown(gunItem)) {
            Helper.spawnLightSource(player, player.level().isWaterAt(player.blockPosition()));
            itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            if (this.getFiringSound() != null)
                level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(), SoundSource.PLAYERS, 0.25F, 1.3F);
            if (!level.isClientSide) {
                var result = Helper.hitscanTrace(player, 64, 1.0F);
                if (this.getProjectileTypeEnum() == ProjectileEnum.BULLET) {
                    if (result != null) {
                        if (result.getEntity() instanceof LivingEntity livingEntity)
                            this.hitScanDamage(livingEntity, player, itemStack);
                    } else {
                        var bullet = Helper.createBullet(level, player, this.getAttackDamage());
                        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 20.0F, 1.0F);
                        if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.common.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                            bullet.setSecondsOnFire(100);
                        bullet.tickCount = -15;
                        level.addFreshEntity(bullet);
                    }
                }
                gunItem.triggerAnim(player, GeoItem.getOrAssignId(player.getMainHandItem(), (ServerLevel) player.level()), AzureAnimatedGunItem.controller, AzureAnimatedGunItem.firing);
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (world.isClientSide && entity instanceof Player player && player.getMainHandItem().getItem() instanceof AzureAnimatedGunItem && selected) {
            if (ClientUtils.RELOAD.consumeClick()) {
                FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
                passedData.writeBoolean(true);
                ClientPlayNetworking.send(PacketHandler.reloadGun, passedData);
            }
            if (AzureLibMod.config.useVanillaUseKey) {
                if (Minecraft.getInstance().options.keyUse.isDown()) {
                    FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
                    passedData.writeBoolean(true);
                    ClientPlayNetworking.send(PacketHandler.shootGun, passedData);
                }
            } else {
                if (ClientUtils.FIRE_WEAPON.isDown()) {
                    FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
                    passedData.writeBoolean(true);
                    ClientPlayNetworking.send(PacketHandler.shootGun, passedData);
                }
            }
        }
    }

    public static void shoot(Player player) {
        if (player.getMainHandItem().getDamageValue() < (player.getMainHandItem().getMaxDamage() - 1) && player.getMainHandItem().getItem() instanceof AzureAnimatedGunItem gunBase) {
            if (!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem()) && !player.getMainHandItem().is(HWGItems.MINIGUN))
                gunBase.fireWeapon(player.getMainHandItem(), player.level(), player);
            else gunBase.autoFire(player.getMainHandItem(), player.level(), player);
        } else {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.25F, 1.3F);
        }
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getMainHandItem().getItem() instanceof AzureAnimatedGunItem gunBase) {
            while (!user.isCreative() && user.getMainHandItem().getDamageValue() != 0 && user.getInventory().countItem(gunBase.getAmmoType()) > 0) {
                Helper.removeAmmo(gunBase.getAmmoType(), user);
                user.getCooldowns().addCooldown(gunBase, gunBase.getReloadCoolDown());
                user.getMainHandItem().hurtAndBreak(-gunBase.getReloadAmount(), user, s -> user.broadcastBreakEvent(hand));
                user.getMainHandItem().setPopTime(3);
                if (gunBase.getReloadSound() != null)
                    user.level().playSound(null, user.getX(), user.getY(), user.getZ(), gunBase.getReloadSound(), SoundSource.PLAYERS, 1.00F, 1.0F);
                if (!user.level().isClientSide) {
                    if (user.getRandom().nextInt(0, 100) >= 95 && gunBase.getItemID().equalsIgnoreCase("tommy_gun"))
                        gunBase.triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()), AzureAnimatedGunItem.controller, "tommyreload2");
                    else
                        gunBase.triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()), AzureAnimatedGunItem.controller, "reload");
                }
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        tooltip.add(Component.translatable("Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
        switch (getProjectileTypeEnum()) {
            case BULLET, HELL ->
                    tooltip.add(Component.translatable("hwg.ammo.reloadbullets").withStyle(ChatFormatting.ITALIC));
            case BLAZE ->
                    tooltip.add(Component.translatable("hwg.ammo.reloadblazerod").withStyle(ChatFormatting.ITALIC));
            case FIREBALL, FLAMES -> {
                tooltip.add(Component.translatable("hwg.ammo.reloadfuel").withStyle(ChatFormatting.ITALIC));
                tooltip.add(Component.translatable("Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
            }
            case MEANIE ->
                    tooltip.add(Component.translatable("hwg.ammo.reloadredstone").withStyle(ChatFormatting.ITALIC));
            case SHELL -> tooltip.add(Component.translatable("hwg.ammo.reloadshells").withStyle(ChatFormatting.ITALIC));
            case ROCKET ->
                    tooltip.add(Component.translatable("hwg.ammo.reloadrockets").withStyle(ChatFormatting.ITALIC));
            case SILVER_BULLET ->
                    tooltip.add(Component.translatable("hwg.ammo.reloadsilverbullets").withStyle(ChatFormatting.ITALIC));
        }
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, AzureAnimatedGunItem.controller, event -> {
            if (event.getAnimatable().gunTypeEnum == GunTypeEnum.BRIMSTONE || event.getAnimatable().gunTypeEnum == GunTypeEnum.BALROG)
                return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            return PlayState.CONTINUE;
        }).triggerableAnim(AzureAnimatedGunItem.firing, RawAnimation.begin().then(AzureAnimatedGunItem.firing, Animation.LoopType.PLAY_ONCE))//firing
                .triggerableAnim("tommyreload2", RawAnimation.begin().then("tommyreload2", Animation.LoopType.PLAY_ONCE))//reload tommy special
                .triggerableAnim("reload", RawAnimation.begin().then("reload", Animation.LoopType.PLAY_ONCE)).setSoundKeyframeHandler(event -> {
                    Player player = ClientUtils.getClientPlayer();
                    if (this.getGunTypeEnum() == GunTypeEnum.FLAMETHROWER && event.getKeyframeData().getSound().matches("tank") && player.level().isClientSide())
                        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.METAL_PLACE, SoundSource.HOSTILE, 0.25F, 1.0F, false);
                }));//reload
    }

    @Override
    public boolean isPerspectiveAware() {
        return true;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GunRender<AzureAnimatedGunItem> renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new GunRender<AzureAnimatedGunItem>(getItemID(), getGunTypeEnum());
                return this.renderer;
            }
        });
    }
}
