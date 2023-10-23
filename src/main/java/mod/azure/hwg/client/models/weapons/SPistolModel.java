package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SPistolItem;
import net.minecraft.resources.ResourceLocation;

public class SPistolModel extends GeoModel<SPistolItem> {
    @Override
    public ResourceLocation getModelResource(SPistolItem object) {
        return new ResourceLocation(HWGMod.MODID, "geo/spistol.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SPistolItem object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/pistol.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SPistolItem animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/pistol.animation.json");
    }
}
