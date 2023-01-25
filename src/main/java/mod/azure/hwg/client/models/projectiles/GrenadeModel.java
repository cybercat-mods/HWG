package mod.azure.hwg.client.models.projectiles;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelResource(GrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureResource(GrenadeEntity object) {
		return new Identifier(HWGMod.MODID, "textures/item/projectiles/grenade_"
				+ (object.getVariant() == 2 ? "frag"
						: object.getVariant() == 3 ? "napalm"
								: object.getVariant() == 4 ? "smoke" : object.getVariant() == 5 ? "stun" : "emp")
				+ ".png");
	}

	@Override
	public Identifier getAnimationResource(GrenadeEntity animatable) {
		return new Identifier(HWGMod.MODID, "animations/grenade.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(GrenadeEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
