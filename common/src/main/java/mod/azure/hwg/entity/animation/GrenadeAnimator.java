package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;

public class GrenadeAnimator extends AzEntityAnimator<GrenadeEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/grenade.animation.json"
    );

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
