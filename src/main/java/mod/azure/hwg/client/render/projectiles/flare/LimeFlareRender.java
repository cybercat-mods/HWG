package mod.azure.hwg.client.render.projectiles.flare;

import mod.azure.hwg.entity.projectiles.flare.LimeFlareEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class LimeFlareRender extends EntityRenderer<LimeFlareEntity> {

	public LimeFlareRender(EntityRendererFactory.Context dispatcher) {
		super(dispatcher);
	}

	public void render(LimeFlareEntity fireworkRocketEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

		matrixStack.pop();
		super.render(fireworkRocketEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@SuppressWarnings("deprecation")
	public Identifier getTexture(LimeFlareEntity fireworkRocketEntity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}

	@Override
	protected int getBlockLight(LimeFlareEntity entity, BlockPos blockPos) {
		return 15;
	}
}