package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.PistolItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PistolModel extends GeoModel<PistolItem> {
	@Override
	public Identifier getModelResource(PistolItem object) {
		return new Identifier(HWGMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public Identifier getTextureResource(PistolItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/pistol.png");
	}

	@Override
	public Identifier getAnimationResource(PistolItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
