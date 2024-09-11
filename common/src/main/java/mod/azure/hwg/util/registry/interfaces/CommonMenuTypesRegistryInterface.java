package mod.azure.hwg.util.registry.interfaces;

import mod.azure.hwg.platform.Services;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

/**
 * Example of using this Interface to create a new MenuType:
 * <p>
 * The following code demonstrates how to register a new screen type in the game:
 * </p>
 * <pre>{@code
 * public static final Supplier<MenuType<?>> TEST = CommonMenuTypesRegistryInterface.registerScreen("modid", "screenname", () -> new MenuType<>(CustomScreenHandler::new, FeatureFlags.VANILLA_SET));
 * }</pre>
 * <p>
 * In this example:
 * </p>
 * <ul>
 * <li><code>registerScreen</code> is a method to register a new screen type with the specified mod ID and screen name.</li>
 * <li><code>MenuType</code> is used to create a new screen type instance.</li>
 * </ul>
 * <p>
 * The {@link MenuType MenuType} class represents a type of screen in the game.
 * </p>
 */
public interface CommonMenuTypesRegistryInterface {

    /**
     * Registers a new screen type.
     *
     * @param modID      The mod ID.
     * @param screenName The name of the screen.
     * @param item       A supplier for the screen type.
     * @param <T>        The type of the screen type.
     * @return A supplier for the registered screen type.
     */
    static <T extends MenuType<?>> Supplier<T> registerScreen(String modID, String screenName, Supplier<T> item) {
        return Services.COMMON_REGISTRY.registerScreen(modID, screenName, item);
    }
}