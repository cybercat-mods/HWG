package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AnimatedItem;
import net.minecraft.resources.ResourceLocation;

public class MinigunModel extends GeoModel<AnimatedItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedItem object) {
        return new ResourceLocation(HWGMod.MODID, "geo/minigun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedItem object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/minigun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedItem animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/minigun.animation.json");
    }
}
