package mod.azure.hwg.client.render.projectiles;

import mod.azure.hwg.client.GrenadeProjectilesRenderer;
import mod.azure.hwg.client.models.projectiles.GStunModel;
import mod.azure.hwg.entity.projectiles.StunGrenadeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GStunRender extends GrenadeProjectilesRenderer<StunGrenadeEntity> {

	public GStunRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new GStunModel());
	}

	protected int getBlockLight(StunGrenadeEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(StunGrenadeEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}