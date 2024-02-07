package mod.azure.hwg.util.registry;

import mod.azure.azurelib.common.api.common.items.AzureSpawnEgg;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.ammo.*;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.item.enums.ProjectileEnum;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import mod.azure.hwg.item.weapons.FlareGunItem;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public record HWGItems() {
    /**
     * Flares
     */
    public static FlareItem RED_FLARE = item(new FlareItem(), "red_flare");
    public static FlareItem BLUE_FLARE = item(new FlareItem(), "blue_flare");
    public static FlareItem CYAN_FLARE = item(new FlareItem(), "cyan_flare");
    public static FlareItem GRAY_FLARE = item(new FlareItem(), "gray_flare");
    public static FlareItem LIME_FLARE = item(new FlareItem(), "lime_flare");
    public static FlareItem PINK_FLARE = item(new FlareItem(), "pink_flare");
    public static FlareItem BLACK_FLARE = item(new FlareItem(), "black_flare");
    public static FlareItem BROWN_FLARE = item(new FlareItem(), "brown_flare");
    public static FlareItem GREEN_FLARE = item(new FlareItem(), "green_flare");
    public static FlareItem WHITE_FLARE = item(new FlareItem(), "white_flare");
    public static FlareItem ORANGE_FLARE = item(new FlareItem(), "orange_flare");
    public static FlareItem PURPLE_FLARE = item(new FlareItem(), "purple_flare");
    public static FlareItem YELLOW_FLARE = item(new FlareItem(), "yellow_flare");
    public static FlareItem MAGENTA_FLARE = item(new FlareItem(), "magenta_flare");
    public static FlareItem LIGHTBLUE_FLARE = item(new FlareItem(), "lightblue_flare");
    public static FlareItem LIGHTGRAY_FLARE = item(new FlareItem(), "lightgray_flare");

    /**
     * Ammo
     */
    public static Item ROCKET = item(new Item(new Item.Properties()), "rocket");
    public static Item BULLETS = item(new Item(new Item.Properties()), "bullets");
    public static Item SNIPER_ROUND = item(new Item(new Item.Properties()), "sniper_round");
    public static Item SHOTGUN_SHELL = item(new Item(new Item.Properties()), "shotgun_shell");
    public static Item SILVERBULLET = item(new Item(new Item.Properties()), "silver_bullet");
    public static GrenadeEmpItem G_EMP = item(new GrenadeEmpItem(), "grenade_emp");
    public static GrenadeFragItem G_FRAG = item(new GrenadeFragItem(), "grenade_frag");
    public static GrenadeStunItem G_STUN = item(new GrenadeStunItem(), "grenade_stun");
    public static GrenadeSmokeItem G_SMOKE = item(new GrenadeSmokeItem(), "grenade_smoke");
    public static GrenadeNapalmItem G_NAPALM = item(new GrenadeNapalmItem(), "grenade_napalm");
    public static BlockItem FUEL_TANK = item(new BlockItem(HWGBlocks.FUEL_TANK, new Item.Properties()), "fuel_tank");

    /**
     * Guns
     */
    public static FlareGunItem FLARE_GUN = item(new FlareGunItem(), "flare_gun");
    public static GrenadeLauncherItem G_LAUNCHER = item(new GrenadeLauncherItem(), "grenade_launcher");
    public static AzureAnimatedGunItem FLAMETHROWER = item(new AzureAnimatedGunItem("flamethrower", ProjectileEnum.FLAMES, GunTypeEnum.FLAMETHROWER, HWGMod.config.gunconfigs.flammerconfigs.flammer_cap, SoundEvents.METAL_PLACE, SoundEvents.FIRECHARGE_USE) {
    }, "flamethrower");
    public static AzureAnimatedGunItem MINIGUN = item(new AzureAnimatedGunItem("minigun", ProjectileEnum.BULLET, GunTypeEnum.MINIGUN, HWGMod.config.gunconfigs.minigunconfigs.minigun_cap, HWGSounds.CLIPRELOAD, HWGSounds.MINIGUN) {
    }, "minigun");
    public static AzureAnimatedGunItem LUGER = item(new AzureAnimatedGunItem("luger", ProjectileEnum.BULLET, GunTypeEnum.LUGER, HWGMod.config.gunconfigs.lugerconfigs.luger_cap, HWGSounds.CLIPRELOAD, HWGSounds.LUGER) {
    }, "luger");
    public static AzureAnimatedGunItem PISTOL = item(new AzureAnimatedGunItem("pistol", ProjectileEnum.BULLET, GunTypeEnum.PISTOL, HWGMod.config.gunconfigs.pistolconfigs.pistol_cap, HWGSounds.CLIPRELOAD, HWGSounds.PISTOL) {
    }, "pistol");
    public static AzureAnimatedGunItem SHOTGUN = item(new AzureAnimatedGunItem("shotgun", ProjectileEnum.SHELL, GunTypeEnum.SHOTGUN, HWGMod.config.gunconfigs.shotgunconfigs.shotgun_cap, HWGSounds.SHOTGUNRELOAD, HWGSounds.SHOTGUN) {
    }, "shotgun");
    public static AzureAnimatedGunItem SPISTOL = item(new AzureAnimatedGunItem("spistol", ProjectileEnum.BULLET, GunTypeEnum.SIL_PISTOL, HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_cap, HWGSounds.PISTOLRELOAD, HWGSounds.SPISTOL) {
    }, "spistol");
    public static AzureAnimatedGunItem SNIPER = item(new AzureAnimatedGunItem("sniper_rifle", ProjectileEnum.BULLET, GunTypeEnum.SNIPER, HWGMod.config.gunconfigs.sniperconfigs.sniper_cap, HWGSounds.SNIPERRELOAD, HWGSounds.SNIPER) {
    }, "sniper_rifle");
    public static AzureAnimatedGunItem MEANIE1 = item(new AzureAnimatedGunItem("meanie_gun_1", ProjectileEnum.MEANIE, GunTypeEnum.MEANIE, HWGMod.config.gunconfigs.meanieconfigs.meanie_cap, HWGSounds.PISTOLRELOAD, SoundEvents.ARMOR_EQUIP_IRON) {
    }, "meanie_gun_1");
    public static AzureAnimatedGunItem MEANIE2 = item(new AzureAnimatedGunItem("meanie_gun_2", ProjectileEnum.MEANIE, GunTypeEnum.MEANIE, HWGMod.config.gunconfigs.meanieconfigs.meanie_cap, HWGSounds.PISTOLRELOAD, SoundEvents.ARMOR_EQUIP_IRON) {
    }, "meanie_gun_2");
    public static AzureAnimatedGunItem GOLDEN_GUN = item(new AzureAnimatedGunItem("golden_gun", ProjectileEnum.BULLET, GunTypeEnum.GOLDEN_PISTOL, HWGMod.config.gunconfigs.gpistolconfigs.golden_pistol_cap, HWGSounds.PISTOLRELOAD, HWGSounds.PISTOL) {
    }, "golden_gun");
    public static AzureAnimatedGunItem ROCKETLAUNCHER = item(new AzureAnimatedGunItem("rocketlauncher", ProjectileEnum.ROCKET, GunTypeEnum.ROCKETLAUNCHER, HWGMod.config.gunconfigs.rocketlauncherconfigs.rocketlauncherCap, HWGSounds.GLAUNCHERRELOAD, HWGSounds.RPG) {
    }, "rocketlauncher");
    public static AzureAnimatedGunItem HELLHORSE = item(new AzureAnimatedGunItem("hellhorse_revolver", ProjectileEnum.HELL, GunTypeEnum.HELLHORSE, HWGMod.config.gunconfigs.hellhorseconfigs.hellhorse_cap, HWGSounds.REVOLVERRELOAD, HWGSounds.REVOLVER) {
    }, "hellhorse_revolver");
    public static AzureAnimatedGunItem SILVERGUN = item(new AzureAnimatedGunItem("silvergun", ProjectileEnum.SILVER_BULLET, GunTypeEnum.SILVER_PISTOL, HWGMod.config.gunconfigs.pistolconfigs.pistol_cap, HWGSounds.CLIPRELOAD, HWGSounds.PISTOL) {
    }, "silvergun");
    public static AzureAnimatedGunItem SILVERHELLHORSE = item(new AzureAnimatedGunItem("shellhorse_revolver", ProjectileEnum.SILVER_BULLET, GunTypeEnum.SILVER_HELL, HWGMod.config.gunconfigs.hellhorseconfigs.hellhorse_cap, HWGSounds.REVOLVERRELOAD, HWGSounds.REVOLVER) {
    }, "shellhorse_revolver");
    public static AzureAnimatedGunItem AK47 = item(new AzureAnimatedGunItem("ak47", ProjectileEnum.BULLET, GunTypeEnum.AK7, HWGMod.config.gunconfigs.ak47configs.ak47_cap, HWGSounds.CLIPRELOAD, HWGSounds.AK) {
    }, "ak47");
    public static AzureAnimatedGunItem SMG = item(new AzureAnimatedGunItem("smg", ProjectileEnum.BULLET, GunTypeEnum.SMG, HWGMod.config.gunconfigs.smgconfigs.smg_cap, HWGSounds.CLIPRELOAD, HWGSounds.SMG) {
    }, "smg");
    public static AzureAnimatedGunItem TOMMYGUN = item(new AzureAnimatedGunItem("tommy_gun", ProjectileEnum.BULLET, GunTypeEnum.TOMMYGUN, HWGMod.config.gunconfigs.tommyconfigs.tommy_cap, HWGSounds.CLIPRELOAD, HWGSounds.TOMMY) {
    }, "tommy_gun");
    public static AzureAnimatedGunItem BALROG = item(new AzureAnimatedGunItem("balrog_gun", ProjectileEnum.BLAZE, GunTypeEnum.BALROG, HWGMod.config.gunconfigs.balrogconfigs.balrog_cap, SoundEvents.FIRECHARGE_USE, SoundEvents.FIREWORK_ROCKET_BLAST_FAR) {
    }, "balrog_gun");
    public static AzureAnimatedGunItem BRIMSTONE = item(new AzureAnimatedGunItem("brimstone_gun", ProjectileEnum.FIREBALL, GunTypeEnum.BRIMSTONE, HWGMod.config.gunconfigs.brimstoneconfigs.brimstone_cap, SoundEvents.FIRECHARGE_USE, SoundEvents.SHULKER_SHOOT) {
    }, "brimstone_gun");
    public static AzureAnimatedGunItem INCINERATOR = item(new AzureAnimatedGunItem("nostromo_flamethrower", ProjectileEnum.FLAMES, GunTypeEnum.FLAMETHROWER, HWGMod.config.gunconfigs.flammerconfigs.flammer_cap, SoundEvents.METAL_HIT, SoundEvents.FIRECHARGE_USE) {
    }, "nostromo_flamethrower");

    /**
     * Blocks
     */
    public static BlockItem GUN_TABLE = item(new BlockItem(HWGBlocks.GUN_TABLE, new Item.Properties()), "gun_table");

    /**
     * Spawn Eggs
     */
    public static AzureSpawnEgg SPY_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.SPY, 11022961, 11035249), "spy_spawn_egg");
    public static AzureSpawnEgg MERC_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.MERC, 11022961, 11035249), "merc_spawn_egg");
    public static AzureSpawnEgg LESSER_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.TECHNOLESSER, 11022961, 11035249), "lesser_spawn_egg");
    public static AzureSpawnEgg GREATER_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.TECHNOGREATER, 11022961, 11035249), "greater_spawn_egg");


    public static Item[] ITEMS = {HELLHORSE, G_LAUNCHER, ROCKETLAUNCHER, FLAMETHROWER, TOMMYGUN, BRIMSTONE, GOLDEN_GUN, AK47, FLARE_GUN, SMG, SNIPER, MEANIE1, MEANIE2, SPISTOL, SHOTGUN, BALROG, PISTOL, LUGER, MINIGUN};

    static <T extends Item> T item(T c, String id) {
        Registry.register(BuiltInRegistries.ITEM, HWGMod.modResource(id), c);
        return c;
    }

    public static Map<Item, Item> getItemMap() {
        Map<Item, Item> vanillaItemMap = new HashMap<>();
        for (Item i : HWGItems.ITEMS) {
            vanillaItemMap.put(BuiltInRegistries.ITEM.get(HWGMod.modResource(BuiltInRegistries.ITEM.getKey(i).getPath())), i);
        }
        return vanillaItemMap;
    }

    public static void initialize() {
    }
}
