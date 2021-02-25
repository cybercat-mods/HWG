package mod.azure.hwg.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class HWGSpecialCraftingRecipe implements GunRecipes {
	private final Identifier id;

	public HWGSpecialCraftingRecipe(Identifier id) {
	      this.id = id;
	   }

	public Identifier getId() {
		return this.id;
	}

	public boolean isIgnoredInRecipeBook() {
		return true;
	}

	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}
}