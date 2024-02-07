package mod.azure.hwg.client;

import mod.azure.azurelib.common.api.client.helper.ClientUtils;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public record ModelProviderinit() {

    public static void init() {
        ItemProperties.register(HWGItems.SNIPER, new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity != null)
                return isScoped() ? 1.0F : 0.0F;
            return 0.0F;
        });
    }

    private static boolean isScoped() {
        return ClientUtils.SCOPE.isDown();
    }
}