package mod.azure.hwg.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TechnodemonGreaterModel extends GeoModel<TechnodemonGreaterEntity> {

    public TechnodemonGreaterModel() {
    }

    @Override
    public ResourceLocation getModelResource(TechnodemonGreaterEntity object) {
        return new ResourceLocation(HWGMod.MODID, "geo/technodemon_greater_" + object.getVariant() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TechnodemonGreaterEntity object) {
        return new ResourceLocation(HWGMod.MODID, "textures/entity/technodemon_greater_" + object.getVariant() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(TechnodemonGreaterEntity object) {
        return new ResourceLocation(HWGMod.MODID, "animations/technodemon_greater_" + object.getVariant() + ".animation.json");
    }

    @Override
    public RenderType getRenderType(TechnodemonGreaterEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(TechnodemonGreaterEntity animatable, long instanceId, AnimationState<TechnodemonGreaterEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var body = getAnimationProcessor().getBone("chest");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX((entityData.headPitch() - 5) * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        if (body != null)
            body.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}