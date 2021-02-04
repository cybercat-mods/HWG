package mod.azure.hwg.item;

import mod.azure.hwg.HWGMod;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class HWGSpawnEgg extends SpawnEggItem {

	public HWGSpawnEgg(EntityType<?> type) {
		super(type, 11022961, 11035249, new Item.Settings().maxCount(1).group(HWGMod.WeaponItemGroup));
	}

}