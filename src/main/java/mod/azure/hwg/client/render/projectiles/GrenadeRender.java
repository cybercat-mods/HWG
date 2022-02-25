package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.models.projectiles.GrenadeModel;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class GrenadeRender extends GeoProjectilesRenderer<GrenadeEntity> {

	public GrenadeRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new GrenadeModel());
	}

	protected int getBlockLight(GrenadeEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(GrenadeEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}