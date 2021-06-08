package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.models.projectiles.GSmokeModel;
import mod.azure.hwg.entity.projectiles.SmokeGrenadeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class GSmokeRender extends GeoProjectilesRenderer<SmokeGrenadeEntity> {

	public GSmokeRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new GSmokeModel());
	}

	protected int getBlockLight(SmokeGrenadeEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(SmokeGrenadeEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}