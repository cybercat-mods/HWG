package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Meanie1Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Meanie1Model extends AnimatedGeoModel<Meanie1Item> {
	@Override
	public Identifier getModelResource(Meanie1Item object) {
		return new Identifier(HWGMod.MODID, "geo/meanie_gun_1.geo.json");
	}

	@Override
	public Identifier getTextureResource(Meanie1Item object) {
		return new Identifier(HWGMod.MODID, "textures/items/meanie_gun.png");
	}

	@Override
	public Identifier getAnimationResource(Meanie1Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
