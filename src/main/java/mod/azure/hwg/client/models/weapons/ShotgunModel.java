package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.ShotgunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {
	@Override
	public Identifier getModelResource(ShotgunItem object) {
		return new Identifier(HWGMod.MODID, "geo/shotgun.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShotgunItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/shotgun.png");
	}

	@Override
	public Identifier getAnimationResource(ShotgunItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/shotgun.animation.json");
	}
}
