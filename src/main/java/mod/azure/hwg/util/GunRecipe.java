package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class GunRecipe extends SpecialCraftingRecipe {
	private static final Ingredient FIREWORK_STAR = Ingredient.ofItems(Items.FIREWORK_STAR);
	private static final Ingredient GUNPOWDER = Ingredient.ofItems(Items.GUNPOWDER);
	private static final Ingredient IRON_NUGGET = Ingredient.ofItems(Items.IRON_NUGGET);

	public GunRecipe(Identifier identifier) {
		super(identifier);
	}

	public boolean matches(CraftingInventory craftingInventory, World world) {
		boolean bl = false;
		int i = 0;

		for (int j = 0; j < craftingInventory.size(); ++j) {
			ItemStack itemStack = craftingInventory.getStack(j);
			if (!itemStack.isEmpty()) {
				if (itemStack.getItem() instanceof DyeItem) {
					bl = true;
				} else if (IRON_NUGGET.test(itemStack)) {
					if (bl) {
						return false;
					}

					bl = true;
				} else if (GUNPOWDER.test(itemStack)) {
					++i;
					if (i > 3) {
						return false;
					}
				} else if (!FIREWORK_STAR.test(itemStack)) {
					return false;
				}
			}
		}

		return bl && i >= 1;
	}

	public ItemStack craft(CraftingInventory craftingInventory) {
		ItemStack itemStack = new ItemStack(HWGItems.AK47, 3);
		CompoundTag compoundTag = itemStack.getOrCreateSubTag("Fireworks");
		ListTag listTag = new ListTag();
		int i = 0;

		for (int j = 0; j < craftingInventory.size(); ++j) {
			ItemStack itemStack2 = craftingInventory.getStack(j);
			if (!itemStack2.isEmpty()) {
				if (GUNPOWDER.test(itemStack2)) {
					++i;
				} else if (FIREWORK_STAR.test(itemStack2)) {
					CompoundTag compoundTag2 = itemStack2.getSubTag("Explosion");
					if (compoundTag2 != null) {
						listTag.add(compoundTag2);
					}
				}
			}
		}

		compoundTag.putByte("Flight", (byte) i);
		if (!listTag.isEmpty()) {
			compoundTag.put("Explosions", listTag);
		}

		return itemStack;
	}

	@Environment(EnvType.CLIENT)
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}

	public ItemStack getOutput() {
		return new ItemStack(HWGItems.AK47);
	}

	public RecipeSerializer<?> getSerializer() {
		return HWGMod.GUNS_RECIPE_SERIALIZER;
	}
}