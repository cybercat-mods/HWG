package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.PistolItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PistolModel extends AnimatedGeoModel<PistolItem> {
	@Override
	public Identifier getModelLocation(PistolItem object) {
		return new Identifier(HWGMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PistolItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/pistol.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PistolItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
