package mod.azure.hwg.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stats;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;

public class GunTableOutputSlot extends Slot {
	private final GunTableInventory merchantInventory;
	private final PlayerEntity player;
	private int amount;
	private final Merchant merchant;

	public GunTableOutputSlot(PlayerEntity player, Merchant merchant, GunTableInventory merchantInventory, int index,
			int x, int y) {
		super(merchantInventory, index, x, y);
		this.player = player;
		this.merchant = merchant;
		this.merchantInventory = merchantInventory;
	}

	public boolean canInsert(ItemStack stack) {
		return false;
	}

	public ItemStack takeStack(int amount) {
		if (this.hasStack()) {
			this.amount += Math.min(amount, this.getStack().getCount());
		}

		return super.takeStack(amount);
	}

	protected void onCrafted(ItemStack stack, int amount) {
		this.amount += amount;
		this.onCrafted(stack);
	}

	protected void onCrafted(ItemStack stack) {
		stack.onCraft(this.player.world, this.player, this.amount);
		this.amount = 0;
	}

	public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
		this.onCrafted(stack);
		TradeOffer tradeOffer = this.merchantInventory.getTradeOffer();
		if (tradeOffer != null) {
			ItemStack itemStack = this.merchantInventory.getStack(0);
			ItemStack itemStack2 = this.merchantInventory.getStack(1);
			if (tradeOffer.depleteBuyItems(itemStack, itemStack2)
					|| tradeOffer.depleteBuyItems(itemStack2, itemStack)) {
				this.merchant.trade(tradeOffer);
				player.incrementStat(Stats.TRADED_WITH_VILLAGER);
				this.merchantInventory.setStack(0, itemStack);
				this.merchantInventory.setStack(1, itemStack2);
			}

			this.merchant.setExperienceFromServer(this.merchant.getExperience() + tradeOffer.getMerchantExperience());
		}

		return stack;
	}
}
