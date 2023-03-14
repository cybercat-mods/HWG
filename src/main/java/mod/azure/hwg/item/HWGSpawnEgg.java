package mod.azure.hwg.item;

import mod.azure.hwg.HWGMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class HWGSpawnEgg extends SpawnEggItem {

	public HWGSpawnEgg(EntityType<? extends Mob> type, int primaryColor, int secondaryColor) {
		super(type, primaryColor, secondaryColor, new Item.Properties().stacksTo(64).tab(HWGMod.WeaponItemGroup));
	}

}