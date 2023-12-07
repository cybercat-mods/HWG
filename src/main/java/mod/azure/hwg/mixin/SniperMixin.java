package mod.azure.hwg.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import mod.azure.azurelib.Keybindings;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class SniperMixin {

    private static final ResourceLocation SNIPER = HWGMod.modResource("textures/gui/pumpkinblur.png");

    @Shadow
    private final Minecraft minecraft;
    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;
    private boolean scoped = true;

    protected SniperMixin(Minecraft client) {
        this.minecraft = client;
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(CallbackInfo info) {
        var itemStack = this.minecraft.player.getInventory().getSelected();
        if (this.minecraft.options.getCameraType().isFirstPerson() && itemStack.is(HWGItems.SNIPER)) {
            if (Keybindings.SCOPE.isDown()) {
                if (this.scoped) this.scoped = false;
                this.renderSniperOverlay();
            } else if (!this.scoped) this.scoped = true;
        }
    }

    private void renderSniperOverlay() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SNIPER);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(0.0D, this.screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferBuilder.vertex(this.screenWidth, this.screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferBuilder.vertex(this.screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tessellator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
