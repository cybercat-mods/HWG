package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.entity.projectiles.FireballEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class FireballRender extends EntityRenderer<FireballEntity> {
	private static final Identifier TEXTURE = new Identifier("textures/item/fire_charge.png");
	private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

	public FireballRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
	}

	protected int getBlockLight(FireballEntity FireballEntity, BlockPos blockPos) {
		return 15;
	}

	public void render(FireballEntity FireballEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.scale(0.5F, 0.5F, 0.5F);
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
		MatrixStack.Entry entry = matrixStack.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
		produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
		matrixStack.pop();
		super.render(FireballEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix,
			int light, float x, int y, int textureU, int textureV) {
		vertexConsumer.vertex(modelMatrix, x - 0.5F, (float) y - 0.25F, 0.0F).color(255, 255, 255, 255)
				.texture((float) textureU, (float) textureV).overlay(OverlayTexture.DEFAULT_UV).light(light)
				.normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
	}

	public Identifier getTexture(FireballEntity FireballEntity) {
		return TEXTURE;
	}
}