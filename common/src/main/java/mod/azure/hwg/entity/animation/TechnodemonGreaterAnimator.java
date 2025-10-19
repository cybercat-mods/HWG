package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;

public class TechnodemonGreaterAnimator extends AzEntityAnimator<TechnodemonGreaterEntity> {

    private static final ResourceLocation ANIMATIONS_1 = CommonMod.modResource(
        "animations/entity/technodemon_greater_1.animation.json"
    );

    private static final ResourceLocation ANIMATIONS_2 = CommonMod.modResource(
        "animations/entity/technodemon_greater_2.animation.json"
    );

    @Override
    public void registerControllers(
        AzAnimationControllerContainer<TechnodemonGreaterEntity> animationControllerContainer
    ) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .setTransitionLength(5)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(TechnodemonGreaterEntity animatable) {
        ResourceLocation animation;
        animation = switch (animatable.getVariant()) {
            case 1 -> ANIMATIONS_1;
            case 2 -> ANIMATIONS_2;
            default -> ANIMATIONS_1;
        };
        return animation;
    }
}
