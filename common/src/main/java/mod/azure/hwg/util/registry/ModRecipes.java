package mod.azure.hwg.util.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.util.recipes.GunTableRecipe.Serializer;
import mod.azure.hwg.util.registry.interfaces.CommonRecipeRegistryInterface;

public record ModRecipes() implements CommonRecipeRegistryInterface {

    public static final Supplier<RecipeSerializer<?>> GUN_TABLE_SERIAL = CommonRecipeRegistryInterface.registerRecipe(
        CommonMod.MOD_ID,
        "gun_table",
        () -> Serializer.INSTANCE
    );

    public static void init() {}
}
