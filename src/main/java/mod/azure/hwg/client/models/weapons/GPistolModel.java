package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.GPistolItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GPistolModel extends AnimatedGeoModel<GPistolItem> {
	@Override
	public Identifier getModelResource(GPistolItem object) {
		return new Identifier(HWGMod.MODID, "geo/golden_gun.geo.json");
	}

	@Override
	public Identifier getTextureResource(GPistolItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/golden_gun.png");
	}

	@Override
	public Identifier getAnimationResource(GPistolItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
