package mod.azure.hwg.compat;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.IncineratorUnitItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GigCompat {

	public static IncineratorUnitItem INCINERATOR = item(new IncineratorUnitItem(), "nostromo_flamethrower");

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registries.ITEM, new Identifier(HWGMod.MODID, id), c);
		return c;
	}
}
