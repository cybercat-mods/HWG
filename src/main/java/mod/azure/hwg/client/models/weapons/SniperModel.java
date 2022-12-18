package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SniperItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SniperModel extends GeoModel<SniperItem> {
	@Override
	public Identifier getModelResource(SniperItem object) {
		return new Identifier(HWGMod.MODID, "geo/sniper_rifle.geo.json");
	}

	@Override
	public Identifier getTextureResource(SniperItem object) {
		return new Identifier(HWGMod.MODID, "textures/item/sniper_rifle.png");
	}

	@Override
	public Identifier getAnimationResource(SniperItem animatable) {
		return new Identifier(HWGMod.MODID, "animations/sniper_rifle.animation.json");
	}
}
