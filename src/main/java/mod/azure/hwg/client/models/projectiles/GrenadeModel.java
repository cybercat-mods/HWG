package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
    @Override
    public ResourceLocation getModelResource(GrenadeEntity object) {
        return new ResourceLocation(HWGMod.MODID, "geo/grenade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrenadeEntity object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/projectiles/grenade_"
                + (object.getVariant() == 2 ? "frag"
                : object.getVariant() == 3 ? "napalm"
                : object.getVariant() == 4 ? "smoke" : object.getVariant() == 5 ? "stun" : "emp")
                + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadeEntity animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/grenade.animation.json");
    }

    @Override
    public RenderType getRenderType(GrenadeEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
