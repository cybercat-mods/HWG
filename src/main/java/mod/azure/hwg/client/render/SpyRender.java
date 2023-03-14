package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.SpyModel;
import mod.azure.hwg.entity.SpyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class SpyRender extends GeoEntityRenderer<SpyEntity> {

	public SpyRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new SpyModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					SpyEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-75f));
				poseStack.mulPose(Axis.YP.rotationDegrees(0f));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0f));
				poseStack.translate(0.4D, 0.3D, 0.7D);
				poseStack.scale(1.0f, 1.0f, 1.0f);
			};
		});
		this.shadowRadius = 0.7F;
	}

}