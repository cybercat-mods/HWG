package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SBulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SBulletModel extends AnimatedGeoModel<SBulletEntity> {
	@Override
	public Identifier getModelResource(SBulletEntity object) {
		return new Identifier(HWGMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(SBulletEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/silver_bullet.png");
	}

	@Override
	public Identifier getAnimationResource(SBulletEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
}
