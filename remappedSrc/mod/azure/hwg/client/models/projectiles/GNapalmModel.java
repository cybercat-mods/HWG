package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.NapalmGrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GNapalmModel extends AnimatedGeoModel<NapalmGrenadeEntity> {
	@Override
	public Identifier getModelLocation(NapalmGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(NapalmGrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_napalm.png");
	}

	@Override
	public Identifier getAnimationFileLocation(NapalmGrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
