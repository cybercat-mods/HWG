package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ShellModel extends GeoModel<ShellEntity> {
	@Override
	public ResourceLocation getModelResource(ShellEntity object) {
		return new ResourceLocation(HWGMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShellEntity object) {
		return new ResourceLocation(HWGMod.MODID, "textures/item/shotgun_shell.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ShellEntity animatable) {
		return new ResourceLocation(HWGMod.MODID, "animations/bullet.animation.json");
	}
	
	@Override
	public RenderType getRenderType(ShellEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
