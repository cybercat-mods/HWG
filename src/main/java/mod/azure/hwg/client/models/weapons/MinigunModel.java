package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AnimatedItem;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class MinigunModel extends GeoModel<AnimatedItem> {
	@Override
	public Identifier getModelResource(AnimatedItem object) {
		return new Identifier(HWGMod.MODID, "geo/minigun.geo.json");
	}

	@Override
	public Identifier getTextureResource(AnimatedItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/minigun.png");
	}

	@Override
	public Identifier getAnimationResource(AnimatedItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/minigun.animation.json");
	}
}
