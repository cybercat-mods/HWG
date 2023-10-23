package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Meanie1Item;
import net.minecraft.resources.ResourceLocation;

public class Meanie1Model extends GeoModel<Meanie1Item> {
    @Override
    public ResourceLocation getModelResource(Meanie1Item object) {
        return new ResourceLocation(HWGMod.MODID, "geo/meanie_gun_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Meanie1Item object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/meanie_gun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Meanie1Item animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/pistol.animation.json");
    }
}
