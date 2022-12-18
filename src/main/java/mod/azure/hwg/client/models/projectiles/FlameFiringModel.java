package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlameFiringModel extends GeoModel<FlameFiring> {
	@Override
	public Identifier getModelResource(FlameFiring object) {
		return new Identifier(HWGMod.MODID, "geo/flamefiring.geo.json");
	}

	@Override
	public Identifier getTextureResource(FlameFiring object) {
		return new Identifier(HWGMod.MODID, "textures/item/empty.png");
	}

	@Override
	public Identifier getAnimationResource(FlameFiring animatable) {
		return new Identifier(HWGMod.MODID, "animations/flamefiring.animation.json");
	}
}
