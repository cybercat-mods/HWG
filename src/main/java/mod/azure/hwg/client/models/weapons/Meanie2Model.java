package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Meanie2Item;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class Meanie2Model extends GeoModel<Meanie2Item> {
	@Override
	public Identifier getModelResource(Meanie2Item object) {
		return new Identifier(HWGMod.MODID, "geo/meanie_gun_2.geo.json");
	}

	@Override
	public Identifier getTextureResource(Meanie2Item object) {
		return new Identifier(HWGMod.MODID, "textures/item/meanie_gun.png");
	}

	@Override
	public Identifier getAnimationResource(Meanie2Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
