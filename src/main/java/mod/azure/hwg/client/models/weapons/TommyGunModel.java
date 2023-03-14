package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult2Item;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class TommyGunModel extends GeoModel<Assasult2Item> {
	@Override
	public ResourceLocation getModelResource(Assasult2Item object) {
		return new ResourceLocation(HWGMod.MODID, "geo/tommy_gun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Assasult2Item object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/tommy_gun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Assasult2Item animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
