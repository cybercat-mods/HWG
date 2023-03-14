package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Meanie2Item;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class Meanie2Model extends GeoModel<Meanie2Item> {
	@Override
	public ResourceLocation getModelResource(Meanie2Item object) {
		return new ResourceLocation(HWGMod.MODID, "geo/meanie_gun_2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Meanie2Item object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/meanie_gun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Meanie2Item animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
