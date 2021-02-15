package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.RocketEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketModel extends AnimatedGeoModel<RocketEntity> {
	@Override
	public Identifier getModelLocation(RocketEntity object) {
		return new Identifier(HWGMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public Identifier getTextureLocation(RocketEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/rocket.png");
	}

	@Override
	public Identifier getAnimationFileLocation(RocketEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/rocket.animation.json");
	}
}
