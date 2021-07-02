package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SHellhorseRevolverModel extends AnimatedGeoModel<SilverRevolverItem> {
	@Override
	public Identifier getModelLocation(SilverRevolverItem object) {
		return new Identifier(HWGMod.MODID, "geo/hellhorse_revolver.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SilverRevolverItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/silver_hellhorse_revolver.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SilverRevolverItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/hellhorse_revolver.animation.json");
	}
}
