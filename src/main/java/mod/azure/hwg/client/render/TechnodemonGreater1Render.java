package mod.azure.hwg.client.render;

import mod.azure.hwg.client.models.TechnodemonGreater1Model;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class TechnodemonGreater1Render extends GeoEntityRenderer<TechnodemonGreaterEntity> {

	public TechnodemonGreater1Render(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new TechnodemonGreater1Model());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderLayer getRenderType(TechnodemonGreaterEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}