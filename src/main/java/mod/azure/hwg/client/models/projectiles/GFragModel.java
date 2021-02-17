package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FragGrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GFragModel extends AnimatedGeoModel<FragGrenadeEntity> {
	@Override
	public Identifier getModelLocation(FragGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(FragGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_frag.png");
	}

	@Override
	public Identifier getAnimationFileLocation(FragGrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
