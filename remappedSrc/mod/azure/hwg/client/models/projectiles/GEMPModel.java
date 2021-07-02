package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.EMPGrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GEMPModel extends AnimatedGeoModel<EMPGrenadeEntity> {
	@Override
	public Identifier getModelLocation(EMPGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(EMPGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_emp.png");
	}

	@Override
	public Identifier getAnimationFileLocation(EMPGrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
