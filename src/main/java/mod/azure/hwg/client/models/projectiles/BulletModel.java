package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BulletModel extends GeoModel<BulletEntity> {
	@Override
	public ResourceLocation getModelResource(BulletEntity object) {
		return new ResourceLocation(HWGMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BulletEntity object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/bullet.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BulletEntity animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/bullet.animation.json");
	}

	@Override
	public RenderType getRenderType(BulletEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
