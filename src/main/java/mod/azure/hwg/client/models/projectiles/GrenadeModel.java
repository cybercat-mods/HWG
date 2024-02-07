package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
    @Override
    public ResourceLocation getModelResource(GrenadeEntity animatable) {
        return HWGMod.modResource("geo/grenade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrenadeEntity animatable) {
        return HWGMod.modResource("textures/item/projectiles/grenade_" + (animatable.getVariant() == 2 ? "frag" : animatable.getVariant() == 3 ? "napalm" : animatable.getVariant() == 4 ? "smoke" : animatable.getVariant() == 5 ? "stun" : "emp") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadeEntity animatable) {
        return HWGMod.modResource("animations/grenade.animation.json");
    }

    @Override
    public RenderType getRenderType(GrenadeEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
