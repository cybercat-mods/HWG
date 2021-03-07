package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.launcher.NapalmGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GNapalmSModel extends AnimatedGeoModel<NapalmGEntity> {
	@Override
	public Identifier getModelLocation(NapalmGEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(NapalmGEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/projectiles/grenade_napalm.png");
	}

	@Override
	public Identifier getAnimationFileLocation(NapalmGEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
}
