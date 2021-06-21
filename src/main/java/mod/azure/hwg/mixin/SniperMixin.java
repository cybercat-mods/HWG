package mod.azure.hwg.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.hwg.item.weapons.SniperItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Mixin(InGameHud.class)
public abstract class SniperMixin extends DrawableHelper {

	private static final Identifier BLUR = new Identifier("hwg", "textures/gui/pumpkinblur.png");
	@Shadow
	private final MinecraftClient client;
	@Shadow
	private int scaledWidth;
	@Shadow
	private int scaledHeight;

	public SniperMixin(MinecraftClient client) {
		this.client = client;
	}

	@Inject(at = @At("TAIL"), method = "render")
	private void render(CallbackInfo info) {
		ItemStack itemStack = this.client.player.getInventory().getMainHandStack();
		if (this.client.options.getPerspective().isFirstPerson() && itemStack.getItem() instanceof SniperItem) {
			if (this.client.options.keySneak.isPressed()) {
				this.client.options.fov = 10;
				this.renderSniperOverlay();
			} else {
				this.client.options.fov = 70;
			}
		}
	}

	private void renderSniperOverlay() {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BLUR);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(0.0D, (double) this.scaledHeight, -90.0D).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex((double) this.scaledWidth, (double) this.scaledHeight, -90.0D).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex((double) this.scaledWidth, 0.0D, -90.0D).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
		tessellator.draw();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
