package mod.azure.hwg.platform.services;

import net.minecraft.core.particles.ParticleType;
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

public interface CommonRegistry {

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String modID, String blockEntityName, Supplier<BlockEntityType<T>> blockEntityType);

    <T extends Block> Supplier<T> registerBlock(String modID, String blockName, Supplier<T> block);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String modID, String entityName, Supplier<EntityType<T>> entity);

    <T extends Item> Supplier<T> registerItem(String modID, String itemName, Supplier<T> item);

    <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(Supplier<EntityType<E>> entityType, int primaryEggColour, int secondaryEggColour, Item.Properties itemProperties);

    <T extends SoundEvent> Supplier<T> registerSound(String modID, String soundName, Supplier<T> sound);

    <T extends MenuType<?>> Supplier<T> registerScreen(String modID, String screenName, Supplier<T> menuType);

    <T extends ParticleType<?>> Supplier<T> registerParticle(String modID, String particleName, Supplier<T> particle);

    <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String modID, String tabName, Supplier<T> tab);

    <T extends RecipeSerializer<?>> Supplier<T> registerRecipe(String modID, String serialName, Supplier<T> recipe);

    <T extends VillagerProfession> Supplier<T> registerProfession(String modID, String serialName, Supplier<T> supplier);

    <T extends PoiType> Supplier<T> registerPOI(String modID, String serialName, Supplier<T> supplier);

    CreativeModeTab.Builder newCreativeTabBuilder();

}
