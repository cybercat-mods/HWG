package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.GPistolItem;
import net.minecraft.resources.ResourceLocation;

public class GPistolModel extends GeoModel<GPistolItem> {
    @Override
    public ResourceLocation getModelResource(GPistolItem object) {
        return new ResourceLocation(HWGMod.MODID, "geo/golden_gun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GPistolItem object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/golden_gun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GPistolItem animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/pistol.animation.json");
    }
}
