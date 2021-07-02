package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShellModel extends AnimatedGeoModel<ShellEntity> {
	@Override
	public Identifier getModelLocation(ShellEntity object) {
		return new Identifier(HWGMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ShellEntity object) {
		return new Identifier(HWGMod.MODID, "textures/items/shotgun_shell.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ShellEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
}
