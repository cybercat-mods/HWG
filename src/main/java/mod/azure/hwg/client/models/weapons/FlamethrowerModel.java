package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlamethrowerModel extends AnimatedGeoModel<FlamethrowerItem> {
	@Override
	public Identifier getModelLocation(FlamethrowerItem object) {
		return new Identifier(HWGMod.MODID, "geo/flamethrower.geo.json");
	}

	@Override
	public Identifier getTextureLocation(FlamethrowerItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/flamethrower.png");
	}

	@Override
	public Identifier getAnimationFileLocation(FlamethrowerItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/flamethrower.animation.json");
	}
}
