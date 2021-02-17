package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.StunGrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GStunModel extends AnimatedGeoModel<StunGrenadeEntity> {
	@Override
	public Identifier getModelLocation(StunGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(StunGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_stun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(StunGrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
