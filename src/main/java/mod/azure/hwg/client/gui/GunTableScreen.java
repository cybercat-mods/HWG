package mod.azure.hwg.client.gui;

import java.util.Iterator;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.hwg.HWGMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class GunTableScreen extends HandledScreen<GunTableScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(HWGMod.MODID, "textures/gui/gun_table_gui.png");
	//private static final Identifier TEXTURE = new Identifier("textures/gui/container/villager2.png");

	private int selectedIndex;
	private final GunTableScreen.WidgetButtonPage[] offers = new GunTableScreen.WidgetButtonPage[7];
	private int indexStartOffset;
	private boolean scrolling;

	public GunTableScreen(GunTableScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundWidth = 276;
		this.playerInventoryTitleX = 107;
	}

	private void syncRecipeIndex() {
		this.handler.setRecipeIndex(this.selectedIndex);
		this.handler.switchTo(this.selectedIndex);
		this.client.getNetworkHandler().sendPacket(new SelectMerchantTradeC2SPacket(this.selectedIndex));
	}

	protected void init() {
		super.init();
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		int k = j + 16 + 2;

		for (int l = 0; l < 7; ++l) {
			this.offers[l] = this
					.addButton(new WidgetButtonPage(i + 5, k, l, (button) -> {
						if (button instanceof WidgetButtonPage) {
							this.selectedIndex = ((WidgetButtonPage) button).getIndex()
									+ this.indexStartOffset;
							this.syncRecipeIndex();
						}

					}));
			k += 20;
		}

	}

	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		this.textRenderer.draw(matrices, this.title,
				(float) (49 + this.backgroundWidth / 2 - this.textRenderer.getWidth(this.title) / 2),
				6.0F, 4210752);
	}

	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		drawTexture(matrices, i, j, this.getZOffset(), 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256,
				512);

	}

	private void renderScrollbar(MatrixStack matrices, int x, int y, TradeOfferList tradeOffers) {
		int i = tradeOffers.size() + 1 - 7;
		if (i > 1) {
			int j = 139 - (27 + (i - 1) * 139 / i);
			int k = 1 + j / i + 139 / i;
			int m = Math.min(113, this.indexStartOffset * k);
			if (this.indexStartOffset == i - 1) {
				m = 113;
			}

			drawTexture(matrices, x + 94, y + 18 + m, this.getZOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
		} else {
			drawTexture(matrices, x + 94, y + 18, this.getZOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
		}

	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		TradeOfferList tradeOfferList = this.handler.getRecipes();
		if (!tradeOfferList.isEmpty()) {
			int i = (this.width - this.backgroundWidth) / 2;
			int j = (this.height - this.backgroundHeight) / 2;
			int k = j + 16 + 1;
			int l = i + 5 + 5;
			RenderSystem.pushMatrix();
			RenderSystem.enableRescaleNormal();
			this.client.getTextureManager().bindTexture(TEXTURE);
			this.renderScrollbar(matrices, i, j, tradeOfferList);
			int m = 0;
			Iterator<?> var11 = tradeOfferList.iterator();

			while (true) {
				TradeOffer tradeOffer;
				while (var11.hasNext()) {
					tradeOffer = (TradeOffer) var11.next();
					if (this.canScroll(tradeOfferList.size())
							&& (m < this.indexStartOffset || m >= 7 + this.indexStartOffset)) {
						++m;
					} else {
						ItemStack itemStack = tradeOffer.getOriginalFirstBuyItem();
						ItemStack itemStack2 = tradeOffer.getAdjustedFirstBuyItem();
						ItemStack itemStack3 = tradeOffer.getSecondBuyItem();
						ItemStack itemStack4 = tradeOffer.getMutableSellItem();
						this.itemRenderer.zOffset = 100.0F;
						int n = k + 2;
						this.renderFirstBuyItem(matrices, itemStack2, itemStack, l, n);
						if (!itemStack3.isEmpty()) {
							this.itemRenderer.renderInGui(itemStack3, i + 5 + 35, n);
							this.itemRenderer.renderGuiItemOverlay(this.textRenderer, itemStack3, i + 5 + 35, n);
						}

						this.renderArrow(matrices, tradeOffer, i, n);
						this.itemRenderer.renderInGui(itemStack4, i + 5 + 68, n);
						this.itemRenderer.renderGuiItemOverlay(this.textRenderer, itemStack4, i + 5 + 68, n);
						this.itemRenderer.zOffset = 0.0F;
						k += 20;
						++m;
					}
				}

				int o = this.selectedIndex;
				tradeOffer = tradeOfferList.get(o);

				GunTableScreen.WidgetButtonPage[] var19 = this.offers;
				int var20 = var19.length;

				for (int var21 = 0; var21 < var20; ++var21) {
					GunTableScreen.WidgetButtonPage widgetButtonPage = var19[var21];
					if (widgetButtonPage.isHovered()) {
						widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);
					}

					widgetButtonPage.visible = widgetButtonPage.index < this.handler
							.getRecipes().size();
				}

				RenderSystem.popMatrix();
				RenderSystem.enableDepthTest();
				break;
			}
		}

		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	private void renderArrow(MatrixStack matrices, TradeOffer tradeOffer, int x, int y) {
		RenderSystem.enableBlend();
		this.client.getTextureManager().bindTexture(TEXTURE);
		if (tradeOffer.isDisabled()) {
			drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
		} else {
			drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
		}

	}

	private void renderFirstBuyItem(MatrixStack matrices, ItemStack adjustedFirstBuyItem,
			ItemStack originalFirstBuyItem, int x, int y) {
		this.itemRenderer.renderInGui(adjustedFirstBuyItem, x, y);
		if (originalFirstBuyItem.getCount() == adjustedFirstBuyItem.getCount()) {
			this.itemRenderer.renderGuiItemOverlay(this.textRenderer, adjustedFirstBuyItem, x, y);
		} else {
			this.itemRenderer.renderGuiItemOverlay(this.textRenderer, originalFirstBuyItem, x, y,
					originalFirstBuyItem.getCount() == 1 ? "1" : null);
			this.itemRenderer.renderGuiItemOverlay(this.textRenderer, adjustedFirstBuyItem, x + 14, y,
					adjustedFirstBuyItem.getCount() == 1 ? "1" : null);
			this.client.getTextureManager().bindTexture(TEXTURE);
			this.setZOffset(this.getZOffset() + 300);
			drawTexture(matrices, x + 7, y + 12, this.getZOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
			this.setZOffset(this.getZOffset() - 300);
		}

	}

	private boolean canScroll(int listSize) {
		return listSize > 7;
	}

	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		int i = this.handler.getRecipes().size();
		if (this.canScroll(i)) {
			int j = i - 7;
			this.indexStartOffset = (int) ((double) this.indexStartOffset - amount);
			this.indexStartOffset = MathHelper.clamp(this.indexStartOffset, 0, j);
		}

		return true;
	}

	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		int i = this.handler.getRecipes().size();
		if (this.scrolling) {
			int j = this.y + 18;
			int k = j + 139;
			int l = i - 7;
			float f = ((float) mouseY - (float) j - 13.5F) / ((float) (k - j) - 27.0F);
			f = f * (float) l + 0.5F;
			this.indexStartOffset = MathHelper.clamp((int) f, 0, l);
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.scrolling = false;
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		if (this.canScroll(this.handler.getRecipes().size()) && mouseX > (double) (i + 94)
				&& mouseX < (double) (i + 94 + 6) && mouseY > (double) (j + 18)
				&& mouseY <= (double) (j + 18 + 139 + 1)) {
			this.scrolling = true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Environment(EnvType.CLIENT)
	class WidgetButtonPage extends ButtonWidget {
		final int index;

		public WidgetButtonPage(int x, int y, int index, ButtonWidget.PressAction onPress) {
			super(x, y, 89, 20, LiteralText.EMPTY, onPress);
			this.index = index;
			this.visible = false;
		}

		public int getIndex() {
			return this.index;
		}

		public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
			if (this.hovered && GunTableScreen.this.handler.getRecipes().size() > this.index
					+ GunTableScreen.this.indexStartOffset) {
				ItemStack itemStack3;
				if (mouseX < this.x + 20) {
					itemStack3 = GunTableScreen.this.handler.getRecipes()
							.get(this.index + GunTableScreen.this.indexStartOffset).getAdjustedFirstBuyItem();
					GunTableScreen.this.renderTooltip(matrices, itemStack3, mouseX, mouseY);
				} else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
					itemStack3 = GunTableScreen.this.handler.getRecipes()
							.get(this.index + GunTableScreen.this.indexStartOffset).getSecondBuyItem();
					if (!itemStack3.isEmpty()) {
						GunTableScreen.this.renderTooltip(matrices, itemStack3, mouseX, mouseY);
					}
				} else if (mouseX > this.x + 65) {
					itemStack3 = GunTableScreen.this.handler.getRecipes()
							.get(this.index + GunTableScreen.this.indexStartOffset).getMutableSellItem();
					GunTableScreen.this.renderTooltip(matrices, itemStack3, mouseX, mouseY);
				}
			}

		}
	}
}