package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.HWGSpawnEgg;
import mod.azure.hwg.item.ammo.BulletAmmo;
import mod.azure.hwg.item.ammo.GrenadeEmpItem;
import mod.azure.hwg.item.ammo.GrenadeFragItem;
import mod.azure.hwg.item.ammo.GrenadeNapalmItem;
import mod.azure.hwg.item.ammo.GrenadeSmokeItem;
import mod.azure.hwg.item.ammo.GrenadeStunItem;
import mod.azure.hwg.item.weapons.AssasultItem;
import mod.azure.hwg.item.weapons.BalrogItem;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.FlareGunItem;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import mod.azure.hwg.item.weapons.Meanietem;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.RocketLauncher;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.item.weapons.ShotgunItem;
import mod.azure.hwg.item.weapons.SniperItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGItems {

	public static Minigun MINIGUN = item(new Minigun(), "minigun");
	public static PistolItem LUGER = item(new PistolItem(3.5), "luger");
	public static BulletAmmo FLARE = item(new BulletAmmo(1.2F), "flare");
	public static PistolItem PISTOL = item(new PistolItem(2.5), "pistol");
	public static BalrogItem BALROG = item(new BalrogItem(), "balrog_gun");
	public static BulletAmmo ROCKET = item(new BulletAmmo(1.2F), "rocket");
	public static ShotgunItem SHOTGUN = item(new ShotgunItem(), "shotgun");
	public static SPistolItem SPISTOL = item(new SPistolItem(), "spistol");
	public static Meanietem MEANIE1 = item(new Meanietem(), "meanie_gun_1");
	public static Meanietem MEANIE2 = item(new Meanietem(), "meanie_gun_2");
	public static BulletAmmo BULLETS = item(new BulletAmmo(1.2F), "bullets");
	public static SniperItem SNIPER = item(new SniperItem(), "sniper_rifle");
	public static AssasultItem SMG = item(new AssasultItem(3.0, 51, 7), "smg");
	public static FlareGunItem FLARE_GUN = item(new FlareGunItem(), "flare_gun");
	public static AssasultItem AK47 = item(new AssasultItem(2.5, 21, 10), "ak47");
	public static PistolItem GOLDEN_GUN = item(new PistolItem(2.5), "golden_gun");
	public static GrenadeEmpItem G_EMP = item(new GrenadeEmpItem(), "grenade_emp");
	public static GrenadeStunItem G_STUN = item(new GrenadeStunItem(), "grenade_stun");
	public static BrimstoneItem BRIMSTONE = item(new BrimstoneItem(), "brimstone_gun");
	public static GrenadeFragItem G_FRAG = item(new GrenadeFragItem(), "grenade_frag");
	public static BulletAmmo SNIPER_ROUND = item(new BulletAmmo(1.2F), "sniper_round");
	public static BulletAmmo SHOTGUN_SHELL = item(new BulletAmmo(1.2F), "shotgun_shell");
	public static AssasultItem TOMMYGUN = item(new AssasultItem(2.5, 51, 5), "tommy_gun");
	public static GrenadeSmokeItem G_SMOKE = item(new GrenadeSmokeItem(), "grenade_smoke");
	public static RocketLauncher ROCKETLAUNCHER = item(new RocketLauncher(), "rocketlauncher");
	public static FlamethrowerItem FLAMETHROWER = item(new FlamethrowerItem(), "flamethrower");
	public static GrenadeNapalmItem G_NAPALM = item(new GrenadeNapalmItem(), "grenade_napalm");
	public static HWGSpawnEgg SPY_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.SPY), "spy_spawn_egg");
	public static HWGSpawnEgg MERC_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.MERC), "merc_spawn_egg");
	public static GrenadeLauncherItem G_LAUNCHER = item(new GrenadeLauncherItem(), "grenade_launcher");
	public static HellhorseRevolverItem HELLHORSE = item(new HellhorseRevolverItem(), "hellhorse_revolver");
	public static HWGSpawnEgg LESSER_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.TECHNOLESSER), "lesser_spawn_egg");
	public static HWGSpawnEgg GREATER_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.TECHNOGREATER), "greater_spawn_egg");
	public static BlockItem FUEL_TANK = item(new BlockItem(HWGMod.FUEL_TANK, new Item.Settings().group(HWGMod.WeaponItemGroup)), "fuel_tank");
	//public static BlockItem GUN_TABLE = item(new BlockItem(HWGMod.GUN_TABLE, new Item.Settings().group(HWGMod.WeaponItemGroup)), "gun_table");

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registry.ITEM, new Identifier(HWGMod.MODID, id), c);
		return c;
	}
}
