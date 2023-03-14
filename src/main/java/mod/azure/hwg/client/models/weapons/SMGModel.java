package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult1Item;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SMGModel extends GeoModel<Assasult1Item> {
	@Override
	public ResourceLocation getModelResource(Assasult1Item object) {
		return new ResourceLocation(HWGMod.MODID, "geo/smg.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Assasult1Item object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/smg.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Assasult1Item animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
