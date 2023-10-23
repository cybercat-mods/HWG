package mod.azure.hwg;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.compat.BWCompat;
import mod.azure.hwg.compat.GigCompat;
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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class HWGMod implements ModInitializer {

    public static final String MODID = "hwg";
    public static final ResourceLocation LUGER = new ResourceLocation(MODID, "luger");
    public static final ResourceLocation HELL = new ResourceLocation(MODID, "hellgun");
    public static final ResourceLocation ASSASULT = new ResourceLocation(MODID, "smg");
    public static final ResourceLocation BALROG = new ResourceLocation(MODID, "balrog");
    public static final ResourceLocation PISTOL = new ResourceLocation(MODID, "pistol");
    public static final ResourceLocation SNIPER = new ResourceLocation(MODID, "sniper");
    public static final ResourceLocation ASSASULT1 = new ResourceLocation(MODID, "smg1");
    public static final ResourceLocation ASSASULT2 = new ResourceLocation(MODID, "smg2");
    public static final ResourceLocation GPISTOL = new ResourceLocation(MODID, "gpistol");
    public static final ResourceLocation MEANIE1 = new ResourceLocation(MODID, "meanie1");
    public static final ResourceLocation MEANIE2 = new ResourceLocation(MODID, "meanie2");
    public static final ResourceLocation MINIGUN = new ResourceLocation(MODID, "minigun");
    public static final ResourceLocation SHOTGUN = new ResourceLocation(MODID, "shotgun");
    public static final ResourceLocation SPISTOL = new ResourceLocation(MODID, "spistol");
    public static final ResourceLocation GUNS = new ResourceLocation(MODID, "crafting_guns");
    public static final ResourceLocation BRIMSTONE = new ResourceLocation(MODID, "brimstone");
    public static final ResourceLocation SILVERGUN = new ResourceLocation(MODID, "silvergun");
    public static final ResourceLocation SILVERHELL = new ResourceLocation(MODID, "silverhell");
    public static final ResourceLocation FLAMETHOWER = new ResourceLocation(MODID, "flamethrower");
    public static final ResourceLocation GUNSMITH_POI = new ResourceLocation(MODID, "gun_smith_poi");
    public static final ResourceLocation GUN_TABLE_GUI = new ResourceLocation(MODID, "gun_table_gui");
    public static final ResourceLocation ROCKETLAUNCHER = new ResourceLocation(MODID, "rocketlauncher");
    public static final TagKey<Biome> SPY_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("spy_biomes"));
    public static final TagKey<Biome> MERC_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("merc_biomes"));
    public static final TagKey<Biome> TECHNOLESSER_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("technolesser_biomes"));
    public static final TagKey<Biome> TECHNOGREATER_BIOMES = TagKey.create(Registries.BIOME, HWGMod.modResource("technogreater_biomes"));
    public static final ResourceKey<CreativeModeTab> WeaponItemGroup = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MODID, "weapons"));
    public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(MODID, "gun_table"), new GunTableRecipe.Serializer());
    public static HWGMobs MOBS;
    public static HWGItems ITEMS;
    public static HWGBlocks BLOCKS;
    public static HWGConfig config;
    public static HWGSounds SOUNDS;
    public static BWCompat BW_ITEMS;
    public static GigCompat GIG_ITEMS;
    public static HWGParticles PARTICLES;
    public static HWGProjectiles PROJECTILES;
    public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MODID, name);
    }

    @Override
    public void onInitialize() {
        config = AzureLibMod.registerConfig(HWGConfig.class, ConfigFormats.json()).getConfigInstance();
        ITEMS = new HWGItems();
        if (FabricLoader.getInstance().isModLoaded("gigeresque"))
            GIG_ITEMS = new GigCompat();
        if (FabricLoader.getInstance().isModLoaded("bewitchment"))
            BW_ITEMS = new BWCompat();
        BLOCKS = new HWGBlocks();
        SOUNDS = new HWGSounds();
        MOBS = new HWGMobs();
        PARTICLES = new HWGParticles();
        PROJECTILES = new HWGProjectiles();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WeaponItemGroup, FabricItemGroup.builder().icon(() -> new ItemStack(HWGItems.AK47)) // icon
                .title(Component.translatable("itemGroup.hwg.weapons")) // title
                .displayItems((context, entries) -> {
                    // Weapons
                    entries.accept(HWGItems.PISTOL);
                    entries.accept(HWGItems.SPISTOL);
                    if (FabricLoader.getInstance().isModLoaded("bewitchment"))
                        entries.accept(BWCompat.SILVERGUN);
                    entries.accept(HWGItems.LUGER);
                    entries.accept(HWGItems.MEANIE1);
                    entries.accept(HWGItems.MEANIE2);
                    entries.accept(HWGItems.GOLDEN_GUN);
                    entries.accept(HWGItems.HELLHORSE);
                    if (FabricLoader.getInstance().isModLoaded("bewitchment"))
                        entries.accept(BWCompat.SILVERHELLHORSE);
                    entries.accept(HWGItems.AK47);
                    entries.accept(HWGItems.SMG);
                    entries.accept(HWGItems.TOMMYGUN);
                    entries.accept(HWGItems.MINIGUN);
                    entries.accept(HWGItems.SHOTGUN);
                    entries.accept(HWGItems.SNIPER);
                    if (FabricLoader.getInstance().isModLoaded("gigeresque"))
                        entries.accept(GigCompat.INCINERATOR);
                    entries.accept(HWGItems.FLAMETHROWER);
                    entries.accept(HWGItems.BALROG);
                    entries.accept(HWGItems.BRIMSTONE);
                    entries.accept(HWGItems.ROCKETLAUNCHER);
                    entries.accept(HWGItems.G_LAUNCHER);
                    entries.accept(HWGItems.FLARE_GUN);
                    // Ammo
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
                    // Blocks
                    entries.accept(HWGItems.FUEL_TANK);
                    entries.accept(HWGItems.GUN_TABLE);
                    // Spawn Eggs
                    entries.accept(HWGItems.MERC_SPAWN_EGG);
                    entries.accept(HWGItems.SPY_SPAWN_EGG);
                    entries.accept(HWGItems.LESSER_SPAWN_EGG);
                    entries.accept(HWGItems.GREATER_SPAWN_EGG);
                }).build()); // build() no longer registers by itself
        GunSmithProfession.init();
        MobSpawn.addSpawnEntries();
        MobAttributes.init();
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (HWGLoot.B_TREASURE.equals(id) || HWGLoot.JUNGLE.equals(id) || HWGLoot.U_BIG.equals(id) || HWGLoot.S_LIBRARY.equals(id) || HWGLoot.U_SMALL.equals(id) || HWGLoot.S_CORRIDOR.equals(id) || HWGLoot.S_CROSSING.equals(id) || HWGLoot.SPAWN_BONUS_CHEST.equals(id)) {
                final LootPool poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).with(LootItem.lootTableItem(HWGItems.MEANIE1).build()).with(LootItem.lootTableItem(HWGItems.MEANIE2).build()).build();
                supplier.pool(poolBuilder);
            }
        });
        SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MODID, "guntable_screen_type"), SCREEN_HANDLER_TYPE);
        PacketHandler.registerMessages();
    }
}