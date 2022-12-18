package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShellModel extends GeoModel<ShellEntity> {
	@Override
	public Identifier getModelResource(ShellEntity object) {
		return new Identifier(HWGMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShellEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/shotgun_shell.png");
	}

	@Override
	public Identifier getAnimationResource(ShellEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/bullet.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(ShellEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
