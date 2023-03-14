package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SilverRevolverModel extends GeoModel<SilverRevolverItem> {
	@Override
	public ResourceLocation getModelResource(SilverRevolverItem object) {
		return new ResourceLocation(HWGMod.MODID, "geo/hellhorse_revolver.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SilverRevolverItem object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/silver_hellhorse_revolver.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SilverRevolverItem animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/hellhorse_revolver.animation.json");
	}
}