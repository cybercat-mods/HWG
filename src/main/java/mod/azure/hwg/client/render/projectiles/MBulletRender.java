package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.GeoProjectilesRenderer;
import mod.azure.hwg.client.models.projectiles.MBulletModel;
import mod.azure.hwg.entity.projectiles.MBulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class MBulletRender extends GeoProjectilesRenderer<MBulletEntity> {

	public MBulletRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new MBulletModel());
	}

	protected int getBlockLight(MBulletEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(MBulletEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(MBulletEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(0.5F, 0.5F, 0.5F);
	}

}