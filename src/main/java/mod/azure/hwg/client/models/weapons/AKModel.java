package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AssasultItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AKModel extends GeoModel<AssasultItem> {
	@Override
	public Identifier getModelResource(AssasultItem object) {
		return new Identifier(HWGMod.MODID, "geo/ak47.geo.json");
	}

	@Override
	public Identifier getTextureResource(AssasultItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/ak47.png");
	}

	@Override
	public Identifier getAnimationResource(AssasultItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
