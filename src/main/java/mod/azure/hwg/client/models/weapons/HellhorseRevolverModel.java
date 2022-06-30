package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HellhorseRevolverModel extends AnimatedGeoModel<HellhorseRevolverItem> {
	@Override
	public Identifier getModelResource(HellhorseRevolverItem object) {
		return new Identifier(HWGMod.MODID, "geo/hellhorse_revolver.geo.json");
	}

	@Override
	public Identifier getTextureResource(HellhorseRevolverItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/hellhorse_revolver.png");
	}

	@Override
	public Identifier getAnimationResource(HellhorseRevolverItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/hellhorse_revolver.animation.json");
	}
}
