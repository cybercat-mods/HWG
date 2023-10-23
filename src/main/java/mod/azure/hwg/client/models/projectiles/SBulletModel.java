package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SBulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SBulletModel extends GeoModel<SBulletEntity> {
    @Override
    public ResourceLocation getModelResource(SBulletEntity object) {
        return new ResourceLocation(HWGMod.MODID, "geo/bullet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SBulletEntity object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/silver_bullet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SBulletEntity animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/bullet.animation.json");
    }

    @Override
    public RenderType getRenderType(SBulletEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
