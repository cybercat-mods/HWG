package mod.azure.hwg.util.recipes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import mod.azure.hwg.client.gui.GunTableInventory;
import mod.azure.hwg.util.registry.ModRecipes;

public record GunTableRecipe(
    List<Pair<Ingredient, Integer>> ingredients,
    ItemStack output
) implements Recipe<GunTableInventory.CustomRecipeInput>, Comparable<GunTableRecipe> {

    @Override
    public boolean matches(@NotNull GunTableInventory.CustomRecipeInput inv, @NotNull Level world) {
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

    @Override
    public @NotNull ItemStack assemble(
        @NotNull GunTableInventory.CustomRecipeInput craftingContainer,
        HolderLookup.@NotNull Provider registries
    ) {
        return this.getResultItem(registries).copy();
    }

    public Ingredient getIngredientForSlot(int index) {
        return ingredients.get(index).getFirst();
    }

    public int countRequired(int index) {
        return ingredients.get(index).getSecond();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return output;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.GUN_TABLE_SERIAL.get();
    }

    public static class Type implements RecipeType<GunTableRecipe> {

        public static final Type INSTANCE = new Type();

        public static final String ID = "gun_table";

        private Type() {}
    }

    @Override
    public int compareTo(@NotNull GunTableRecipe o) {
        var outputThis = output.getItem();
        var outputOther = o.output.getItem();
        return BuiltInRegistries.ITEM.getKey(outputThis).compareTo(BuiltInRegistries.ITEM.getKey(outputOther));
    }

    public static class Serializer implements RecipeSerializer<GunTableRecipe> {

        public static Serializer INSTANCE = new Serializer();

        public Serializer() {}

        public static final MapCodec<GunTableRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                Codec.list(
                    Codec.mapPair(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient"),
                        Codec.INT.fieldOf("count")
                    ).codec()
                ).fieldOf("ingredients").forGetter(i -> i.ingredients),
                ItemStack.CODEC.fieldOf("result").forGetter(i -> i.output)
            ).apply(inst, GunTableRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, GunTableRecipe> STREAM_CODEC = StreamCodec.of(
            Serializer::toNetwork,
            Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<GunTableRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, GunTableRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static GunTableRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            List<Pair<Ingredient, Integer>> list = buffer.readList(
                buf -> Pair.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer), buffer.readInt())
            );
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new GunTableRecipe(list, output);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, GunTableRecipe recipe) {
            buffer.writeCollection(recipe.ingredients, (buf, pair) -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, pair.getFirst());
                buf.writeInt(pair.getSecond());
            });
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}
