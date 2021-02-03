package mod.azure.hwg.client.render;

import mod.azure.hwg.client.models.TechnodemonLesser2Model;
import mod.azure.hwg.entity.Technodemon2Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class TechnodemonLesser2Render extends GeoEntityRenderer<Technodemon2Entity> {

	public TechnodemonLesser2Render(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new TechnodemonLesser2Model());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderLayer getRenderType(Technodemon2Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}