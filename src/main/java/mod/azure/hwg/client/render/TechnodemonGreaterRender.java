package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.TechnodemonGreaterModel;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.item.weapons.Minigun;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class TechnodemonGreaterRender extends GeoEntityRenderer<TechnodemonGreaterEntity> {

	public TechnodemonGreaterRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TechnodemonGreaterModel());
		this.shadowRadius = 0.7F;
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					TechnodemonGreaterEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(animatable.getMainHandItem().getItem() instanceof Minigun ? -15 : -90));
				poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getMainHandItem().getItem() instanceof Minigun ? -15 : 0));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0f));
				poseStack.translate(animatable.getMainHandItem().getItem() instanceof Minigun ? 0.50D : 0.42D,
						animatable.getMainHandItem().getItem() instanceof Minigun ? 1.79D : 0.12D,
								animatable.getMainHandItem().getItem() instanceof Minigun ? -0.3D : 1.5D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
	}

}