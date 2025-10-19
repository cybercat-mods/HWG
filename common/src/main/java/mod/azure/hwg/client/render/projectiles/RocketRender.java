package mod.azure.hwg.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.common.util.client.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.projectiles.RocketEntity;

public class RocketRender extends AzEntityRenderer<RocketEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/rocket.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/projectiles/rocket.png");

    public RocketRender(EntityRendererProvider.Context context) {
        super(AzEntityRendererConfig.<RocketEntity>builder(MODEL, TEXTURE).build(), context);
    }

    @Override
    public void render(
        @NotNull RocketEntity entity,
        float entityYaw,
        float partialTick,
        @NotNull PoseStack poseStack,
        @NotNull MultiBufferSource bufferSource,
        int packedLight
    ) {
        RenderUtils.faceRotation(poseStack, entity, partialTick);
        poseStack.scale(
            entity.tickCount > 2 ? 1.0F : 0.0F,
            entity.tickCount > 2 ? 0.5F : 0.0F,
            entity.tickCount > 2 ? 1.0F : 0.0F
        );
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
