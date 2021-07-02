package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.launcher.FragGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GFragSModel extends AnimatedGeoModel<FragGEntity> {
	@Override
	public Identifier getModelLocation(FragGEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(FragGEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_frag.png");
	}

	@Override
	public Identifier getAnimationFileLocation(FragGEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
