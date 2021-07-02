package mod.azure.hwg;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunBlockEntity;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.MobAttributes;
import mod.azure.hwg.util.MobSpawn;
import mod.azure.hwg.util.recipes.GunTableRecipe;
import mod.azure.hwg.util.registry.BWCompatItems;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGSounds;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class HWGMod implements ModInitializer {

	public static HWGMobs MOBS;
	public static HWGItems ITEMS;
	public static HWGConfig config;
	public static HWGSounds SOUNDS;
	public static BWCompatItems BWITEMS;
	public static HWGParticles PARTICLES;
	public static final String MODID = "hwg";
	public static ProjectilesEntityRegister PROJECTILES;
	public static final Block FUEL_TANK = new FuelTankBlock();
	public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
	public static final Identifier LUGER = new Identifier(MODID, "luger");
	public static final Identifier HELL = new Identifier(MODID, "hellgun");
	public static final Identifier ASSASULT = new Identifier(MODID, "smg");
	public static final Identifier BALROG = new Identifier(MODID, "balrog");
	public static final Identifier PISTOL = new Identifier(MODID, "pistol");
	public static final Identifier SNIPER = new Identifier(MODID, "sniper");
	public static final Identifier MEANIE1 = new Identifier(MODID, "meanie");
	public static final Identifier MEANIE2 = new Identifier(MODID, "meanie");
	public static final Identifier ASSASULT1 = new Identifier(MODID, "smg1");
	public static final Identifier ASSASULT2 = new Identifier(MODID, "smg2");
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
	public static final GunTableBlock GUN_TABLE = new GunTableBlock(
			FabricBlockSettings.of(Material.METAL).strength(4.0f).nonOpaque());
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE = ScreenHandlerRegistry
			.registerSimple(GUN_TABLE_GUI, GunTableScreenHandler::new);
	public static final ItemGroup WeaponItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(HWGItems.AK47)).build();
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry
			.register(Registry.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		AutoConfig.register(HWGConfig.class, Toml4jConfigSerializer::new);
		config = AutoConfig.getConfigHolder(HWGConfig.class).getConfig();
		Registry.register(Registry.BLOCK, new Identifier(MODID, "fuel_tank"), FUEL_TANK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "gun_table"), GUN_TABLE);
		ITEMS = new HWGItems();
		SOUNDS = new HWGSounds();
//		if (FabricLoader.getInstance().isModLoaded("bewitchment")) {
//			BWITEMS = new BWCompatItems();
//			LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
//				if (HWGLoot.BASTION_BRIDGE.equals(id) || HWGLoot.BASTION_HOGLIN_STABLE.equals(id)
//						|| HWGLoot.BASTION_OTHER.equals(id) || HWGLoot.BASTION_TREASURE.equals(id)
//						|| HWGLoot.NETHER_BRIDGE.equals(id) || HWGLoot.RUINED_PORTAL.equals(id)) {
//					FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
//							.rolls(ConstantLootTableRange.create(1)).withEntry(ItemEntry.builder(BWCompatItems.SILVERHELLHORSE).build());
//					supplier.pool(poolBuilder);
//				}
//			});
//		}
		MOBS = new HWGMobs();
		PARTICLES = new HWGParticles();
		PROJECTILES = new ProjectilesEntityRegister();
		GeckoLib.initialize();
		GunSmithProfession.init();
		MobSpawn.addSpawnEntries();
		GUN_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":guntable",
				FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, GUN_TABLE).build(null));
		MobAttributes.init();
//		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
//			if (HWGLoot.B_TREASURE.equals(id) || HWGLoot.JUNGLE.equals(id) || HWGLoot.U_BIG.equals(id)
//					|| HWGLoot.S_LIBRARY.equals(id) || HWGLoot.U_SMALL.equals(id) || HWGLoot.S_CORRIDOR.equals(id)
//					|| HWGLoot.S_CROSSING.equals(id) || HWGLoot.SPAWN_BONUS_CHEST.equals(id)) {
//				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
//						.rolls(ConstantLootTableRange.create(1)).withEntry(ItemEntry.builder(HWGItems.MEANIE1).build())
//						.withEntry(ItemEntry.builder(HWGItems.MEANIE2).build());
//				supplier.pool(poolBuilder);
//			}
//		});
		PacketHandler.registerMessages();
	}
}