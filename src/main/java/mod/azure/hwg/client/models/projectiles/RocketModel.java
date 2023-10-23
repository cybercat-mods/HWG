package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.RocketEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RocketModel extends GeoModel<RocketEntity> {
    @Override
    public ResourceLocation getModelResource(RocketEntity object) {
        return new ResourceLocation(HWGMod.MODID, "geo/rocket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketEntity object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/projectiles/rocket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketEntity animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/rocket.animation.json");
    }

    @Override
    public RenderType getRenderType(RocketEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
