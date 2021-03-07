package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.launcher.EMPGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GEMPSModel extends AnimatedGeoModel<EMPGEntity> {
	@Override
	public Identifier getModelLocation(EMPGEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(EMPGEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_emp.png");
	}

	@Override
	public Identifier getAnimationFileLocation(EMPGEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
