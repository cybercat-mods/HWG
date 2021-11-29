package mod.azure.hwg.client.render.projectiles.flare;

import mod.azure.hwg.entity.projectiles.flare.YellowFlareEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class YellowFlareRender extends EntityRenderer<YellowFlareEntity> {

	public YellowFlareRender(EntityRendererFactory.Context dispatcher) {
		super(dispatcher);
	}

	public void render(YellowFlareEntity fireworkRocketEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

		matrixStack.pop();
		super.render(fireworkRocketEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@SuppressWarnings("deprecation")
	public Identifier getTexture(YellowFlareEntity fireworkRocketEntity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}

	@Override
	protected int getBlockLight(YellowFlareEntity entity, BlockPos blockPos) {
		return 15;
	}
}