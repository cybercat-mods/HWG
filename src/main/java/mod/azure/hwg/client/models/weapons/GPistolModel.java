package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.GPistolItem;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class GPistolModel extends GeoModel<GPistolItem> {
	@Override
	public Identifier getModelResource(GPistolItem object) {
		return new Identifier(HWGMod.MODID, "geo/golden_gun.geo.json");
	}

	@Override
	public Identifier getTextureResource(GPistolItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/golden_gun.png");
	}

	@Override
	public Identifier getAnimationResource(GPistolItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
