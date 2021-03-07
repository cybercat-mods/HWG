package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.launcher.SmokeGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GSmokeSModel extends AnimatedGeoModel<SmokeGEntity> {
	@Override
	public Identifier getModelLocation(SmokeGEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SmokeGEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_smoke.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SmokeGEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
