package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Meanie2Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Meanie2Model extends AnimatedGeoModel<Meanie2Item> {
	@Override
	public Identifier getModelResource(Meanie2Item object) {
		return new Identifier(HWGMod.MODID, "geo/meanie_gun_2.geo.json");
	}

	@Override
	public Identifier getTextureResource(Meanie2Item object) {
		return new Identifier(HWGMod.MODID, "textures/items/meanie_gun.png");
	}

	@Override
	public Identifier getAnimationResource(Meanie2Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}
