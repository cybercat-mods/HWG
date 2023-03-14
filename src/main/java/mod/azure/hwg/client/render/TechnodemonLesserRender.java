package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.TechnodemonLesserModel;
import mod.azure.hwg.entity.TechnodemonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class TechnodemonLesserRender extends GeoEntityRenderer<TechnodemonEntity> {

	public TechnodemonLesserRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TechnodemonLesserModel());
		this.shadowRadius = 0.7F;
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					TechnodemonEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
				poseStack.mulPose(Axis.YP.rotationDegrees(0f));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0f));
				poseStack.translate(0.34D, 0.1D, 1.0D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
	}

}