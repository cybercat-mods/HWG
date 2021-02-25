package mod.azure.hwg.util;

import mod.azure.hwg.client.gui.GunTableInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public interface GunRecipes extends Recipe<GunTableInventory> {
	default RecipeType<?> getType() {
		return RecipeType.CRAFTING;
	}
}