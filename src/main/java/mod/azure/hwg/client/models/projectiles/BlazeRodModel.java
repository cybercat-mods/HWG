package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BlazeRodModel extends GeoModel<BlazeRodEntity> {
	@Override
	public ResourceLocation getModelResource(BlazeRodEntity object) {
		return new ResourceLocation(HWGMod.MODID, "geo/blaze_rod_projectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BlazeRodEntity object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/projectiles/blaze_rod_projectile.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BlazeRodEntity animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/bullet.animation.json");
	}

	@Override
	public RenderType getRenderType(BlazeRodEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
