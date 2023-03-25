package mod.azure.hwg.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.hwg.entity.projectiles.BaseFlareEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BaseFlareRender extends EntityRenderer<BaseFlareEntity> {

	public BaseFlareRender(EntityRendererProvider.Context dispatcher) {
		super(dispatcher);
	}

	public void render(BaseFlareEntity fireworkRocketEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		matrixStack.pushPose();
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		matrixStack.scale(fireworkRocketEntity.tickCount > 2 ? 0.5F : 0.0F, fireworkRocketEntity.tickCount > 2 ? 0.5F : 0.0F, fireworkRocketEntity.tickCount > 2 ? 0.5F : 0.0F);

		matrixStack.popPose();
		super.render(fireworkRocketEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseFlareEntity fireworkRocketEntity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}

	@Override
	protected int getBlockLightLevel(BaseFlareEntity entity, BlockPos blockPos) {
		return 15;
	}
}