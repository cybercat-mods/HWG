package mod.azure.hwg.client;

import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModelProviderinit {

	public static void init() {
		ItemProperties.register(HWGItems.ROCKETLAUNCHER, new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		ItemProperties.register(HWGItems.SNIPER, new ResourceLocation("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped() ? 1.0F : 0.0F;
					return 0.0F;
				});
	}

	private static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}

	private static boolean isScoped() {
		return ClientInit.scope.isDown();
	}
}