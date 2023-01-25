package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class HellhorseRevolverModel extends GeoModel<HellhorseRevolverItem> {
	@Override
	public Identifier getModelResource(HellhorseRevolverItem object) {
		return new Identifier(HWGMod.MODID, "geo/hellhorse_revolver.geo.json");
	}

	@Override
	public Identifier getTextureResource(HellhorseRevolverItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/hellhorse_revolver.png");
	}

	@Override
	public Identifier getAnimationResource(HellhorseRevolverItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/hellhorse_revolver.animation.json");
	}
}
