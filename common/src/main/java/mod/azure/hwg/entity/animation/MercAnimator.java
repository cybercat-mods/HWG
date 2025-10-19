package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.MercEntity;

public class MercAnimator extends AzEntityAnimator<MercEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/merc_illager.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<MercEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .setTransitionLength(5)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(MercEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(MercEntity animatable, float partialTicks) {
        super.setCustomAnimations(animatable, partialTicks);

        var boneCache = this.context().boneCache();
        var leftArm = boneCache.getBakedModel().getBone("BipedLeftArm");
        var rightArm = boneCache.getBakedModel().getBone("BipedRightArm");
        var leftLeg = boneCache.getBakedModel().getBone("BipedLeftLeg");
        var rightLeg = boneCache.getBakedModel().getBone("BipedRightLeg");
        if (leftArm.isPresent())
            leftArm.get()
                .setRotX(
                    Mth.cos(
                        animatable.walkAnimation.position(
                            partialTicks
                        ) * 0.6662F
                    ) * 2.0F * animatable.walkAnimation.speed() * 0.5F
                );
        if (rightArm.isPresent() && !animatable.isAggressive())
            rightArm.get()
                .setRotX(
                    Mth.cos(
                        animatable.walkAnimation.position(
                            partialTicks
                        ) * 0.6662F + 3.1415927F
                    ) * 2.0F * animatable.walkAnimation.speed() * 0.5F
                );
        if (leftLeg.isPresent())
            leftLeg.get()
                .setRotX(
                    Mth.cos(
                        animatable.walkAnimation.position(
                            partialTicks
                        ) * 0.6662F + 3.1415927F
                    ) * 1.4F * animatable.walkAnimation.speed() * 0.5F
                );
        if (rightLeg.isPresent())
            rightLeg.get()
                .setRotX(
                    Mth.cos(
                        animatable.walkAnimation.position(
                            partialTicks
                        ) * 0.6662F
                    ) * 1.4F * animatable.walkAnimation.speed() * 0.5F
                );
    }
}
