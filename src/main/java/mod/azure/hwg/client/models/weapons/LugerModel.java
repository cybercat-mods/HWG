package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.LugerItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class LugerModel extends GeoModel<LugerItem> {
	@Override
	public ResourceLocation getModelResource(LugerItem object) {
		return new ResourceLocation(HWGMod.MODID, "geo/luger.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LugerItem object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/luger.png");
	}

	@Override
	public ResourceLocation getAnimationResource(LugerItem animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
