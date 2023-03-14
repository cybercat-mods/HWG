package mod.azure.hwg.client.render;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.TechnodemonLesserModel;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class TechnodemonLesserRender extends GeoEntityRenderer<TechnodemonEntity> {

	public TechnodemonLesserRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TechnodemonLesserModel());
		this.shadowRadius = 0.7F;
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, TechnodemonEntity animatable) {
				return switch (bone.getName()) {
				case "rightHand" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
				default -> null;
				};
			}

			@Override
			protected ItemTransforms.TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack,
					TechnodemonEntity animatable) {
				return switch (bone.getName()) {
				default -> ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					TechnodemonEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-90));
				poseStack.mulPose(Axis.YP
						.rotationDegrees(animatable.getMainHandItem().getItem() instanceof BrimstoneItem ? 0 : 8));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0));
				if (animatable.getMainHandItem().getItem() instanceof BrimstoneItem)
					poseStack.translate(0.0D, 0.15D, -0.7D);
				else
					poseStack.translate(0.2D, 0.15D, -0.65D);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
						packedOverlay);
			}
		});
	}

}