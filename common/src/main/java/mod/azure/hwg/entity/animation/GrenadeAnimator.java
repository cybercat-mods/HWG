package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzEntityAnimator;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GrenadeAnimator extends AzEntityAnimator<GrenadeEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
            "animations/grenade.animation.json");

    @Override
    public void registerControllers(AzAnimationControllerContainer<GrenadeEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .setTransitionLength(5)
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(GrenadeEntity animatable) {
        return ANIMATIONS;
    }
}
