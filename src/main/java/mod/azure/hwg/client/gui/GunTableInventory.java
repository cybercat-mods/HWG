package mod.azure.hwg.client.gui;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class GunTableInventory implements Inventory {
	private final Merchant merchant;
	private final DefaultedList<ItemStack> inventory;
	@Nullable
	private TradeOffer tradeOffer;
	private int recipeIndex;
	private int merchantRewardedExperience;

	public GunTableInventory(Merchant merchant) {
		this.inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
		this.merchant = merchant;
	}

	public int size() {
		return this.inventory.size();
	}

	public boolean isEmpty() {
		Iterator<ItemStack> var1 = this.inventory.iterator();

		ItemStack itemStack;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			itemStack = (ItemStack) var1.next();
		} while (itemStack.isEmpty());

		return false;
	}

	public ItemStack getStack(int slot) {
		return (ItemStack) this.inventory.get(slot);
	}

	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = (ItemStack) this.inventory.get(slot);
		if (slot == 2 && !itemStack.isEmpty()) {
			return Inventories.splitStack(this.inventory, slot, itemStack.getCount());
		} else {
			ItemStack itemStack2 = Inventories.splitStack(this.inventory, slot, amount);
			if (!itemStack2.isEmpty() && this.needRecipeUpdate(slot)) {
				this.updateRecipes();
			}

			return itemStack2;
		}
	}

	private boolean needRecipeUpdate(int slot) {
		return slot == 0 || slot == 1;
	}

	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.inventory, slot);
	}

	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
		if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}

		if (this.needRecipeUpdate(slot)) {
			this.updateRecipes();
		}

	}

	public boolean canPlayerUse(PlayerEntity player) {
		return this.merchant.getCurrentCustomer() == player;
	}

	public void markDirty() {

	}

	public void updateRecipes() {
		this.tradeOffer = null;
		ItemStack itemStack3;
		ItemStack itemStack4;
		if (((ItemStack) this.inventory.get(0)).isEmpty()) {
			itemStack3 = (ItemStack) this.inventory.get(1);
			itemStack4 = ItemStack.EMPTY;
		} else {
			itemStack3 = (ItemStack) this.inventory.get(0);
			itemStack4 = (ItemStack) this.inventory.get(1);
		}

		if (itemStack3.isEmpty()) {
			this.setStack(2, ItemStack.EMPTY);
			this.merchantRewardedExperience = 0;
		} else {
			TradeOfferList tradeOfferList = this.merchant.getOffers();
			if (!tradeOfferList.isEmpty()) {
				TradeOffer tradeOffer = tradeOfferList.getValidOffer(itemStack3, itemStack4, this.recipeIndex);
				if (tradeOffer == null || tradeOffer.isDisabled()) {
					this.tradeOffer = tradeOffer;
					tradeOffer = tradeOfferList.getValidOffer(itemStack4, itemStack3, this.recipeIndex);
				}

				if (tradeOffer != null && !tradeOffer.isDisabled()) {
					this.tradeOffer = tradeOffer;
					this.setStack(2, tradeOffer.getSellItem());
					this.merchantRewardedExperience = tradeOffer.getMerchantExperience();
				} else {
					this.setStack(2, ItemStack.EMPTY);
					this.merchantRewardedExperience = 0;
				}
			}

			this.merchant.onSellingItem(this.getStack(2));
		}
	}

	@Nullable
	public TradeOffer getTradeOffer() {
		return this.tradeOffer;
	}

	public void setRecipeIndex(int index) {
		this.recipeIndex = index;
		this.updateRecipes();
	}

	public void clear() {
		this.inventory.clear();
	}

	@Environment(EnvType.CLIENT)
	public int getMerchantRewardedExperience() {
		return this.merchantRewardedExperience;
	}
}
