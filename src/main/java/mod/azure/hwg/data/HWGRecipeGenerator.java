package mod.azure.hwg.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;

public class HWGRecipeGenerator extends FabricRecipeProvider implements HWGRecipeHelper {
	public HWGRecipeGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		HWGRecipeHelper.makeWeapons(exporter);

		HWGRecipeHelper.makeGrenades(exporter);

		HWGRecipeHelper.makeAmmo(exporter);
	}
}
