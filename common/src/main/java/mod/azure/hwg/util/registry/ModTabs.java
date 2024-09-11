package mod.azure.hwg.util.registry;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public record ModTabs() {

    public static final Supplier<CreativeModeTab> ITEM_GROUP = Services.COMMON_REGISTRY.registerCreativeModeTab(
            CommonMod.MOD_ID,
            "items",
            () -> Services.COMMON_REGISTRY.newCreativeTabBuilder().title(
                    Component.translatable("itemGroup." + CommonMod.MOD_ID + ".weapons"))
                    .icon(() -> new ItemStack(HWGItems.AK47.get()))
                    .displayItems((enabledFeatures, entries) -> {
                        /**
                         * Weapons
                         */
                        entries.accept(HWGItems.PISTOL.get());
                        entries.accept(HWGItems.SPISTOL.get());
                        if (Services.COMMON_REGISTRY.isModLoaded("bewitchment")) entries.accept(HWGItems.SILVERGUN.get());
                        entries.accept(HWGItems.LUGER.get());
                        entries.accept(HWGItems.MEANIE1.get());
                        entries.accept(HWGItems.MEANIE2.get());
                        entries.accept(HWGItems.GOLDEN_GUN.get());
                        entries.accept(HWGItems.HELLHORSE.get());
                        if (Services.COMMON_REGISTRY.isModLoaded("bewitchment")) entries.accept(HWGItems.SILVERHELLHORSE.get());
                        entries.accept(HWGItems.AK47.get());
                        entries.accept(HWGItems.SMG.get());
                        entries.accept(HWGItems.TOMMYGUN.get());
                        entries.accept(HWGItems.MINIGUN.get());
                        entries.accept(HWGItems.SHOTGUN.get());
                        entries.accept(HWGItems.SNIPER.get());
                        if (Services.COMMON_REGISTRY.isModLoaded("gigeresque")) entries.accept(HWGItems.INCINERATOR.get());
                        entries.accept(HWGItems.FLAMETHROWER.get());
                        entries.accept(HWGItems.BALROG.get());
                        entries.accept(HWGItems.BRIMSTONE.get());
                        entries.accept(HWGItems.ROCKETLAUNCHER.get());
                        entries.accept(HWGItems.G_LAUNCHER.get());
                        entries.accept(HWGItems.FLARE_GUN.get());
                        /**
                         * Ammo
                         */
                        if (Services.COMMON_REGISTRY.isModLoaded("bewitchment")) entries.accept(HWGItems.SILVERBULLET.get());
                        entries.accept(HWGItems.BULLETS.get());
                        entries.accept(HWGItems.SHOTGUN_SHELL.get());
                        entries.accept(HWGItems.SNIPER_ROUND.get());
                        entries.accept(HWGItems.ROCKET.get());
                        entries.accept(HWGItems.G_FRAG.get());
                        entries.accept(HWGItems.G_STUN.get());
                        entries.accept(HWGItems.G_SMOKE.get());
                        entries.accept(HWGItems.G_NAPALM.get());
                        entries.accept(HWGItems.G_EMP.get());
                        entries.accept(HWGItems.RED_FLARE.get());
                        entries.accept(HWGItems.BLUE_FLARE.get());
                        entries.accept(HWGItems.CYAN_FLARE.get());
                        entries.accept(HWGItems.GRAY_FLARE.get());
                        entries.accept(HWGItems.LIME_FLARE.get());
                        entries.accept(HWGItems.PINK_FLARE.get());
                        entries.accept(HWGItems.BLACK_FLARE.get());
                        entries.accept(HWGItems.BROWN_FLARE.get());
                        entries.accept(HWGItems.GREEN_FLARE.get());
                        entries.accept(HWGItems.WHITE_FLARE.get());
                        entries.accept(HWGItems.ORANGE_FLARE.get());
                        entries.accept(HWGItems.PURPLE_FLARE.get());
                        entries.accept(HWGItems.YELLOW_FLARE.get());
                        entries.accept(HWGItems.MAGENTA_FLARE.get());
                        entries.accept(HWGItems.LIGHTBLUE_FLARE.get());
                        entries.accept(HWGItems.LIGHTGRAY_FLARE.get());
                        /**
                         * Blocks
                         */
                        entries.accept(HWGItems.FUEL_TANK.get());
                        entries.accept(HWGItems.GUN_TABLE.get());
                        /**
                         * Spawn Eggs
                         */
                        entries.accept(HWGItems.MERC_SPAWN_EGG.get());
                        entries.accept(HWGItems.SPY_SPAWN_EGG.get());
                        entries.accept(HWGItems.LESSER_SPAWN_EGG.get());
                        entries.accept(HWGItems.GREATER_SPAWN_EGG.get());
                    }).build());

    public static void init() {
    }
}
