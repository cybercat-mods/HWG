package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Meanie1Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Meanie1Model extends AnimatedGeoModel<Meanie1Item> {
	@Override
	public Identifier getModelLocation(Meanie1Item object) {
		return new Identifier(HWGMod.MODID, "geo/meanie_gun_1.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Meanie1Item object) {
		return new Identifier(HWGMod.MODID, "textures/items/meanie_gun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Meanie1Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
