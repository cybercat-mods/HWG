package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzEntityAnimator;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TechnodemonGreaterAnimator extends AzEntityAnimator<TechnodemonGreaterEntity> {

    private static final ResourceLocation ANIMATIONS_1 = CommonMod.modResource(
            "animations/entity/technodemon_greater_1.animation.json");

    private static final ResourceLocation ANIMATIONS_2 = CommonMod.modResource(
            "animations/entity/technodemon_greater_2.animation.json");

    @Override
    public void registerControllers(AzAnimationControllerContainer<TechnodemonGreaterEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .setTransitionLength(5)
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(TechnodemonGreaterEntity animatable) {
        ResourceLocation animation;
        animation = switch(animatable.getVariant()) {
            case 1 -> ANIMATIONS_1;
            case 2 -> ANIMATIONS_2;
            default -> ANIMATIONS_1;
        };
        return animation;
    }
}
