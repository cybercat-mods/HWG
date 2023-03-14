package mod.azure.hwg.client.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GunTableInventory implements Container {
	private final GunTableScreenHandler container;

	private final NonNullList<ItemStack> stacks;


	public GunTableInventory(GunTableScreenHandler container) {
		this.stacks = NonNullList.withSize(6, ItemStack.EMPTY);
		this.container = container;
	}

	public int getContainerSize() {
		return this.stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack stack : stacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getItem(int slot) {
		return this.stacks.get(slot);
	}

	public ItemStack removeItem(int slot, int amount) {
		ItemStack itemStack = ContainerHelper.removeItem(this.stacks, slot, amount);
		if (!itemStack.isEmpty() && slot != 5) {
			this.container.slotsChanged(this);
		}
		return itemStack;
	}

	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(this.stacks, slot);
	}

	public void setItem(int slot, ItemStack stack) {
		this.stacks.set(slot, stack);
		if (slot != 5)
		container.slotsChanged(this);
	}

	public boolean stillValid(Player player) {
		return true;
	}

	public void setChanged() {

	}

	public void clearContent() {
		this.stacks.clear();
	}

}
