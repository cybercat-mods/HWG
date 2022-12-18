package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SBulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SBulletModel extends GeoModel<SBulletEntity> {
	@Override
	public Identifier getModelResource(SBulletEntity object) {
		return new Identifier(HWGMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(SBulletEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/silver_bullet.png");
	}

	@Override
	public Identifier getAnimationResource(SBulletEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(SBulletEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
