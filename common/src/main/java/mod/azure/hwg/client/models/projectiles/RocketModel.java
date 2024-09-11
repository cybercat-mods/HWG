package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.projectiles.RocketEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RocketModel extends GeoModel<RocketEntity> {
    @Override
    public ResourceLocation getModelResource(RocketEntity object) {
        return CommonMod.modResource("geo/rocket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketEntity object) {
        return CommonMod.modResource("textures/item/projectiles/rocket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketEntity animatable) {
        return CommonMod.modResource("animations/rocket.animation.json");
    }

    @Override
    public RenderType getRenderType(RocketEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
