package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.models.projectiles.GStunSModel;
import mod.azure.hwg.entity.projectiles.launcher.StunGEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderer.geo.GeoProjectilesRenderer;

public class GStunSRender extends GeoProjectilesRenderer<StunGEntity> {

	public GStunSRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new GStunSModel());
	}

	protected int getBlockLight(StunGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(StunGEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}