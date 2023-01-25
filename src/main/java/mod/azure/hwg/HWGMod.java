package mod.azure.hwg;

import eu.midnightdust.lib.config.MidnightConfig;
import mod.azure.azurelib.AzureLib;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.compat.BWCompat;
import mod.azure.hwg.compat.GigCompat;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.MobAttributes;
import mod.azure.hwg.util.MobSpawn;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import mod.azure.hwg.util.registry.HWGBlocks;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGLoot;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGSounds;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class HWGMod implements ModInitializer {

	public static HWGMobs MOBS;
	public static HWGItems ITEMS;
	public static HWGBlocks BLOCKS;
	public static HWGSounds SOUNDS;
	public static BWCompat BW_ITEMS;
	public static GigCompat GIG_ITEMS;
	public static HWGParticles PARTICLES;
	public static final String MODID = "hwg";
	public static ProjectilesEntityRegister PROJECTILES;
	public static final Identifier LUGER = new Identifier(MODID, "luger");
	public static final Identifier HELL = new Identifier(MODID, "hellgun");
	public static final Identifier ASSASULT = new Identifier(MODID, "smg");
	public static final Identifier BALROG = new Identifier(MODID, "balrog");
	public static final Identifier PISTOL = new Identifier(MODID, "pistol");
	public static final Identifier SNIPER = new Identifier(MODID, "sniper");
	public static final Identifier ASSASULT1 = new Identifier(MODID, "smg1");
	public static final Identifier ASSASULT2 = new Identifier(MODID, "smg2");
	public static final Identifier MEANIE1 = new Identifier(MODID, "meanie1");
	public static final Identifier MEANIE2 = new Identifier(MODID, "meanie2");
	public static final Identifier MINIGUN = new Identifier(MODID, "minigun");
	public static final Identifier SHOTGUN = new Identifier(MODID, "shotgun");
	public static final Identifier SPISTOL = new Identifier(MODID, "spistol");
	public static final Identifier GUNS = new Identifier(MODID, "crafting_guns");
	public static final Identifier BRIMSTONE = new Identifier(MODID, "brimstone");
	public static final Identifier SILVERGUN = new Identifier(MODID, "silvergun");
	public static final Identifier SILVERHELL = new Identifier(MODID, "silverhell");
	public static final Identifier FLAMETHOWER = new Identifier(MODID, "flamethrower");
	public static final Identifier GUNSMITH_POI = new Identifier(MODID, "gun_smith_poi");
	public static final Identifier GUN_TABLE_GUI = new Identifier(MODID, "gun_table_gui");
	public static final Identifier ROCKETLAUNCHER = new Identifier(MODID, "rocketlauncher");
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
	public static final ItemGroup WeaponItemGroup = FabricItemGroup.builder(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(HWGItems.AK47)).entries((enabledFeatures, entries, operatorEnabled) -> {
				// Weapons
				entries.add(HWGItems.PISTOL);
				entries.add(HWGItems.SPISTOL);
				if (FabricLoader.getInstance().isModLoaded("bewitchment"))
					entries.add(BWCompat.SILVERGUN);
				entries.add(HWGItems.LUGER);
				entries.add(HWGItems.MEANIE1);
				entries.add(HWGItems.MEANIE2);
				entries.add(HWGItems.GOLDEN_GUN);
				entries.add(HWGItems.HELLHORSE);
				if (FabricLoader.getInstance().isModLoaded("bewitchment"))
					entries.add(BWCompat.SILVERHELLHORSE);
				entries.add(HWGItems.AK47);
				entries.add(HWGItems.SMG);
				entries.add(HWGItems.TOMMYGUN);
				entries.add(HWGItems.MINIGUN);
				entries.add(HWGItems.SHOTGUN);
				entries.add(HWGItems.SNIPER);
				if (FabricLoader.getInstance().isModLoaded("gigeresque"))
					entries.add(GigCompat.INCINERATOR);
				entries.add(HWGItems.FLAMETHROWER);
				entries.add(HWGItems.BALROG);
				entries.add(HWGItems.BRIMSTONE);
				entries.add(HWGItems.ROCKETLAUNCHER);
				entries.add(HWGItems.G_LAUNCHER);
				entries.add(HWGItems.FLARE_GUN);
				// Ammo
				entries.add(HWGItems.BULLETS);
				entries.add(HWGItems.SNIPER_ROUND);
				entries.add(HWGItems.ROCKET);
				entries.add(HWGItems.G_FRAG);
				entries.add(HWGItems.G_STUN);
				entries.add(HWGItems.G_SMOKE);
				entries.add(HWGItems.G_NAPALM);
				entries.add(HWGItems.G_EMP);
				entries.add(HWGItems.RED_FLARE);
				entries.add(HWGItems.BLUE_FLARE);
				entries.add(HWGItems.CYAN_FLARE);
				entries.add(HWGItems.GRAY_FLARE);
				entries.add(HWGItems.LIME_FLARE);
				entries.add(HWGItems.PINK_FLARE);
				entries.add(HWGItems.BLACK_FLARE);
				entries.add(HWGItems.BROWN_FLARE);
				entries.add(HWGItems.GREEN_FLARE);
				entries.add(HWGItems.WHITE_FLARE);
				entries.add(HWGItems.ORANGE_FLARE);
				entries.add(HWGItems.PURPLE_FLARE);
				entries.add(HWGItems.YELLOW_FLARE);
				entries.add(HWGItems.MAGENTA_FLARE);
				entries.add(HWGItems.LIGHTBLUE_FLARE);
				entries.add(HWGItems.LIGHTGRAY_FLARE);
				// Blocks
				entries.add(HWGItems.FUEL_TANK);
				entries.add(HWGItems.GUN_TABLE);
				// Spawn Eggs
				entries.add(HWGItems.MERC_SPAWN_EGG);
				entries.add(HWGItems.SPY_SPAWN_EGG);
				entries.add(HWGItems.LESSER_SPAWN_EGG);
				entries.add(HWGItems.GREATER_SPAWN_EGG);
			}).build();
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(
			Registries.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		MidnightConfig.init(MODID, HWGConfig.class);
		ITEMS = new HWGItems();
		if (FabricLoader.getInstance().isModLoaded("gigeresque"))
			GIG_ITEMS = new GigCompat();
		if (FabricLoader.getInstance().isModLoaded("bewitchment"))
			BW_ITEMS = new BWCompat();
		BLOCKS = new HWGBlocks();
		SOUNDS = new HWGSounds();
		MOBS = new HWGMobs();
		PARTICLES = new HWGParticles();
		PROJECTILES = new ProjectilesEntityRegister();
		AzureLib.initialize();
		GunSmithProfession.init();
		MobSpawn.addSpawnEntries();
		MobAttributes.init();
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (HWGLoot.B_TREASURE.equals(id) || HWGLoot.JUNGLE.equals(id) || HWGLoot.U_BIG.equals(id)
					|| HWGLoot.S_LIBRARY.equals(id) || HWGLoot.U_SMALL.equals(id) || HWGLoot.S_CORRIDOR.equals(id)
					|| HWGLoot.S_CROSSING.equals(id) || HWGLoot.SPAWN_BONUS_CHEST.equals(id)) {
				LootPool poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
						.with(ItemEntry.builder(HWGItems.MEANIE1).build())
						.with(ItemEntry.builder(HWGItems.MEANIE2).build()).build();
				supplier.pool(poolBuilder);
			}
		});
		SCREEN_HANDLER_TYPE = new ScreenHandlerType<>(GunTableScreenHandler::new);
		Registry.register(Registries.SCREEN_HANDLER, new Identifier(MODID, "guntable_screen_type"),
				SCREEN_HANDLER_TYPE);
		PacketHandler.registerMessages();
	}
}