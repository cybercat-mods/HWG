package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult1Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SMGModel extends GeoModel<Assasult1Item> {
	@Override
	public Identifier getModelResource(Assasult1Item object) {
		return new Identifier(HWGMod.MODID, "geo/smg.geo.json");
	}

	@Override
	public Identifier getTextureResource(Assasult1Item object) {
		return new Identifier(HWGMod.MODID, "textures/item/smg.png");
	}

	@Override
	public Identifier getAnimationResource(Assasult1Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
