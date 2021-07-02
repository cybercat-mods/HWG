package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SPistolItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SPistolModel extends AnimatedGeoModel<SPistolItem> {
	@Override
	public Identifier getModelLocation(SPistolItem object) {
		return new Identifier(HWGMod.MODID, "geo/spistol.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SPistolItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/pistol.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SPistolItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
