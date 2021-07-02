package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.launcher.StunGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GStunSModel extends AnimatedGeoModel<StunGEntity> {
	@Override
	public Identifier getModelLocation(StunGEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(StunGEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_stun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(StunGEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
