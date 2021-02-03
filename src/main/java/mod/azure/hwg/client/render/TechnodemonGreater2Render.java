package mod.azure.hwg.client.render;

import mod.azure.hwg.client.models.TechnodemonGreater2Model;
import mod.azure.hwg.entity.TechnodemonGreater2Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class TechnodemonGreater2Render extends GeoEntityRenderer<TechnodemonGreater2Entity> {

	public TechnodemonGreater2Render(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new TechnodemonGreater2Model());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderLayer getRenderType(TechnodemonGreater2Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}