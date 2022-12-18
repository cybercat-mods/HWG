package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult2Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TommyGunModel extends GeoModel<Assasult2Item> {
	@Override
	public Identifier getModelResource(Assasult2Item object) {
		return new Identifier(HWGMod.MODID, "geo/tommy_gun.geo.json");
	}

	@Override
	public Identifier getTextureResource(Assasult2Item object) {
		return new Identifier(HWGMod.MODID, "textures/item/tommy_gun.png");
	}

	@Override
	public Identifier getAnimationResource(Assasult2Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
