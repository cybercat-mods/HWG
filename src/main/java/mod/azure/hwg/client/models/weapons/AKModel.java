package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AssasultItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AKModel extends AnimatedGeoModel<AssasultItem> {
	@Override
	public Identifier getModelLocation(AssasultItem object) {
		return new Identifier(HWGMod.MODID, "geo/ak47.geo.json");
	}

	@Override
	public Identifier getTextureLocation(AssasultItem object) {
		return new Identifier(HWGMod.MODID, "textures/items/ak47.png");
	}

	@Override
	public Identifier getAnimationFileLocation(AssasultItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
