package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.RocketEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class RocketModel extends GeoModel<RocketEntity> {
	@Override
	public Identifier getModelResource(RocketEntity object) {
		return new Identifier(HWGMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public Identifier getTextureResource(RocketEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/projectiles/rocket.png");
	}

	@Override
	public Identifier getAnimationResource(RocketEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/rocket.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(RocketEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
