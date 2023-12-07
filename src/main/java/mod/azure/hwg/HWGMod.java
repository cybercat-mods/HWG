package mod.azure.hwg;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.MobAttributes;
import mod.azure.hwg.util.MobSpawn;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import mod.azure.hwg.util.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

public class HWGMod implements ModInitializer {
    /**
     * TODO: Fix Lesser Demons weapon rendering being too high
     */
    public static HWGConfig config;
    public static final String MODID = "hwg";
    protected final RandomSource random = RandomSource.create();
    public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
    public static final TagKey<Biome> SPY_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("spy_biomes"));
    public static final TagKey<Biome> MERC_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("merc_biomes"));
    public static final TagKey<Biome> TECHNOLESSER_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("technolesser_biomes"));
    public static final TagKey<Biome> TECHNOGREATER_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("technogreater_biomes"));
    public static final ResourceKey<CreativeModeTab> WeaponItemGroup = ResourceKey.create(Registries.CREATIVE_MODE_TAB, HWGMod.modResource("weapons"));
    public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, HWGMod.modResource("gun_table"), new GunTableRecipe.Serializer());
    protected final List<String> chests = List.of("jungle_temple", "underwater_ruin_big", "buried_treasure", "bastion_other", "nether_bridge", "ruined_portal", "stronghold_library", "bastion_bridge", "underwater_ruin_small", "stronghold_corridor", "stronghold_crossing", "bastion_treasure", "spawn_bonus_chest", "bastion_hoglin_stable");

    @Override
    public void onInitialize() {
        config = AzureLibMod.registerConfig(HWGConfig.class, ConfigFormats.json()).getConfigInstance();
        HWGItems.initialize();
        HWGBlocks.initialize();
        HWGSounds.initialize();
        HWGMobs.initialize();
        HWGParticles.initialize();
        HWGProjectiles.initialize();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WeaponItemGroup, FabricItemGroup.builder().icon(() -> new ItemStack(HWGItems.AK47)) // icon
                .title(Component.translatable("itemGroup.hwg.weapons")) // title
                .displayItems((context, entries) -> {
                    /**
                     * Weapons
                     */
                    entries.accept(HWGItems.PISTOL);
                    entries.accept(HWGItems.SPISTOL);
                    if (FabricLoader.getInstance().isModLoaded("bewitchment")) entries.accept(HWGItems.SILVERGUN);
                    entries.accept(HWGItems.LUGER);
                    entries.accept(HWGItems.MEANIE1);
                    entries.accept(HWGItems.MEANIE2);
                    entries.accept(HWGItems.GOLDEN_GUN);
                    entries.accept(HWGItems.HELLHORSE);
                    if (FabricLoader.getInstance().isModLoaded("bewitchment")) entries.accept(HWGItems.SILVERHELLHORSE);
                    entries.accept(HWGItems.AK47);
                    entries.accept(HWGItems.SMG);
                    entries.accept(HWGItems.TOMMYGUN);
                    entries.accept(HWGItems.MINIGUN);
                    entries.accept(HWGItems.SHOTGUN);
                    entries.accept(HWGItems.SNIPER);
                    if (FabricLoader.getInstance().isModLoaded("gigeresque")) entries.accept(HWGItems.INCINERATOR);
                    entries.accept(HWGItems.FLAMETHROWER);
                    entries.accept(HWGItems.BALROG);
                    entries.accept(HWGItems.BRIMSTONE);
                    entries.accept(HWGItems.ROCKETLAUNCHER);
                    entries.accept(HWGItems.G_LAUNCHER);
                    entries.accept(HWGItems.FLARE_GUN);
                    /**
                     * Ammo
                     */
                    if (FabricLoader.getInstance().isModLoaded("bewitchment")) entries.accept(HWGItems.SILVERBULLET);
                    entries.accept(HWGItems.BULLETS);
                    entries.accept(HWGItems.SHOTGUN_SHELL);
                    entries.accept(HWGItems.SNIPER_ROUND);
                    entries.accept(HWGItems.ROCKET);
                    entries.accept(HWGItems.G_FRAG);
                    entries.accept(HWGItems.G_STUN);
                    entries.accept(HWGItems.G_SMOKE);
                    entries.accept(HWGItems.G_NAPALM);
                    entries.accept(HWGItems.G_EMP);
                    entries.accept(HWGItems.RED_FLARE);
                    entries.accept(HWGItems.BLUE_FLARE);
                    entries.accept(HWGItems.CYAN_FLARE);
                    entries.accept(HWGItems.GRAY_FLARE);
                    entries.accept(HWGItems.LIME_FLARE);
                    entries.accept(HWGItems.PINK_FLARE);
                    entries.accept(HWGItems.BLACK_FLARE);
                    entries.accept(HWGItems.BROWN_FLARE);
                    entries.accept(HWGItems.GREEN_FLARE);
                    entries.accept(HWGItems.WHITE_FLARE);
                    entries.accept(HWGItems.ORANGE_FLARE);
                    entries.accept(HWGItems.PURPLE_FLARE);
                    entries.accept(HWGItems.YELLOW_FLARE);
                    entries.accept(HWGItems.MAGENTA_FLARE);
                    entries.accept(HWGItems.LIGHTBLUE_FLARE);
                    entries.accept(HWGItems.LIGHTGRAY_FLARE);
                    /**
                     * Blocks
                     */
                    entries.accept(HWGItems.FUEL_TANK);
                    entries.accept(HWGItems.GUN_TABLE);
                    /**
                     * Spawn Eggs
                     */
                    entries.accept(HWGItems.MERC_SPAWN_EGG);
                    entries.accept(HWGItems.SPY_SPAWN_EGG);
                    entries.accept(HWGItems.LESSER_SPAWN_EGG);
                    entries.accept(HWGItems.GREATER_SPAWN_EGG);
                }).build()); // build() no longer registers by itself
        GunSmithProfession.init();
        MobSpawn.addSpawnEntries();
        MobAttributes.init();
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (HWGMod.chestResource(chests.get(this.random.nextInt(chests.size()))).equals(id))
                supplier.pool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).with(LootItem.lootTableItem(HWGItems.MEANIE1).build()).with(LootItem.lootTableItem(HWGItems.MEANIE2).build()).build());
        });
        SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET);
        Registry.register(BuiltInRegistries.MENU, HWGMod.modResource("guntable_screen_type"), SCREEN_HANDLER_TYPE);
        PacketHandler.registerMessages();
        if (FabricLoader.getInstance().isModLoaded("gigeresque"))
            FabricLoader.getInstance().getModContainer(HWGMod.MODID).ifPresent((modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                    // Mod Compat datapack found in resources/resourcepacks
                    HWGMod.modResource("gigcompat"), modContainer, Component.literal("gigcompat"), ResourcePackActivationType.DEFAULT_ENABLED)));
        if (FabricLoader.getInstance().isModLoaded("bewitchment"))
            FabricLoader.getInstance().getModContainer(HWGMod.MODID).ifPresent((modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                    // Mod Compat datapack found in resources/resourcepacks
                    HWGMod.modResource("bewitchmentcompat"), modContainer, Component.literal("bewitchmentcompat"), ResourcePackActivationType.DEFAULT_ENABLED)));
    }

    public static final ResourceLocation chestResource(String name) {
        return new ResourceLocation("minecraft", "chests/" + name);
    }

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MODID, name);
    }
}