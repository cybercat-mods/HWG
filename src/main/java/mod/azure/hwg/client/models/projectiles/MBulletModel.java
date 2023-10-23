package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.MBulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MBulletModel extends GeoModel<MBulletEntity> {
    @Override
    public ResourceLocation getModelResource(MBulletEntity object) {
        return new ResourceLocation(HWGMod.MODID, "geo/bullet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MBulletEntity object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/bullet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MBulletEntity animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/bullet.animation.json");
    }

    @Override
    public RenderType getRenderType(MBulletEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
