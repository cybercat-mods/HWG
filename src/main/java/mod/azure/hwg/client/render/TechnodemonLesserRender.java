package mod.azure.hwg.client.render;

import mod.azure.hwg.client.models.TechnodemonLesserModel;
import mod.azure.hwg.entity.TechnodemonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class TechnodemonLesserRender extends GeoEntityRenderer<TechnodemonEntity> {

	public TechnodemonLesserRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new TechnodemonLesserModel());
		this.shadowRadius = 0.7F;
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					TechnodemonEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0f));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f));
				poseStack.translate(0.34D, 0.1D, 1.0D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
	}

}