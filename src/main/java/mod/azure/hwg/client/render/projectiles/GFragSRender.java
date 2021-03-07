package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.GeoProjectilesRenderer;
import mod.azure.hwg.client.models.projectiles.GFragSModel;
import mod.azure.hwg.entity.projectiles.launcher.FragGEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GFragSRender extends GeoProjectilesRenderer<FragGEntity> {

	public GFragSRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new GFragSModel());
	}

	protected int getBlockLight(FragGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(FragGEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}