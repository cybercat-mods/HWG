package mod.azure.hwg.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.hwg.item.weapons.HWGGunBase;
import mod.azure.hwg.item.weapons.HWGGunLoadedBase;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(value = AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

	public AnvilScreenHandlerMixin(@Nullable MenuType<?> type, int syncId, Inventory playerInventory,
			ContainerLevelAccess context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "createResult", at = @At(value = "RETURN"))
	private void updateRuinedRepair(CallbackInfo ci) {
		ItemStack leftStack = this.inputSlots.getItem(0).copy();
		ItemStack rightStack = this.inputSlots.getItem(1).copy();
		if ((leftStack.getItem() instanceof HWGGunBase || leftStack.getItem() instanceof HWGGunLoadedBase)
				&& rightStack.getItem() == Items.ENCHANTED_BOOK) {
			ItemStack repaired = ItemStack.EMPTY;
			this.resultSlots.setItem(0, repaired);
			this.broadcastChanges();
		}
	}
}