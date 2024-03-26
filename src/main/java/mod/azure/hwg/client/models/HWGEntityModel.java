package mod.azure.hwg.client.models;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.*;
import mod.azure.hwg.entity.enums.EntityEnum;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HWGEntityModel<T extends HWGEntity & GeoEntity> extends GeoModel<T> {
    private final EntityEnum entityType;
    private static final String lesserDemon = "technodemon_lesser_";
    private static final String greaterDemon = "technodemon_greater_";

    public HWGEntityModel(EntityEnum entityType) {
        this.entityType = entityType;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        String model = null;
        if (this.entityType == EntityEnum.ILLEAGER) model = "merc_illager";
        if (this.entityType == EntityEnum.DEMON) {
            if (animatable instanceof TechnodemonEntity technodemonEntity)
                model = lesserDemon + technodemonEntity.getVariant();
            if (animatable instanceof TechnodemonGreaterEntity greaterEntity)
                model = greaterDemon + greaterEntity.getVariant();
        }
        return HWGMod.modResource("geo/entity/" + model + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        String texture = null;
        if (this.entityType == EntityEnum.ILLEAGER) {
            if (animatable instanceof MercEntity mercEntity) texture = "merc_" + mercEntity.getVariant();
            if (animatable instanceof SpyEntity spyEntity) texture = "spy_0" + spyEntity.getVariant();
        }
        if (this.entityType == EntityEnum.DEMON) {
            if (animatable instanceof TechnodemonEntity technodemonEntity)
                texture = lesserDemon + technodemonEntity.getVariant();
            if (animatable instanceof TechnodemonGreaterEntity greaterEntity)
                texture = greaterDemon + greaterEntity.getVariant();
        }
        return HWGMod.modResource("textures/entity/" + texture + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        String animation = null;
        if (this.entityType == EntityEnum.ILLEAGER) animation = "merc_illager";
        if (this.entityType == EntityEnum.DEMON) {
            if (animatable instanceof TechnodemonEntity technodemonEntity)
                animation = lesserDemon + technodemonEntity.getVariant();
            if (animatable instanceof TechnodemonGreaterEntity greaterEntity)
                animation = greaterDemon + greaterEntity.getVariant();
        }
        return HWGMod.modResource("animations/entity/" + animation + ".animation.json");
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        if (this.entityType == EntityEnum.ILLEAGER) {
            var body = getAnimationProcessor().getBone("body");
            var leftArm = getAnimationProcessor().getBone("BipedLeftArm");
            var rightArm = getAnimationProcessor().getBone("BipedRightArm");
            var leftLeg = getAnimationProcessor().getBone("BipedLeftLeg");
            var rightLeg = getAnimationProcessor().getBone("BipedRightLeg");
            var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            if (head != null) {
                head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
                head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            }
            if (body != null) body.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            if (leftArm != null)
                leftArm.setRotX(Mth.cos(animatable.walkAnimation.position(
                        animationState.getPartialTick()) * 0.6662F) * 2.0F * animatable.walkAnimation.speed() * 0.5F);
            if (rightArm != null && !animatable.isAggressive())
                rightArm.setRotX(Mth.cos(animatable.walkAnimation.position(
                        animationState.getPartialTick()) * 0.6662F + 3.1415927F) * 2.0F * animatable.walkAnimation.speed() * 0.5F);
            if (leftLeg != null)
                leftLeg.setRotX(Mth.cos(animatable.walkAnimation.position(
                        animationState.getPartialTick()) * 0.6662F + 3.1415927F) * 1.4F * animatable.walkAnimation.speed() * 0.5F);
            if (rightLeg != null)
                rightLeg.setRotX(Mth.cos(animatable.walkAnimation.position(
                        animationState.getPartialTick()) * 0.6662F) * 1.4F * animatable.walkAnimation.speed() * 0.5F);
        }
        if (this.entityType == EntityEnum.DEMON) {
            var body = getAnimationProcessor().getBone("chest");
            var body2 = getAnimationProcessor().getBone("bipedBody");
            var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            if (head != null) {
                head.setRotX((entityData.headPitch() - 5) * Mth.DEG_TO_RAD);
                head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            }
            if (body != null) body.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            if (body2 != null) body2.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
