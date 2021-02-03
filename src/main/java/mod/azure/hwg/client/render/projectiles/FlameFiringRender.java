package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.GeoProjectilesRenderer;
import mod.azure.hwg.client.models.projectiles.FlameFiringModel;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class FlameFiringRender extends GeoProjectilesRenderer<FlameFiring> {

	public FlameFiringRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new FlameFiringModel());
	}

	protected int getBlockLight(FlameFiring entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(FlameFiring animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}