package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SilverGunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SilverGunModel extends GeoModel<SilverGunItem> {
	@Override
	public Identifier getModelResource(SilverGunItem object) {
		return new Identifier(HWGMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public Identifier getTextureResource(SilverGunItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/silvergun.png");
	}

	@Override
	public Identifier getAnimationResource(SilverGunItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/pistol.animation.json");
	}
}