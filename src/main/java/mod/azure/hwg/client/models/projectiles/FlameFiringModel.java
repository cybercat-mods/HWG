package mod.azure.hwg.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import net.minecraft.resources.ResourceLocation;

public class FlameFiringModel extends GeoModel<FlameFiring> {
    @Override
    public ResourceLocation getModelResource(FlameFiring object) {
        return new ResourceLocation(HWGMod.MODID, "geo/flamefiring.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FlameFiring object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/empty.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FlameFiring animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/flamefiring.animation.json");
    }
}
