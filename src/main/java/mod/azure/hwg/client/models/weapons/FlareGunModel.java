package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.FlareGunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlareGunModel extends GeoModel<FlareGunItem> {
	@Override
	public Identifier getModelResource(FlareGunItem object) {
		return new Identifier(HWGMod.MODID, "geo/flare_gun.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlareGunItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/flare_gun.png");
	}

	@Override
	public Identifier getAnimationResource(FlareGunItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/flare_gun.animation.json");
	}
}
