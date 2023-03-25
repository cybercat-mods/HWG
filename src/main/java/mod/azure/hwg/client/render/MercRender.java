package mod.azure.hwg.client.render;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.MercModel;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.item.weapons.ShotgunItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MercRender extends GeoEntityRenderer<MercEntity> {

	public MercRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MercModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, MercEntity animatable) {
				return switch (bone.getName()) {
				case "rArmRuff" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
				default -> null;
				};
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, MercEntity animatable) {
				return switch (bone.getName()) {
				default -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, MercEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-90));
				poseStack.mulPose(Axis.YP.rotationDegrees(0));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0));
				if (animatable.getMainHandItem().getItem() instanceof ShotgunItem)
					poseStack.translate(0.0D, 0.15D, -0.1D);
				else
					poseStack.translate(0.0D, 0.1D, -0.1D);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
		this.shadowRadius = 0.7F;
	}

}