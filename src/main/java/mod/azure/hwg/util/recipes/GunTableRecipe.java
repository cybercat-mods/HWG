package mod.azure.hwg.util.recipes;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.gui.GunTableInventory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record GunTableRecipe(ResourceLocation id, List<Pair<Ingredient, Integer>> ingredients, ItemStack output) implements Recipe<GunTableInventory>, Comparable<GunTableRecipe> {

    @Override
    public boolean matches(GunTableInventory inv, Level world) {
        for (int i = 0; i < this.ingredients.size(); i++) {
            var slotStack = inv.getItem(i);
            var pair = ingredients.get(i);
            var ingredient = pair.getFirst();
            var count = pair.getSecond();
            if (slotStack.getCount() < count || !(ingredient.test(slotStack)))
                return false;
        }
        return true;
    }

    public Ingredient getIngredientForSlot(int index) {
        return ingredients.get(index).getFirst();
    }

    public int countRequired(int index) {
        return ingredients.get(index).getSecond();
    }

    @Override
    public ItemStack assemble(GunTableInventory inv, RegistryAccess var2) {
        return this.getResultItem(var2).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess var1) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HWGMod.GUN_TABLE_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public int compareTo(@NotNull GunTableRecipe o) {
        var outputThis = output.getItem();
        var outputOther = o.output.getItem();
        return BuiltInRegistries.ITEM.getKey(outputThis).compareTo(BuiltInRegistries.ITEM.getKey(outputOther));
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public static class Type implements RecipeType<GunTableRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "gun_table";

        private Type() {
        }
    }

    public static class Serializer implements RecipeSerializer<GunTableRecipe> {

        private static List<Pair<Ingredient, Integer>> getIngredients(String pattern, Map<String, Pair<Ingredient, Integer>> keys, int width) {
            List<Pair<Ingredient, Integer>> pairList = new ArrayList<>();
            for (int i = 0; i < 5; i++)
                pairList.add(Pair.of(Ingredient.EMPTY, 0));
            var set = Sets.newHashSet(keys.keySet());
            set.remove(" ");

            for (int i = 0; i < pattern.length(); ++i) {
                var key = pattern.substring(i, i + 1);
                var ingredient = keys.get(key);
                if (ingredient == null)
                    throw new JsonSyntaxException(
                            "Pattern references symbol '" + key + "' but it's not defined in the key");

                set.remove(key);
                pairList.set(i, Pair.of(ingredient.getFirst(), keys.get(key).getSecond()));
            }

            if (!set.isEmpty())
                throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
            else
                return pairList;
        }

        private static Map<String, Pair<Ingredient, Integer>> getComponents(JsonObject json) {
            Map<String, Pair<Ingredient, Integer>> map = Maps.newHashMap();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                var key = entry.getKey();
                var jsonElement = entry.getValue();
                if (key.length() != 1)
                    throw new JsonSyntaxException(
                            "Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 String only).");

                if (" ".equals(key))
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

                map.put(key, Pair.of(Ingredient.fromJson(jsonElement),
                        GsonHelper.getAsInt(jsonElement.getAsJsonObject(), "count", 1)));
            }

            map.put(" ", Pair.of(Ingredient.EMPTY, 0));
            return map;
        }

        @Override
        public GunTableRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject) {

            var pattern = GsonHelper.getAsString(jsonObject, "pattern");
            var map = getComponents(GsonHelper.getAsJsonObject(jsonObject, "key"));
            var pairList = getIngredients(pattern, map, pattern.length());

            if (pairList.isEmpty())
                throw new JsonParseException("No ingredients for gun table recipe");
            else if (pairList.size() > 5)
                throw new JsonParseException("Too many ingredients for gun table recipe");
            else {
                var itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
                return new GunTableRecipe(identifier, pairList, itemStack);
            }
        }

        @Override
        public GunTableRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf packetByteBuf) {
            List<Pair<Ingredient, Integer>> list = packetByteBuf.readList(buf -> Pair.of(Ingredient.fromNetwork(buf), buf.readInt()));

            ItemStack output = packetByteBuf.readItem();

            return new GunTableRecipe(identifier, list, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf packetByteBuf, GunTableRecipe gunTableRecipe) {
            packetByteBuf.writeCollection(gunTableRecipe.ingredients, (buf, pair) -> {
                pair.getFirst().toNetwork(buf);
                buf.writeInt(pair.getSecond());
            });
            packetByteBuf.writeItem(gunTableRecipe.output);
        }
    }
}
