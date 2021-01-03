package mod.azure.hwg.client.render;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.hwg.client.models.PistolModel;
import mod.azure.hwg.item.PistolItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class PistolRender extends GeoItemRenderer<PistolItem> {
	public PistolRender() {
		super(new PistolModel());
	}

	@Override
	public void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStackIn,
			VertexConsumerProvider bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (mode == ModelTransformation.Mode.GUI) {
			RenderSystem.pushMatrix();
			VertexConsumerProvider.Immediate irendertypebuffer$impl = MinecraftClient.getInstance().getBufferBuilders()
					.getEntityVertexConsumers();
			DiffuseLighting.disableGuiDepthLighting();
			super.render(itemStack, mode, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			irendertypebuffer$impl.draw();
			RenderSystem.enableDepthTest();
			DiffuseLighting.enableGuiDepthLighting();
			RenderSystem.popMatrix();
		} else {
			super.render(itemStack, mode, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		}
	}
}