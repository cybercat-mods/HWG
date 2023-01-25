package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SPistolItem;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class SPistolModel extends GeoModel<SPistolItem> {
	@Override
	public Identifier getModelResource(SPistolItem object) {
		return new Identifier(HWGMod.MODID, "geo/spistol.geo.json");
	}

	@Override
	public Identifier getTextureResource(SPistolItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/pistol.png");
	}

	@Override
	public Identifier getAnimationResource(SPistolItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
