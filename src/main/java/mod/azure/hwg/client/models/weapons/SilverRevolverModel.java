package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SilverRevolverModel extends AnimatedGeoModel<SilverRevolverItem> {
	@Override
	public Identifier getModelResource(SilverRevolverItem object) {
		return new Identifier(HWGMod.MODID, "geo/hellhorse_revolver.geo.json");
	}

	@Override
	public Identifier getTextureResource(SilverRevolverItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/silver_hellhorse_revolver.png");
	}

	@Override
	public Identifier getAnimationResource(SilverRevolverItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/hellhorse_revolver.animation.json");
	}
}