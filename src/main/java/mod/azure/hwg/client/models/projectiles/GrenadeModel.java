package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeModel extends AnimatedGeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelLocation(GrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_"
				+ (object.getVariant() == 2 ? "frag"
						: object.getVariant() == 3 ? "napalm"
								: object.getVariant() == 4 ? "smoke" : object.getVariant() == 5 ? "stun" : "emp")
				+ ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(GrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
