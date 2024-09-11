package mod.azure.hwg.platform;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.platform.services.CommonRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class FabricCommonRegistry implements CommonRegistry {

    private static <T, R extends Registry<? super T>> Supplier<T> registerSupplier(R registry, String modID, String id, Supplier<T> object) {
        final T registeredObject = Registry.register((Registry<T>) registry,
                ResourceLocation.fromNamespaceAndPath(modID, id), object.get());

        return () -> registeredObject;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String modID, String blockEntityName, Supplier<BlockEntityType<T>> blockEntityType) {
        return registerSupplier(BuiltInRegistries.BLOCK_ENTITY_TYPE, modID, blockEntityName, blockEntityType);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String modID, String blockName, Supplier<T> block) {
        return registerSupplier(BuiltInRegistries.BLOCK, modID, blockName, block);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String modID, String entityName, Supplier<EntityType<T>> entity) {
        return registerSupplier(BuiltInRegistries.ENTITY_TYPE, modID, entityName, entity);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String modID, String itemName, Supplier<T> item) {
        return registerSupplier(BuiltInRegistries.ITEM, modID, itemName, item);
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(Supplier<EntityType<E>> entityType, int primaryEggColour, int secondaryEggColour, Item.Properties itemProperties) {
        return () -> new SpawnEggItem(entityType.get(), primaryEggColour, secondaryEggColour, itemProperties);
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSound(String modID, String soundName, Supplier<T> sound) {
        return registerSupplier(BuiltInRegistries.SOUND_EVENT, modID, soundName, sound);
    }

    @Override
    public <T extends MenuType<?>> Supplier<T> registerScreen(String modID, String screenName, Supplier<T> menuType) {
        return registerSupplier(BuiltInRegistries.MENU, modID, screenName, menuType);
    }

    @Override
    public <T extends ParticleType<?>> Supplier<T> registerParticle(String modID, String particleName, Supplier<T> particle) {
        return registerSupplier(BuiltInRegistries.PARTICLE_TYPE, modID, particleName, particle);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String modID, String tabName, Supplier<T> tab) {
        return registerSupplier(BuiltInRegistries.CREATIVE_MODE_TAB, modID, tabName, tab);
    }

    @Override
    public <T extends RecipeSerializer<?>> Supplier<T> registerRecipe(String modID, String serialName, Supplier<T> recipe) {
        return registerSupplier(BuiltInRegistries.RECIPE_SERIALIZER, modID, serialName, recipe);
    }

    @Override
    public <T extends VillagerProfession> Supplier<T> registerProfession(String modID, String serialName, Supplier<T> supplier) {
        return registerSupplier(BuiltInRegistries.VILLAGER_PROFESSION, modID, serialName, supplier);
    }

    @Override
    public <T extends PoiType> Supplier<T> registerPOI(String modID, String serialName, Supplier<T> supplier) {
        return registerSupplier(BuiltInRegistries.POINT_OF_INTEREST_TYPE, modID, serialName, supplier);
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return FabricItemGroup.builder();
    }
}
