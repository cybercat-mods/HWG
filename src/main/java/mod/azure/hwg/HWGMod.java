package mod.azure.hwg;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.MobAttributes;
import mod.azure.hwg.util.MobSpawn;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import mod.azure.hwg.util.registry.BWCompatItems;
import mod.azure.hwg.util.registry.GigCompat;
import mod.azure.hwg.util.registry.HWGBlocks;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGLoot;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGSounds;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class HWGMod implements ModInitializer {

	public static HWGMobs MOBS;
	public static HWGItems ITEMS;
	public static GigCompat GIG_ITEMS;
	public static HWGBlocks BLOCKS;
	public static HWGConfig config;
	public static HWGSounds SOUNDS;
	public static BWCompatItems BWITEMS;
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
	public static final Identifier GUNSMITH = new Identifier(MODID, "gun_smith");
	public static final Identifier BRIMSTONE = new Identifier(MODID, "brimstone");
	public static final Identifier SILVERGUN = new Identifier(MODID, "silvergun");
	public static final Identifier SILVERHELL = new Identifier(MODID, "silverhell");
	public static final Identifier FLAMETHOWER = new Identifier(MODID, "flamethrower");
	public static final Identifier GUNSMITH_POI = new Identifier(MODID, "gun_smith_poi");
	public static final Identifier GUN_TABLE_GUI = new Identifier(MODID, "gun_table_gui");
	public static final Identifier ROCKETLAUNCHER = new Identifier(MODID, "rocketlauncher");
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
	public static final ItemGroup WeaponItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(HWGItems.AK47)).build();
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry
			.register(Registry.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		AutoConfig.register(HWGConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(HWGConfig.class).getConfig();
		ITEMS = new HWGItems();
		if (FabricLoader.getInstance().isModLoaded("gigeresque")) {
			GIG_ITEMS = new GigCompat();
		}
		BLOCKS = new HWGBlocks();
		SOUNDS = new HWGSounds();
		MOBS = new HWGMobs();
		PARTICLES = new HWGParticles();
		PROJECTILES = new ProjectilesEntityRegister();
		GeckoLib.initialize();
		GunSmithProfession.init();
		MobSpawn.addSpawnEntries();
		MobAttributes.init();
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (HWGLoot.B_TREASURE.equals(id) || HWGLoot.JUNGLE.equals(id) || HWGLoot.U_BIG.equals(id)
					|| HWGLoot.S_LIBRARY.equals(id) || HWGLoot.U_SMALL.equals(id) || HWGLoot.S_CORRIDOR.equals(id)
					|| HWGLoot.S_CROSSING.equals(id) || HWGLoot.SPAWN_BONUS_CHEST.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.withEntry(ItemEntry.builder(HWGItems.MEANIE1).build())
						.withEntry(ItemEntry.builder(HWGItems.MEANIE2).build());
				supplier.pool(poolBuilder);
			}
		});
		SCREEN_HANDLER_TYPE = new ScreenHandlerType<>(GunTableScreenHandler::new);
		Registry.register(Registry.SCREEN_HANDLER, new Identifier(MODID, "guntable_screen_type"), SCREEN_HANDLER_TYPE);
		PacketHandler.registerMessages();
	}
}