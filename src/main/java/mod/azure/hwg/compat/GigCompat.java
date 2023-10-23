package mod.azure.hwg.compat;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.IncineratorUnitItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GigCompat {

    public static IncineratorUnitItem INCINERATOR = item(new IncineratorUnitItem(), "nostromo_flamethrower");

    static <T extends Item> T item(T c, String id) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(HWGMod.MODID, id), c);
        return c;
    }
}
