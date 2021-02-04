package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.HWGSpawnEgg;
import mod.azure.hwg.item.ammo.BulletAmmo;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.SPistolItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGItems {

	public static PistolItem PISTOL = item(new PistolItem(), "pistol");
	public static SPistolItem SPISTOL = item(new SPistolItem(), "spistol");
	public static BulletAmmo BULLETS = item(new BulletAmmo(1.2F), "bullets");
	public static FlamethrowerItem FLAMETHROWER = item(new FlamethrowerItem(), "flamethrower");
	public static HWGSpawnEgg LESSER_SPAWN_EGG = item("lesser_spawn_egg", new HWGSpawnEgg(HWGMobs.TECHNOLESSER));
	public static HWGSpawnEgg GREATER_SPAWN_EGG = item("greater_spawn_egg", new HWGSpawnEgg(HWGMobs.TECHNOGREATER));

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registry.ITEM, new Identifier(HWGMod.MODID, id), c);
		return c;
	}

	static <T extends Item> T item(String id, T c) {
		Registry.register(Registry.ITEM, new Identifier(HWGMod.MODID, id), c);
		return c;
	}
}
