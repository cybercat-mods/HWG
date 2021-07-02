package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeLauncherModel extends AnimatedGeoModel<GrenadeLauncherItem> {
	@Override
	public Identifier getModelLocation(GrenadeLauncherItem object) {
		return new Identifier(HWGMod.MODID, "geo/grenade_launcher.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GrenadeLauncherItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/grenade_launcher.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GrenadeLauncherItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade_launcher.animation.json");
	}
}
