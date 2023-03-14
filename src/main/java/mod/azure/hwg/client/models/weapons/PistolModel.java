package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.PistolItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PistolModel extends GeoModel<PistolItem> {
	@Override
	public ResourceLocation getModelResource(PistolItem object) {
		return new ResourceLocation(HWGMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PistolItem object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/pistol.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PistolItem animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
