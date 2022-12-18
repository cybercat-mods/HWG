package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.MBulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MBulletModel extends GeoModel<MBulletEntity> {
	@Override
	public Identifier getModelResource(MBulletEntity object) {
		return new Identifier(HWGMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(MBulletEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/bullet.png");
	}

	@Override
	public Identifier getAnimationResource(MBulletEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(MBulletEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
