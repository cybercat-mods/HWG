package mod.azure.hwg.client.models;

import mod.azure.azurelib.common.api.client.model.DefaultedItemGeoModel;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.common.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.hwg.CommonMod;
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
                return CommonMod.modResource("animations/item/pistol/pistol.animation.json");
            }
            case MEANIE -> {
                return CommonMod.modResource("animations/item/meanie/meanie.animation.json");
            }
            case HELLHORSE, SILVER_HELL -> {
                return CommonMod.modResource("animations/item/hellhorse_revolver/hellhorse_revolver.animation.json");
            }
        }
        return super.getAnimationResource(animatable);
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        if (this.gunTypeEnum == GunTypeEnum.SILVER_HELL)
            return CommonMod.modResource("geo/item/hellhorse_revolver/hellhorse_revolver.geo.json");
        if (this.gunTypeEnum == GunTypeEnum.SILVER_PISTOL) return CommonMod.modResource("geo/item/pistol/pistol.geo.json");
        return super.getModelResource(animatable);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (this.gunTypeEnum == GunTypeEnum.SIL_PISTOL) return CommonMod.modResource("textures/item/pistol/pistol.png");
        if (this.gunTypeEnum == GunTypeEnum.MEANIE)
            return CommonMod.modResource("textures/item/meanie_gun_1/meanie_gun.png");
        return super.getTextureResource(animatable);
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        switch (animationState.getData(DataTickets.ITEM_RENDER_PERSPECTIVE)) {
            case GUI, GROUND, HEAD, NONE, FIXED -> animationState.getController().stop();
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
