package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlameFiringModel extends AnimatedGeoModel<FlameFiring> {
	@Override
	public Identifier getModelResource(FlameFiring object) {
		return new Identifier(HWGMod.MODID, "geo/flamefiring.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlameFiring object) {
		return new Identifier(HWGMod.MODID, "textures/items/empty.png");
	}

	@Override
	public Identifier getAnimationResource(FlameFiring animatable) {
		return new Identifier(HWGMod.MODID, "animations/flamefiring.animation.json");
	}
}
