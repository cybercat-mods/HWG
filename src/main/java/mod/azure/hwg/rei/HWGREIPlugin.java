package mod.azure.hwg.rei;

import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.plugin.DefaultPlugin;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.util.Identifier;

public class HWGREIPlugin implements REIPluginV0 {
	@Override
	public Identifier getPluginIdentifier() {
		return new Identifier("hwg:hwg_plugin");
	}

	@Override
	public void registerOthers(RecipeHelper recipeHelper) {
		recipeHelper.registerWorkingStations(DefaultPlugin.CRAFTING, EntryStack.create(HWGItems.GUN_TABLE));
	}
}