package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.GeoProjectilesRenderer;
import mod.azure.hwg.client.models.projectiles.GEMPSModel;
import mod.azure.hwg.entity.projectiles.launcher.EMPGEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GEMPSRender extends GeoProjectilesRenderer<EMPGEntity> {

	public GEMPSRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new GEMPSModel());
	}

	protected int getBlockLight(EMPGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(EMPGEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}