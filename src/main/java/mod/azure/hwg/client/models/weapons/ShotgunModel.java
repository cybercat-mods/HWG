package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.ShotgunItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {
	@Override
	public ResourceLocation getModelResource(ShotgunItem object) {
		return new ResourceLocation(HWGMod.MODID, "geo/shotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShotgunItem object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/shotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ShotgunItem animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/shotgun.animation.json");
	}
}
