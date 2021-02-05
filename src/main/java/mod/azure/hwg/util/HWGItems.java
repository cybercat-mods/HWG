package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.HWGSpawnEgg;
import mod.azure.hwg.item.ammo.BulletAmmo;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.SPistolItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGItems {

	public static PistolItem PISTOL = item(new PistolItem(), "pistol");
	public static BulletAmmo FLARE = item(new BulletAmmo(1.2F), "flare");
	public static BulletAmmo ROCKET = item(new BulletAmmo(1.2F), "rocket");
	public static SPistolItem SPISTOL = item(new SPistolItem(), "spistol");
	public static BulletAmmo BULLETS = item(new BulletAmmo(1.2F), "bullets");
	public static BulletAmmo SNIPER_ROUND = item(new BulletAmmo(1.2F), "sniper_round");
	public static BulletAmmo SHOTGUN_SHELL = item(new BulletAmmo(1.2F), "shotgun_shell");
	public static FlamethrowerItem FLAMETHROWER = item(new FlamethrowerItem(), "flamethrower");
	public static HWGSpawnEgg SPY_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.SPY), "spy_spawn_egg");
	public static HWGSpawnEgg MERC_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.MERC), "merc_spawn_egg");
	public static HWGSpawnEgg LESSER_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.TECHNOLESSER), "lesser_spawn_egg");
	public static HWGSpawnEgg GREATER_SPAWN_EGG = item(new HWGSpawnEgg(HWGMobs.TECHNOGREATER), "greater_spawn_egg");
	public static BlockItem FUEL_TANK = item(new BlockItem(HWGMod.FUEL_TANK, new Item.Settings().group(HWGMod.WeaponItemGroup)), "fuel_tank");

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registry.ITEM, new Identifier(HWGMod.MODID, id), c);
		return c;
	}
}
