package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AnimatedItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MinigunModel extends AnimatedGeoModel<AnimatedItem> {
	@Override
	public Identifier getModelLocation(AnimatedItem object) {
		return new Identifier(HWGMod.MODID, "geo/minigun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(AnimatedItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/minigun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(AnimatedItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/minigun.animation.json");
	}
}
