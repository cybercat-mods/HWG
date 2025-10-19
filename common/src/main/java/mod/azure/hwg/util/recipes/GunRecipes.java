package mod.azure.hwg.util.recipes;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.client.gui.GunTableInventory;

public interface GunRecipes extends Recipe<GunTableInventory.CustomRecipeInput> {

    default @NotNull RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }
}
