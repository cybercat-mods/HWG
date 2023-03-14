package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AssasultItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class AKModel extends GeoModel<AssasultItem> {
	@Override
	public ResourceLocation getModelResource(AssasultItem object) {
		return new ResourceLocation(HWGMod.MODID, "geo/ak47.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AssasultItem object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/ak47.png");
	}

	@Override
	public ResourceLocation getAnimationResource(AssasultItem animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
