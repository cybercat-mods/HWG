package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.TechnodemonEntity;

public class TechnodemonAnimator extends AzEntityAnimator<TechnodemonEntity> {

    private static final ResourceLocation ANIMATIONS_1 = CommonMod.modResource(
        "animations/entity/technodemon_lesser_1.animation.json"
    );

    private static final ResourceLocation ANIMATIONS_2 = CommonMod.modResource(
        "animations/entity/technodemon_lesser_2.animation.json"
    );

    private static final ResourceLocation ANIMATIONS_3 = CommonMod.modResource(
        "animations/entity/technodemon_lesser_3.animation.json"
    );

    private static final ResourceLocation ANIMATIONS_4 = CommonMod.modResource(
        "animations/entity/technodemon_lesser_4.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<TechnodemonEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .setTransitionLength(5)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(TechnodemonEntity animatable) {
        ResourceLocation animation;
        animation = switch (animatable.getVariant()) {
            case 1 -> ANIMATIONS_1;
            case 2 -> ANIMATIONS_2;
            case 3 -> ANIMATIONS_3;
            case 4 -> ANIMATIONS_4;
            default -> ANIMATIONS_1;
        };
        return animation;
    }
}
