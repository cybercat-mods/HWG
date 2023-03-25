package mod.azure.hwg.client.gui;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.network.C2SMessageSelectCraft;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GunTableScreen extends AbstractContainerScreen<GunTableScreenHandler> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(HWGMod.MODID, "textures/gui/gun_table_gui.png");

	private int selectedIndex;
	private final GunTableScreen.WidgetButtonPage[] offers = new GunTableScreen.WidgetButtonPage[7];
	private int indexStartOffset;
	private boolean scrolling;

	public GunTableScreen(GunTableScreenHandler handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
		this.imageWidth = 300;
		this.inventoryLabelX = 107;
	}

	private void syncRecipeIndex() {
		this.menu.setRecipeIndex(this.selectedIndex);
		this.menu.switchTo(this.selectedIndex);
		C2SMessageSelectCraft.send(selectedIndex);
	}

	protected void init() {
		super.init();
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		int k = j + 18;

		for (int l = 0; l < 7; ++l) {
			this.offers[l] = this.addRenderableWidget(new WidgetButtonPage(i, k, l, (button) -> {
				if (button instanceof WidgetButtonPage) {
					this.selectedIndex = ((WidgetButtonPage) button).getIndex() + this.indexStartOffset;
					this.syncRecipeIndex();
				}
			}));
			k += 20;
		}
	}

	protected void renderLabels(PoseStack matrices, int mouseX, int mouseY) {
		this.font.draw(matrices, this.title, (float) (49 + this.imageWidth / 2 - this.font.width(this.title) / 2), 6.0F, 4210752);
	}

	protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = ((this.width - this.imageWidth) / 2) - 5;
		int j = (this.height - this.imageHeight) / 2;
		blit(matrices, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);

	}

	private void renderScrollbar(PoseStack matrices, int x, int y, List<GunTableRecipe> tradeOffers) {
		var i = tradeOffers.size() + 1 - 7;
		if (i > 1) {
			var j = 139 - (27 + (i - 1) * 139 / i);
			var k = 1 + j / i + 139 / i;
			var m = Math.min(113, this.indexStartOffset * k);
			if (this.indexStartOffset == i - 1)
				m = 113;

			blit(matrices, x + 113, y + 18 + m, 0, 0.0F, 199.0F, 6, 27, 512, 256);
		} else {
			blit(matrices, x + 113, y + 18, 0, 6.0F, 199.0F, 6, 27, 512, 256);
		}

	}

	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		var tradeOfferList = this.menu.getRecipes();
		if (!tradeOfferList.isEmpty()) {
			var i = (this.width - this.imageWidth) / 2;
			var j = (this.height - this.imageHeight) / 2;
			var yPos = j + 17;
			var xPos = i + 3;
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, TEXTURE);
			this.renderScrollbar(matrices, i, j, tradeOfferList);
			var m = 0;

			while (true) {
				for (GunTableRecipe gunTableRecipe : tradeOfferList)
					if (this.canScroll(tradeOfferList.size()) && (m < this.indexStartOffset || m >= 7 + this.indexStartOffset))
						++m;
					else {
						var output = gunTableRecipe.output;
						var n = yPos + 2;
						this.renderIngredients(matrices, gunTableRecipe, xPos, n);

						this.renderArrow(matrices, gunTableRecipe, i + 22, n);
						this.itemRenderer.renderAndDecorateFakeItem(matrices, output, i + 24 + 68, n);
						this.itemRenderer.renderGuiItemDecorations(matrices, this.font, output, i + 24 + 68, n);
						yPos += 20;
						++m;
					}

				for (WidgetButtonPage widgetButtonPage : this.offers) {
					if (widgetButtonPage.isHoveredOrFocused())
						widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);

					widgetButtonPage.visible = widgetButtonPage.index < this.menu.getRecipes().size();
				}

				RenderSystem.enableDepthTest();
				break;
			}
		}

		this.renderTooltip(matrices, mouseX, mouseY);
	}

	private void renderArrow(PoseStack matrices, GunTableRecipe tradeOffer, int x, int y) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);
		blit(matrices, x + 5 + 35 + 20, y + 3, 0, 15.0F, 171.0F, 10, 9, 512, 256);

	}

	private void renderIngredients(PoseStack matrices, GunTableRecipe gunTableRecipe, int x, int y) {
		for (var i = 0; i < 5; i++) {
			var displayStacks = gunTableRecipe.getIngredientForSlot(i).getItems();
			if (displayStacks.length > 0) {
				var stack = new ItemStack(displayStacks[0].getItem(), gunTableRecipe.countRequired(i));
				if (!stack.isEmpty()) {
					this.itemRenderer.renderAndDecorateFakeItem(matrices, stack, x, y);
					this.itemRenderer.renderGuiItemDecorations(matrices, this.font, stack, x, y);
					x += 16;
				}
			}
		}
	}

	private boolean canScroll(int listSize) {
		return listSize > 7;
	}

	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		var i = this.menu.getRecipes().size();
		if (this.canScroll(i)) {
			var j = i - 7;
			this.indexStartOffset = (int) ((double) this.indexStartOffset - amount);
			this.indexStartOffset = Mth.clamp(this.indexStartOffset, 0, j);
		}
		return true;
	}

	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		var i = this.menu.getRecipes().size();
		if (this.scrolling) {
			var j = this.topPos + 18;
			var k = j + 139;
			var l = i - 7;
			var f = ((float) mouseY - (float) j - 13.5F) / ((float) (k - j) - 27.0F);
			f = f * (float) l + 0.5F;
			this.indexStartOffset = Mth.clamp((int) f, 0, l);
			return true;
		} else
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.scrolling = false;
		var i = (this.width - this.imageWidth) / 2;
		var j = (this.height - this.imageHeight) / 2;
		if (this.canScroll(this.menu.getRecipes().size()) && mouseX > (double) (i + 94) && mouseX < (double) (i + 94 + 6) && mouseY > (double) (j + 18) && mouseY <= (double) (j + 18 + 139 + 1))
			this.scrolling = true;

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Environment(EnvType.CLIENT)
	class WidgetButtonPage extends Button {

		final int index;

		public WidgetButtonPage(int x, int y, int index, Button.OnPress onPress) {
			super(x, y, 112, 20, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
			this.index = index;
			this.visible = false;
		}

		public int getIndex() {
			return this.index;
		}

		public void renderToolTip(PoseStack matrices, int mouseX, int mouseY) {
			if (this.isHovered && menu.getRecipes().size() > this.index + indexStartOffset) {
				ItemStack stack;
				if (mouseX < this.getX() + 20) {
					stack = menu.getRecipes().get(this.index + indexStartOffset).output;
					renderTooltip(matrices, mouseX, mouseY);
				} else if (mouseX < this.getX() + 50 && mouseX > this.getX() + 30) {
					stack = menu.getRecipes().get(this.index + indexStartOffset).output;
					if (!stack.isEmpty())
						renderTooltip(matrices, mouseX, mouseY);
				} else if (mouseX > this.getX() + 65) {
					stack = menu.getRecipes().get(this.index + indexStartOffset).output;
					renderTooltip(matrices, mouseX, mouseY);
				}
			}
		}
	}
}