package mod.azure.hwg.client.render.projectiles;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.azure.hwg.entity.projectiles.FireballEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class FireballRender extends EntityRenderer<FireballEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/item/fire_charge.png");
	private static final RenderType LAYER = RenderType.entityCutoutNoCull(TEXTURE);

	public FireballRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	protected int getBlockLightLevel(FireballEntity FireballEntity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public void render(FireballEntity FireballEntity, float f, float g, PoseStack matrixStack,
			MultiBufferSource vertexConsumerProvider, int i) {
		matrixStack.pushPose();
		matrixStack.scale(0.5F, 0.5F, 0.5F);
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		PoseStack.Pose entry = matrixStack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
		matrixStack.popPose();
		super.render(FireballEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix,
			int light, float x, int y, int textureU, int textureV) {
		vertexConsumer.vertex(modelMatrix, x - 0.5F, (float) y - 0.25F, 0.0F).color(255, 255, 255, 255)
				.uv((float) textureU, (float) textureV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
				.normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(FireballEntity FireballEntity) {
		return TEXTURE;
	}
}