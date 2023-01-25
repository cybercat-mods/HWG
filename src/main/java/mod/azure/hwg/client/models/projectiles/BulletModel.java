package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class BulletModel extends GeoModel<BulletEntity> {
	@Override
	public Identifier getModelResource(BulletEntity object) {
		return new Identifier(HWGMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(BulletEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/bullet.png");
	}

	@Override
	public Identifier getAnimationResource(BulletEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(BulletEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
