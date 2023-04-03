package mod.azure.hwg.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import net.minecraft.resources.ResourceLocation;

public class ReiPlugin implements REIClientPlugin {

	public static final CategoryIdentifier<HWGDisplay> CRAFTING = CategoryIdentifier.of(new ResourceLocation(HWGMod.MODID, "crafting"));

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new HWGCategory());
		registry.addWorkstations(CRAFTING, HWGCategory.ICON);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerFiller(GunTableRecipe.class, HWGDisplay::new);
	}
}
