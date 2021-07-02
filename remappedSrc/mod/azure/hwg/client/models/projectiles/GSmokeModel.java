package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SmokeGrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GSmokeModel extends AnimatedGeoModel<SmokeGrenadeEntity> {
	@Override
	public Identifier getModelLocation(SmokeGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SmokeGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_smoke.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SmokeGrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
