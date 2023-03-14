package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.MercModel;
import mod.azure.hwg.entity.MercEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class MercRender extends GeoEntityRenderer<MercEntity> {

	public MercRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MercModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					MercEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
				poseStack.mulPose(Axis.YP.rotationDegrees(0f));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0f));
				poseStack.translate(0.4D, 0.3D, 0.7D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
		this.shadowRadius = 0.7F;
	}

}