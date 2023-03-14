package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.FlareGunItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class FlareGunModel extends GeoModel<FlareGunItem> {
	@Override
	public ResourceLocation getModelResource(FlareGunItem object) {
		return new ResourceLocation(HWGMod.MODID, "geo/flare_gun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FlareGunItem object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/flare_gun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(FlareGunItem animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/flare_gun.animation.json");
	}
}
