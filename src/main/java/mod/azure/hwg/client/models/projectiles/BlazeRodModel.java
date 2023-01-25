package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class BlazeRodModel extends GeoModel<BlazeRodEntity> {
	@Override
	public Identifier getModelResource(BlazeRodEntity object) {
		return new Identifier(HWGMod.MODID, "geo/blaze_rod_projectile.geo.json");
	}

	@Override
	public Identifier getTextureResource(BlazeRodEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/projectiles/blaze_rod_projectile.png");
	}

	@Override
	public Identifier getAnimationResource(BlazeRodEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(BlazeRodEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
