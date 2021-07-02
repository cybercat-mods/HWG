package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult1Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SMGModel extends AnimatedGeoModel<Assasult1Item> {
	@Override
	public Identifier getModelLocation(Assasult1Item object) {
		return new Identifier(HWGMod.MODID, "geo/smg.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Assasult1Item object) {
		return new Identifier(HWGMod.MODID, "textures/items/smg.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Assasult1Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
