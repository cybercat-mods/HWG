package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import net.minecraft.resources.ResourceLocation;

public class HellhorseRevolverModel extends GeoModel<HellhorseRevolverItem> {
    @Override
    public ResourceLocation getModelResource(HellhorseRevolverItem object) {
        return new ResourceLocation(HWGMod.MODID, "geo/hellhorse_revolver.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HellhorseRevolverItem object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/hellhorse_revolver.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HellhorseRevolverItem animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/hellhorse_revolver.animation.json");
    }
}
