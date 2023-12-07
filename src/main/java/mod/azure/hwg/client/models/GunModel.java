package mod.azure.hwg.client.models;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.model.DefaultedItemGeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.enums.GunTypeEnum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GunModel<T extends Item & GeoItem> extends DefaultedItemGeoModel<T> {
    private final GunTypeEnum gunTypeEnum;

    public GunModel(ResourceLocation assetSubpath, GunTypeEnum gunTypeEnum) {
        super(assetSubpath);
        this.gunTypeEnum = gunTypeEnum;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        switch (this.gunTypeEnum) {
            case PISTOL, SIL_PISTOL, SILVER_PISTOL -> {
                return HWGMod.modResource("animations/item/pistol/pistol.animation.json");
            }
            case MEANIE -> {
                return HWGMod.modResource("animations/item/meanie/meanie.animation.json");
            }
            case HELLHORSE, SILVER_HELL -> {
                return HWGMod.modResource("animations/item/hellhorse_revolver/hellhorse_revolver.animation.json");
            }
        }
        return super.getAnimationResource(animatable);
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        if (this.gunTypeEnum == GunTypeEnum.SILVER_HELL)
            return HWGMod.modResource("geo/item/hellhorse_revolver/hellhorse_revolver.geo.json");
        if (this.gunTypeEnum == GunTypeEnum.SILVER_PISTOL) return HWGMod.modResource("geo/item/pistol/pistol.geo.json");
        return super.getModelResource(animatable);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (this.gunTypeEnum == GunTypeEnum.SIL_PISTOL) return HWGMod.modResource("textures/item/pistol/pistol.png");
        if (this.gunTypeEnum == GunTypeEnum.MEANIE)
            return HWGMod.modResource("textures/item/meanie_gun_1/meanie_gun.png");
        return super.getTextureResource(animatable);
    }
}
