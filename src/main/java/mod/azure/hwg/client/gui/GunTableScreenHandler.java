package mod.azure.hwg.client.gui;

import mod.azure.hwg.HWGMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.Merchant;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class GunTableScreenHandler extends ScreenHandler {
	private final Merchant inventory;
	private final GunTableInventory merchantInventory;

	public GunTableScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleMerchant(playerInventory.player));
	}

	public GunTableScreenHandler(int syncId, PlayerInventory playerInventory, Merchant inventory) {
		super(HWGMod.SCREEN_HANDLER_TYPE, syncId);
		this.inventory = inventory;
		this.merchantInventory = new GunTableInventory(inventory);
		this.addSlot(new Slot(this.merchantInventory, 0, 136, 13));
		this.addSlot(new Slot(this.merchantInventory, 1, 156, 33));
		this.addSlot(new Slot(this.merchantInventory, 2, 116, 33));
		this.addSlot(new Slot(this.merchantInventory, 3, 123, 56));
		this.addSlot(new Slot(this.merchantInventory, 4, 149, 56));
		this.addSlot(new GunTableOutputSlot(playerInventory.player, inventory, this.merchantInventory, 5, 219, 38));

		int k;
		for (k = 0; k < 3; ++k) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 108 + j * 18, 84 + k * 18));
			}
		}

		for (k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 108 + k * 18, 142));
		}

	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.getCurrentCustomer() == player;
	}

	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return false;
	}

	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index == 2) {
				if (!this.insertItem(itemStack2, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onStackChanged(itemStack2, itemStack);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 30) {
					if (!this.insertItem(itemStack2, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.insertItem(itemStack2, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, itemStack2);
		}

		return itemStack;
	}

	public TradeOfferList getRecipes() {
		return this.inventory.getOffers();
	}

	public void setRecipeIndex(int index) {
		this.merchantInventory.setRecipeIndex(index);
	}

	public void switchTo(int recipeIndex) {
		if (this.getRecipes().size() > recipeIndex) {
			ItemStack itemStack = this.merchantInventory.getStack(0);
			if (!itemStack.isEmpty()) {
				if (!this.insertItem(itemStack, 3, 39, true)) {
					return;
				}

				this.merchantInventory.setStack(0, itemStack);
			}

			ItemStack itemStack2 = this.merchantInventory.getStack(1);
			if (!itemStack2.isEmpty()) {
				if (!this.insertItem(itemStack2, 3, 39, true)) {
					return;
				}

				this.merchantInventory.setStack(1, itemStack2);
			}

			if (this.merchantInventory.getStack(0).isEmpty() && this.merchantInventory.getStack(1).isEmpty()) {
				ItemStack itemStack3 = ((TradeOffer) this.getRecipes().get(recipeIndex)).getAdjustedFirstBuyItem();
				this.autofill(0, itemStack3);
				ItemStack itemStack4 = ((TradeOffer) this.getRecipes().get(recipeIndex)).getSecondBuyItem();
				this.autofill(1, itemStack4);
			}

		}
	}

	private void autofill(int slot, ItemStack stack) {
		if (!stack.isEmpty()) {
			for (int i = 3; i < 39; ++i) {
				ItemStack itemStack = ((Slot) this.slots.get(i)).getStack();
				if (!itemStack.isEmpty() && this.equals(stack, itemStack)) {
					ItemStack itemStack2 = this.merchantInventory.getStack(slot);
					int j = itemStack2.isEmpty() ? 0 : itemStack2.getCount();
					int k = Math.min(stack.getMaxCount() - j, itemStack.getCount());
					ItemStack itemStack3 = itemStack.copy();
					int l = j + k;
					itemStack.decrement(k);
					itemStack3.setCount(l);
					this.merchantInventory.setStack(slot, itemStack3);
					if (l >= stack.getMaxCount()) {
						break;
					}
				}
			}
		}

	}

	private boolean equals(ItemStack itemStack, ItemStack otherItemStack) {
		return itemStack.getItem() == otherItemStack.getItem() && ItemStack.areTagsEqual(itemStack, otherItemStack);
	}

	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.setCurrentCustomer((PlayerEntity) null);
		if (!this.inventory.getMerchantWorld().isClient) {
			if (player.isAlive()
					&& (!(player instanceof ServerPlayerEntity) || !((ServerPlayerEntity) player).isDisconnected())) {
				player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(0));
				player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(1));
				player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(2));
				player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(3));
				player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(4));
				player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(5));
			} else {
				ItemStack itemStack0 = this.merchantInventory.removeStack(0);
				ItemStack itemStack1 = this.merchantInventory.removeStack(1);
				ItemStack itemStack2 = this.merchantInventory.removeStack(2);
				ItemStack itemStack3 = this.merchantInventory.removeStack(3);
				ItemStack itemStack4 = this.merchantInventory.removeStack(4);
				ItemStack itemStack5 = this.merchantInventory.removeStack(5);
				if (!itemStack0.isEmpty()) {
					player.dropItem(itemStack0, false);
				}

				if (!itemStack1.isEmpty()) {
					player.dropItem(itemStack1, false);
				}

				if (!itemStack2.isEmpty()) {
					player.dropItem(itemStack2, false);
				}

				if (!itemStack3.isEmpty()) {
					player.dropItem(itemStack3, false);
				}

				if (!itemStack4.isEmpty()) {
					player.dropItem(itemStack4, false);
				}

				if (!itemStack5.isEmpty()) {
					player.dropItem(itemStack5, false);
				}
			}

		}
	}
}