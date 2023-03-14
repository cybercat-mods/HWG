package mod.azure.hwg.util.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class HWGSpecialCraftingRecipe implements GunRecipes {
	private final ResourceLocation id;

	public HWGSpecialCraftingRecipe(ResourceLocation id) {
	      this.id = id;
	   }

	public ResourceLocation getId() {
		return this.id;
	}

	public boolean isSpecial() {
		return true;
	}

	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}
}