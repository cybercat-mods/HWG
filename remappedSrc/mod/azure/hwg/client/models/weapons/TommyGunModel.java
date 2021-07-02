package mod.azure.hwg.client.models.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult2Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TommyGunModel extends AnimatedGeoModel<Assasult2Item> {
	@Override
	public Identifier getModelLocation(Assasult2Item object) {
		return new Identifier(HWGMod.MODID, "geo/tommy_gun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Assasult2Item object) {
		return new Identifier(HWGMod.MODID, "textures/items/tommy_gun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Assasult2Item animatable) {
		return new Identifier(HWGMod.MODID, "animations/assasult.animation.json");
	}
}
