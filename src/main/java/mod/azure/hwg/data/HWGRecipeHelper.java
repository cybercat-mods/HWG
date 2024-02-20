package mod.azure.hwg.data;

import com.mojang.datafixers.util.Pair;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface HWGRecipeHelper {
	static void makeColoredFlares(RecipeOutput exporter) {
		makeGunTableRecipe(exporter, HWGItems.BLACK_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.BLACK_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.BLUE_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.BLUE_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.BROWN_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.BROWN_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.CYAN_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.CYAN_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.GRAY_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.GRAY_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.GREEN_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.GREEN_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.LIGHTBLUE_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.LIGHT_BLUE_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.LIGHTGRAY_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.LIGHT_GRAY_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.LIME_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.LIME_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.MAGENTA_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.MAGENTA_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.ORANGE_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.ORANGE_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.PINK_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.PINK_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.PURPLE_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.PURPLE_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.RED_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.RED_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.WHITE_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.WHITE_DYE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.YELLOW_FLARE, 2, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 3),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.YELLOW_DYE), 1)
		));
	}

	static void makeGrenades(RecipeOutput exporter) {
		makeGunTableRecipe(exporter, HWGItems.G_EMP, 2, List.of(
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 5),
				Pair.of(Ingredient.of(Items.REDSTONE), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.G_FRAG, 2, List.of(
				Pair.of(Ingredient.of(Items.TNT), 1),
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 5),
				Pair.of(Ingredient.of(Items.STICK), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.G_NAPALM, 2, List.of(
				Pair.of(Ingredient.of(Items.TNT), 1),
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 5),
				Pair.of(Ingredient.of(Items.MAGMA_CREAM), 2),
				Pair.of(Ingredient.of(Items.STICK), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.G_SMOKE, 2, List.of(
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 5),
				Pair.of(Ingredient.of(Items.BLACK_DYE), 1),
				Pair.of(Ingredient.of(Items.STICK), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.G_STUN, 2, List.of(
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2),
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 5),
				Pair.of(Ingredient.of(Items.REDSTONE), 2),
				Pair.of(Ingredient.of(Items.STICK), 1)
		));
	}

	static void makeWeapons(RecipeOutput exporter) {
		makeGunTableRecipe(exporter, HWGItems.AK47, List.of(
				Pair.of(Ingredient.of(Items.IRON_INGOT), 10),
				Pair.of(Ingredient.of(Items.OAK_PLANKS), 1),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.BLACK_TERRACOTTA), 2)
		));

		makeGunTableRecipe(exporter, HWGItems.FLARE_GUN, List.of(
				Pair.of(Ingredient.of(Items.IRON_INGOT), 5),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.RED_DYE), 1),
				Pair.of(Ingredient.of(Items.FIREWORK_ROCKET), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.LUGER, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 54),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.BLACK_TERRACOTTA), 2)
		));

		makeGunTableRecipe(exporter, HWGItems.PISTOL, List.of(
				Pair.of(Ingredient.of(Items.IRON_INGOT), 5),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.BLACK_TERRACOTTA), 2)
		));

		makeGunTableRecipe(exporter, HWGItems.SHOTGUN, List.of(
				Pair.of(Ingredient.of(Items.IRON_INGOT), 7),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.BLACK_TERRACOTTA), 2),
				Pair.of(Ingredient.of(Items.OAK_PLANKS), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.SMG, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 7),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.BLACK_TERRACOTTA), 2),
				Pair.of(Ingredient.of(Items.DIAMOND), 1)
		));

		makeGunTableRecipe(exporter, HWGItems.SPISTOL, List.of(
				Pair.of(Ingredient.of(HWGItems.PISTOL), 1),
				Pair.of(Ingredient.of(Items.IRON_INGOT), 2)
		));

		HWGRecipeHelper.makeGunTableRecipe(exporter, HWGItems.TOMMYGUN, List.of(
				Pair.of(Ingredient.of(Items.IRON_INGOT), 12),
				Pair.of(Ingredient.of(Items.LEVER), 1),
				Pair.of(Ingredient.of(Items.BLACK_TERRACOTTA), 3),
				Pair.of(Ingredient.of(Items.DIAMOND), 1),
				Pair.of(Ingredient.of(Items.REPEATER), 1)
		));
	}

	static void makeAmmo(RecipeOutput exporter) {
		HWGRecipeHelper.makeColoredFlares(exporter);

		makeGunTableRecipe(exporter, HWGItems.BULLETS, 10, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 2),
				Pair.of(Ingredient.of(Items.GOLD_NUGGET), 4),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 2)
		));

		makeGunTableRecipe(exporter, HWGItems.FUEL_TANK, List.of(
				Pair.of(Ingredient.of(Items.BUCKET), 2),
				Pair.of(Ingredient.of(Items.IRON_INGOT), 4),
				Pair.of(Ingredient.of(Items.RED_DYE), 2)
		));

		makeGunTableRecipe(exporter, HWGItems.ROCKET, 2, List.of(
				Pair.of(Ingredient.of(Items.TNT), 3),
				Pair.of(Ingredient.of(Items.IRON_INGOT), 3),
				Pair.of(Ingredient.of(Items.BLAZE_POWDER), 2)
		));

		makeGunTableRecipe(exporter, HWGItems.SHOTGUN_SHELL, 10, List.of(
				Pair.of(Ingredient.of(Items.IRON_NUGGET), 5),
				Pair.of(Ingredient.of(Items.LEVER), 2),
				Pair.of(Ingredient.of(Items.GUNPOWDER), 3)
		));

		makeGunTableRecipe(exporter, HWGItems.SNIPER_ROUND, 8, List.of(
				Pair.of(Ingredient.of(Items.IRON_INGOT), 1),
				Pair.of(Ingredient.of(Items.GOLD_INGOT), 2),
				Pair.of(Ingredient.of(Items.DIAMOND), 1)
		));
	}

	static void makeGunTableRecipe(RecipeOutput exporter, Item result, List<Pair<Ingredient, Integer>> ingredients) {
		makeGunTableRecipe(exporter, result.builtInRegistryHolder().key().location(), result, 1, ingredients);
	}

	static void makeGunTableRecipe(RecipeOutput exporter, Item result, int count, List<Pair<Ingredient, Integer>> ingredients) {
		makeGunTableRecipe(exporter, result.builtInRegistryHolder().key().location(), result, count, ingredients);
	}

	static void makeGunTableRecipe(RecipeOutput exporter, ResourceLocation recipeId, Item result, int count, List<Pair<Ingredient, Integer>> ingredients) {
		if (ingredients.size() > 5)
			throw new IllegalStateException("GunTableRecipe cannot have more than 5 ingredients!");

		AdvancementHolder hasTheRecipe = exporter.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
				.rewards(AdvancementRewards.Builder.recipe(recipeId))
				.requirements(AdvancementRequirements.Strategy.OR).build(recipeId.withPrefix("recipes/" + RecipeCategory.COMBAT.getFolderName() + "/"));

		GunTableRecipe recipe = new GunTableRecipe(ingredients, new ItemStack(result, count));

		exporter.accept(recipeId, recipe, hasTheRecipe);
	}
}
