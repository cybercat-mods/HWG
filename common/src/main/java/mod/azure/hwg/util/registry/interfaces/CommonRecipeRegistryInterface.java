package mod.azure.hwg.util.registry.interfaces;

import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

import mod.azure.hwg.platform.Services;

/**
 * Example of using this Interface to create a new Recipe Serializer:
 * <p>
 * The following code demonstrates how to register a new Recipe Serializer in the game:
 * </p>
 *
 * <pre>{@code
 *
 * public static final Supplier<RecipeSerializer<?>> TEST = CommonRecipeRegistryInterface.registerRecipe(
 *     "modid",
 *     "itemname",
 *     CustomRecipe.Serializer::new
 * );
 * }</pre>
 * <p>
 * In this example:
 * </p>
 * <ul>
 * <li><code>registerRecipe</code> is a method to register a new recipe serializer with the specified mod ID and recipe
 * name.</li>
 * <li><code>RecipeSerializer</code> is used to create a new recipe serializer instance.</li>
 * <li><code>CustomRecipe.Serializer::new</code> is a method reference to the constructor of the custom recipe
 * serializer.</li>
 * </ul>
 * <p>
 * The {@link RecipeSerializer RecipeSerializer} class represents a recipe serializer in the game.
 * </p>
 */
public interface CommonRecipeRegistryInterface {

    /**
     * Registers a new Recipe Serializer.
     *
     * @param modID      The mod ID.
     * @param serialName The name of the Recipe Serializer.
     * @param recipe     A supplier for the Recipe Serializer.
     * @param <T>        The type of the Recipe Serializer.
     * @return A supplier for the registered Recipe Serializer.
     */
    static <T extends RecipeSerializer<?>> Supplier<T> registerRecipe(
        String modID,
        String serialName,
        Supplier<T> recipe
    ) {
        return Services.COMMON_REGISTRY.registerRecipe(modID, serialName, recipe);
    }
}
