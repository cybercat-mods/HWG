package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.FlareGunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlareGunModel extends AnimatedGeoModel<FlareGunItem> {
	@Override
	public Identifier getModelLocation(FlareGunItem object) {
		return new Identifier(HWGMod.MODID, "geo/flare_gun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(FlareGunItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/flare_gun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(FlareGunItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/flare_gun.animation.json");
	}
}
