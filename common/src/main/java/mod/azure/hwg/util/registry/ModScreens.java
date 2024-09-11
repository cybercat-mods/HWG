package mod.azure.hwg.util.registry;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.util.registry.interfaces.CommonMenuTypesRegistryInterface;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public record ModScreens() implements CommonMenuTypesRegistryInterface {

    public static final Supplier<MenuType<GunTableScreenHandler>> SCREEN_HANDLER_TYPE = CommonMenuTypesRegistryInterface.registerScreen(
            CommonMod.MOD_ID, "gun_table_gui", () -> new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET));

    public static void init() {
    }
}
