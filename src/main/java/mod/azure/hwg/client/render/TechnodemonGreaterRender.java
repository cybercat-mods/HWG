package mod.azure.hwg.client.render;

import mod.azure.hwg.client.models.TechnodemonGreaterModel;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.item.weapons.Minigun;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class TechnodemonGreaterRender extends GeoEntityRenderer<TechnodemonGreaterEntity> {

	public TechnodemonGreaterRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new TechnodemonGreaterModel());
		this.shadowRadius = 0.7F;
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					TechnodemonGreaterEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(animatable.getMainHandStack().getItem() instanceof Minigun ? -15 : -90));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(animatable.getMainHandStack().getItem() instanceof Minigun ? -15 : 0));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f));
				poseStack.translate(animatable.getMainHandStack().getItem() instanceof Minigun ? 0.50D : 0.42D,
						animatable.getMainHandStack().getItem() instanceof Minigun ? 1.79D : 0.12D,
								animatable.getMainHandStack().getItem() instanceof Minigun ? -0.3D : 1.5D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
	}

}