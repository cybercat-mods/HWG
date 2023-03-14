package mod.azure.hwg.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FuelTankRender extends EntityRenderer<FuelTankEntity> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(HWGMod.MODID, "textures/blocks/barrel_explode.png");

	public FuelTankRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.5F;
	}

	public void render(FuelTankEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.0D, 0.5D, 0.0D);

		matrixStackIn.mulPose(Axis.YP.rotationDegrees(-90.0F));
		matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
		matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F));
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(FuelTankEntity entity) {
		return TEXTURE;
	}
}