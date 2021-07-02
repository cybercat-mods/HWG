package mod.azure.hwg.client;

import mod.azure.hwg.util.registry.HWGItems;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModelProviderinit {

	public static void init() {
		FabricModelPredicateProviderRegistry.register(HWGItems.ROCKETLAUNCHER, new Identifier("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		FabricModelPredicateProviderRegistry.register(HWGItems.SNIPER, new Identifier("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isSneaking(livingEntity) ? 1.0F : 0.0F;
					return 0.0F;
				});
	}

	private static boolean isUsable(ItemStack stack) {
		return stack.getDamage() < stack.getMaxDamage() - 1;
	}

	private static boolean isSneaking(LivingEntity livingEntity) {
		return livingEntity.isSneaking();
	}
}