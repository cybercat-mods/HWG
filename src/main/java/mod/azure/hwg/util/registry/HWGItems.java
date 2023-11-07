package mod.azure.hwg.util.registry;

import mod.azure.azurelib.items.AzureSpawnEgg;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.ammo.*;
import mod.azure.hwg.item.weapons.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public record HWGItems() {

    public static Minigun MINIGUN = item(new Minigun(), "minigun");
    public static LugerItem LUGER = item(new LugerItem(), "luger");
    public static PistolItem PISTOL = item(new PistolItem(), "pistol");
    public static BalrogItem BALROG = item(new BalrogItem(), "balrog_gun");
    public static FlareItem RED_FLARE = item(new FlareItem(), "red_flare");
    public static ShotgunItem SHOTGUN = item(new ShotgunItem(), "shotgun");
    public static SPistolItem SPISTOL = item(new SPistolItem(), "spistol");
    public static FlareItem BLUE_FLARE = item(new FlareItem(), "blue_flare");
    public static FlareItem CYAN_FLARE = item(new FlareItem(), "cyan_flare");
    public static FlareItem GRAY_FLARE = item(new FlareItem(), "gray_flare");
    public static FlareItem LIME_FLARE = item(new FlareItem(), "lime_flare");
    public static FlareItem PINK_FLARE = item(new FlareItem(), "pink_flare");
    public static SniperItem SNIPER = item(new SniperItem(), "sniper_rifle");
    public static FlareItem BLACK_FLARE = item(new FlareItem(), "black_flare");
    public static FlareItem BROWN_FLARE = item(new FlareItem(), "brown_flare");
    public static FlareItem GREEN_FLARE = item(new FlareItem(), "green_flare");
    public static FlareItem WHITE_FLARE = item(new FlareItem(), "white_flare");
    public static Meanie1Item MEANIE1 = item(new Meanie1Item(), "meanie_gun_1");
    public static Meanie2Item MEANIE2 = item(new Meanie2Item(), "meanie_gun_2");
    public static Item ROCKET = item(new Item(new Item.Properties()), "rocket");
    public static FlareGunItem FLARE_GUN = item(new FlareGunItem(), "flare_gun");
    public static FlareItem ORANGE_FLARE = item(new FlareItem(), "orange_flare");
    public static FlareItem PURPLE_FLARE = item(new FlareItem(), "purple_flare");
    public static FlareItem YELLOW_FLARE = item(new FlareItem(), "yellow_flare");
    public static GPistolItem GOLDEN_GUN = item(new GPistolItem(), "golden_gun");
    public static Item BULLETS = item(new Item(new Item.Properties()), "bullets");
    public static GrenadeEmpItem G_EMP = item(new GrenadeEmpItem(), "grenade_emp");
    public static FlareItem MAGENTA_FLARE = item(new FlareItem(), "magenta_flare");
    public static BrimstoneItem BRIMSTONE = item(new BrimstoneItem(), "brimstone_gun");
    public static GrenadeFragItem G_FRAG = item(new GrenadeFragItem(), "grenade_frag");
    public static GrenadeStunItem G_STUN = item(new GrenadeStunItem(), "grenade_stun");
    public static FlareItem LIGHTBLUE_FLARE = item(new FlareItem(), "lightblue_flare");
    public static FlareItem LIGHTGRAY_FLARE = item(new FlareItem(), "lightgray_flare");
    public static AssasultItem AK47 = item(new AssasultItem(HWGMod.config.gunconfigs.ak47configs.ak47_cap + 1, HWGMod.config.gunconfigs.ak47configs.ak47_cooldown, "akfiring"), "ak47");
    public static Assasult1Item SMG = item(new Assasult1Item(HWGMod.config.gunconfigs.smgconfigs.smg_cap + 1, HWGMod.config.gunconfigs.smgconfigs.smg_cooldown, "smgfiring"), "smg");
    public static GrenadeSmokeItem G_SMOKE = item(new GrenadeSmokeItem(), "grenade_smoke");
    public static Item SNIPER_ROUND = item(new Item(new Item.Properties()), "sniper_round");
    public static Item SHOTGUN_SHELL = item(new Item(new Item.Properties()), "shotgun_shell");
    public static FlamethrowerItem FLAMETHROWER = item(new FlamethrowerItem(), "flamethrower");
    public static GrenadeNapalmItem G_NAPALM = item(new GrenadeNapalmItem(), "grenade_napalm");
    public static RocketLauncher ROCKETLAUNCHER = item(new RocketLauncher(), "rocketlauncher");
    public static Assasult2Item TOMMYGUN = item(new Assasult2Item(HWGMod.config.gunconfigs.tommyconfigs.tommy_cap + 1, 2, "tommyfiring"), "tommy_gun");
    public static GrenadeLauncherItem G_LAUNCHER = item(new GrenadeLauncherItem(), "grenade_launcher");
    public static HellhorseRevolverItem HELLHORSE = item(new HellhorseRevolverItem(), "hellhorse_revolver");
    public static BlockItem FUEL_TANK = item(new BlockItem(HWGBlocks.FUEL_TANK, new Item.Properties()), "fuel_tank");
    public static BlockItem GUN_TABLE = item(new BlockItem(HWGBlocks.GUN_TABLE, new Item.Properties()), "gun_table");
    public static AzureSpawnEgg SPY_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.SPY, 11022961, 11035249), "spy_spawn_egg");
    public static AzureSpawnEgg MERC_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.MERC, 11022961, 11035249), "merc_spawn_egg");
    public static AzureSpawnEgg LESSER_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.TECHNOLESSER, 11022961, 11035249), "lesser_spawn_egg");
    public static AzureSpawnEgg GREATER_SPAWN_EGG = item(new AzureSpawnEgg(HWGMobs.TECHNOGREATER, 11022961, 11035249), "greater_spawn_egg");
    public static Item[] ITEMS = {HELLHORSE, G_LAUNCHER, ROCKETLAUNCHER, FLAMETHROWER, TOMMYGUN, BRIMSTONE, GOLDEN_GUN, AK47, FLARE_GUN, SMG, SNIPER, MEANIE1, MEANIE2, SPISTOL, SHOTGUN, BALROG, PISTOL, LUGER, MINIGUN};

    static <T extends Item> T item(T c, String id) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(HWGMod.MODID, id), c);
        return c;
    }

    public static Map<Item, Item> getItemMap() {
        Map<Item, Item> vanillaItemMap = new HashMap<>();
        for (Item i : HWGItems.ITEMS) {
            vanillaItemMap.put(BuiltInRegistries.ITEM.get(new ResourceLocation(HWGMod.MODID, BuiltInRegistries.ITEM.getKey(i).getPath())), i);
        }
        return vanillaItemMap;
    }

    public static void initialize() {
    }
}
