package mod.azure.hwg.client.render;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.SpyModel;
import mod.azure.hwg.entity.SpyEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class SpyRender extends GeoEntityRenderer<SpyEntity> {

	public SpyRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SpyModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					SpyEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-75f));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0f));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f));
				poseStack.translate(0.4D, 0.3D, 0.7D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
		this.shadowRadius = 0.7F;
	}

}