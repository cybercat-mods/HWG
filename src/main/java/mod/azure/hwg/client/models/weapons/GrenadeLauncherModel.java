package mod.azure.hwg.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;
import net.minecraft.resources.ResourceLocation;

public class GrenadeLauncherModel extends GeoModel<GrenadeLauncherItem> {
    @Override
    public ResourceLocation getModelResource(GrenadeLauncherItem object) {
        return new ResourceLocation(HWGMod.MODID, "geo/grenade_launcher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrenadeLauncherItem object) {
        return new ResourceLocation(HWGMod.MODID, "textures/item/grenade_launcher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadeLauncherItem animatable) {
        return new ResourceLocation(HWGMod.MODID, "animations/grenade_launcher.animation.json");
    }
}
