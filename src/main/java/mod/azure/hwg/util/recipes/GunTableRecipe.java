package mod.azure.hwg.util.recipes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.gui.GunTableInventory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GunTableRecipe(List<Pair<Ingredient, Integer>> ingredients, ItemStack output) implements Recipe<GunTableInventory>, Comparable<GunTableRecipe> {

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

    public static class Type implements RecipeType<GunTableRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "gun_table";

        private Type() {
        }
    }

    public static class Serializer implements RecipeSerializer<GunTableRecipe> {
        public static Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public static final Codec<GunTableRecipe> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.list(Codec.mapPair(Ingredient.CODEC_NONEMPTY.fieldOf("ingredient"), Codec.INT.fieldOf("count")).codec()).fieldOf("ingredients").forGetter(i -> i.ingredients),
                ItemStack.RESULT_CODEC.codec().fieldOf("result").forGetter(i -> i.output)
        ).apply(inst, GunTableRecipe::new));

        @Override
        public Codec<GunTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public GunTableRecipe fromNetwork(FriendlyByteBuf packetByteBuf) {
            List<Pair<Ingredient, Integer>> list = packetByteBuf.readList(buf -> Pair.of(Ingredient.fromNetwork(buf), buf.readInt()));

            ItemStack output = packetByteBuf.readItem();

            return new GunTableRecipe(list, output);
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
