package mod.azure.hwg.client.render;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.MercModel;
import mod.azure.hwg.entity.MercEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class MercRender extends GeoEntityRenderer<MercEntity> {

	public MercRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new MercModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					MercEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0f));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f));
				poseStack.translate(0.4D, 0.3D, 0.7D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
		this.shadowRadius = 0.7F;
	}

}