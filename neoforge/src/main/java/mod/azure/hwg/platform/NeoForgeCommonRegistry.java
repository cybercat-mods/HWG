package mod.azure.hwg.platform;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import mod.azure.hwg.NeoForgeMod;
import mod.azure.hwg.platform.services.CommonRegistry;

public class NeoForgeCommonRegistry implements CommonRegistry {

    private static final List<Map.Entry<Supplier<VillagerProfession>, LevelTrades>> villagerTradeData =
        new ArrayList<>();

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(
        String modID,
        String blockEntityName,
        Supplier<BlockEntityType<T>> blockEntityType
    ) {
        return NeoForgeMod.BLOCK_ENTITIES.register(blockEntityName, blockEntityType);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String modID, String blockName, Supplier<T> block) {
        return NeoForgeMod.BLOCKS.register(blockName, block);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(
        String modID,
        String entityName,
        Supplier<EntityType<T>> entity
    ) {
        return NeoForgeMod.ENTITIES.register(entityName, entity);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String modID, String itemName, Supplier<T> item) {
        return NeoForgeMod.ITEMS.register(itemName, item);
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(
        Supplier<EntityType<E>> entityType,
        int primaryEggColour,
        int secondaryEggColour,
        Item.Properties itemProperties
    ) {
        return () -> new DeferredSpawnEggItem(entityType, primaryEggColour, secondaryEggColour, itemProperties);
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSound(String modID, String soundName, Supplier<T> sound) {
        return NeoForgeMod.SOUNDS.register(soundName, sound);
    }

    @Override
    public <T extends MenuType<?>> Supplier<T> registerScreen(String modID, String screenName, Supplier<T> menuType) {
        return NeoForgeMod.CONTAIN.register(screenName, menuType);
    }

    @Override
    public <T extends ParticleType<?>> Supplier<T> registerParticle(
        String modID,
        String particleName,
        Supplier<T> particle
    ) {
        return NeoForgeMod.PARTICLES.register(particleName, particle);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(
        String modID,
        String tabName,
        Supplier<T> tab
    ) {
        return NeoForgeMod.TABS.register(tabName, tab);
    }

    @Override
    public <T extends RecipeSerializer<?>> Supplier<T> registerRecipe(
        String modID,
        String serialName,
        Supplier<T> recipe
    ) {
        return NeoForgeMod.RECIPE_SERIALIZER.register(serialName, recipe);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String registryName, Supplier<? extends T> supplier) {
        if (registry == BuiltInRegistries.VILLAGER_PROFESSION) {
            return (Supplier<T>) NeoForgeMod.PROFESSION.register(registryName, (Supplier<VillagerProfession>) supplier);
        }

        throw new IllegalArgumentException(
            "Received registration attempt for an unhandled registry. Registry: " + registry
        );
    }

    @Override
    public void registerVillagerTrade(
        Supplier<VillagerProfession> villagerProfessionSupplier,
        int level,
        List<VillagerTrades.ItemListing> villagerTradeItemListings
    ) {
        villagerTradeData.add(
            new AbstractMap.SimpleEntry<>(
                villagerProfessionSupplier,
                new LevelTrades(level, villagerTradeItemListings)
            )
        );
    }

    public static List<Map.Entry<Supplier<VillagerProfession>, LevelTrades>> getVillagerTradeData() {
        return villagerTradeData;
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public Holder<PoiType> getPOIType() {
        return NeoForgeMod.GUNSMITH_POI;
    }

    @Override
    public VillagerProfession getProfession() {
        return NeoForgeMod.GUNSMITH.get();
    }

    public record LevelTrades(
        int level,
        List<VillagerTrades.ItemListing> trades
    ) {}
}
