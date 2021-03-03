package mod.azure.hwg.mixin;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import mod.azure.hwg.item.weapons.HWGGunBase;
import mod.azure.hwg.item.weapons.HWGGunLoadedBase;
import mod.azure.hwg.util.HWGEquipmentUtils;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;

/**
 * 
 * Credit to RuinedEquipment
 * https://github.com/Pepperoni-Jabroni/RuinedEquipment/blob/main/src/main/java/pepjebs/ruined_equipment/mixin/AnvilScreenHandlerMixin.java
 *
 */
@Mixin(value = AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

	private static final double REPAIR_MODIFIER = 9999.0;

	@Shadow
	@Final
	private Property levelCost;

	@Shadow
	private int repairItemUsage;

	@Shadow
	private String newItemName;

	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory,
			ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "updateResult", at = @At(value = "RETURN"))
	private void updateRuinedRepair(CallbackInfo ci) {
		ItemStack leftStack = this.input.getStack(0).copy();
		ItemStack rightStack = this.input.getStack(1).copy();
		if (leftStack.getItem() instanceof HWGGunBase) {
			HWGGunBase ruinedItem = (HWGGunBase) leftStack.getItem();
			Item vanillaItem = HWGItems.getItemMap().get(ruinedItem);
			int vanillaMaxDamage = vanillaItem.getMaxDamage() - 1;
			ItemStack repaired = ItemStack.EMPTY;
			int maxLevel = 4;
			if (rightStack.getItem() == vanillaItem) {
				int targetDamage = rightStack.getDamage() - (int) (REPAIR_MODIFIER * rightStack.getMaxDamage());
				repaired = HWGEquipmentUtils.generateRepairedItemForAnvilByDamage(leftStack,
						Math.min(targetDamage, vanillaMaxDamage));
				maxLevel = 2;
				this.repairItemUsage = 0;
			}
			if (!repaired.isEmpty()) {
				int levelCost = HWGEquipmentUtils.generateRepairLevelCost(repaired, maxLevel);
				if (this.newItemName.compareTo(leftStack.getName().getString()) != 0) {
					if (StringUtils.isBlank(this.newItemName)) {
						repaired.removeCustomName();
					} else {
						repaired.setCustomName(new LiteralText(this.newItemName));
						levelCost++;
					}
				}
				this.levelCost.set(levelCost);
			}
			this.output.setStack(0, repaired);
			this.sendContentUpdates();
		}
		if (leftStack.getItem() instanceof HWGGunLoadedBase) {
			HWGGunLoadedBase ruinedItem = (HWGGunLoadedBase) leftStack.getItem();
			Item vanillaItem = HWGItems.getItemMap().get(ruinedItem);
			int vanillaMaxDamage = vanillaItem.getMaxDamage() - 1;
			ItemStack repaired = ItemStack.EMPTY;
			int maxLevel = 4;
			if (rightStack.getItem() == vanillaItem) {
				int targetDamage = rightStack.getDamage() - (int) (REPAIR_MODIFIER * rightStack.getMaxDamage());
				repaired = HWGEquipmentUtils.generateRepairedItemForAnvilByDamage(leftStack,
						Math.min(targetDamage, vanillaMaxDamage));
				maxLevel = 2;
				this.repairItemUsage = 0;
			}
			if (!repaired.isEmpty()) {
				int levelCost = HWGEquipmentUtils.generateRepairLevelCost(repaired, maxLevel);
				if (this.newItemName.compareTo(leftStack.getName().getString()) != 0) {
					if (StringUtils.isBlank(this.newItemName)) {
						repaired.removeCustomName();
					} else {
						repaired.setCustomName(new LiteralText(this.newItemName));
						levelCost++;
					}
				}
				this.levelCost.set(levelCost);
			}
			this.output.setStack(0, repaired);
			this.sendContentUpdates();
		}
	}
}