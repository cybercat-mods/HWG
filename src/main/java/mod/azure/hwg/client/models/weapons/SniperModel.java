package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.SniperItem;
import net.minecraft.resources.ResourceLocation;

public class SniperModel extends GeoModel<SniperItem> {
    @Override
    public ResourceLocation getModelResource(SniperItem object) {
        return new ResourceLocation(HWGMod.MODID, "geo/sniper_rifle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SniperItem object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/sniper_rifle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SniperItem animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/sniper_rifle.animation.json");
    }
}
