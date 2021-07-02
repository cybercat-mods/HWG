package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SilverBulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SilverBulletModel extends AnimatedGeoModel<SilverBulletEntity> {
	@Override
	public Identifier getModelLocation(SilverBulletEntity object) {
		return new Identifier(HWGMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SilverBulletEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/silver_bullet.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SilverBulletEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
}
