package mod.azure.hwg;

import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunBlockEntity;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.item.weapons.AssasultItem;
import mod.azure.hwg.item.weapons.BalrogItem;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.Meanietem;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.RocketLauncher;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.item.weapons.ShotgunItem;
import mod.azure.hwg.item.weapons.SniperItem;
import mod.azure.hwg.util.FlareRecipe;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.HWGLoot;
import mod.azure.hwg.util.HWGMobs;
import mod.azure.hwg.util.MobAttributes;
import mod.azure.hwg.util.MobSpawn;
import mod.azure.hwg.util.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class HWGMod implements ModInitializer {

	public static HWGMobs MOBS;
	public static HWGItems ITEMS;
	public static final String MODID = "hwg";
	public static ProjectilesEntityRegister PROJECTILES;
	public static final Block FUEL_TANK = new FuelTankBlock();
	public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
	public static ScreenHandlerType<ScreenHandler> SCREEN_HANDLER_TYPE;
	public static final Identifier ASSASULT = new Identifier(MODID, "smg");
	public static final Identifier BALROG = new Identifier(MODID, "balrog");
	public static final Identifier MEANIE = new Identifier(MODID, "meanie");
	public static final Identifier PISTOL = new Identifier(MODID, "pistol");
	public static final Identifier SNIPER = new Identifier(MODID, "sniper");
	public static final Identifier MINIGUN = new Identifier(MODID, "minigun");
	public static final Identifier SHOTGUN = new Identifier(MODID, "shotgun");
	public static final Identifier SPISTOL = new Identifier(MODID, "spistol");
	public static final Identifier GUNSMITH = new Identifier(MODID, "gun_smith");
	public static final Identifier BRIMSTONE = new Identifier(MODID, "brimstone");
	public static final Identifier FLARES = new Identifier(MODID, "crafting_flares");
	public static final Identifier FLAMETHOWER = new Identifier(MODID, "flamethrower");
	public static final Identifier GUNSMITH_POI = new Identifier(MODID, "gun_smith_poi");
	public static final Identifier GUN_TABLE_GUI = new Identifier(MODID, "gun_table_gui");
	public static final Identifier ROCKETLAUNCHER = new Identifier(MODID, "rocketlauncher");
	public static final GunTableBlock GUN_TABLE = new GunTableBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).nonOpaque());
	public static final ItemGroup WeaponItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "weapons")).icon(() -> new ItemStack(HWGItems.PISTOL)).build();
	public static final SpecialRecipeSerializer<FlareRecipe> FLARE_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, FLARES, new SpecialRecipeSerializer<>(FlareRecipe::new));

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(MODID, "fuel_tank"), FUEL_TANK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "gun_table"), GUN_TABLE);
		ITEMS = new HWGItems();
		MOBS = new HWGMobs();
		PROJECTILES = new ProjectilesEntityRegister();
		GeckoLib.initialize();
		GunSmithProfession.init();
		MobSpawn.addSpawnEntries();
//		SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(GUN_TABLE_GUI, (syncId, inventory) -> new GunTableDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
//        Registry.register(Registry.BLOCK, new Identifier(MODID, "gun_table"), GUN_TABLE);
//		GUN_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hwg:guntable",
//				BlockEntityType.Builder.create(GunBlockEntity::new, GUN_TABLE).build(null));
		MobAttributes.init();
		RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, id, biome) -> {
			MobSpawn.addSpawnEntries();
		});
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (HWGLoot.B_TREASURE.equals(id) || HWGLoot.JUNGLE.equals(id) || HWGLoot.U_BIG.equals(id)
					|| HWGLoot.S_LIBRARY.equals(id) || HWGLoot.U_SMALL.equals(id) || HWGLoot.S_CORRIDOR.equals(id)
					|| HWGLoot.S_CROSSING.equals(id) || HWGLoot.SPAWN_BONUS_CHEST.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootTableRange.create(1)).withEntry(ItemEntry.builder(HWGItems.MEANIE1).build())
						.withEntry(ItemEntry.builder(HWGItems.MEANIE2).build());
				supplier.pool(poolBuilder);
			}
		});
		ServerPlayNetworking.registerGlobalReceiver(PISTOL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof PistolItem) {
						((PistolItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(SPISTOL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SPistolItem) {
						((SPistolItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(FLAMETHOWER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof FlamethrowerItem) {
						((FlamethrowerItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(ROCKETLAUNCHER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof RocketLauncher) {
						((RocketLauncher) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(MINIGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Minigun) {
						((Minigun) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(BRIMSTONE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BrimstoneItem) {
						((BrimstoneItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(SHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof ShotgunItem) {
						((ShotgunItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(ASSASULT,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof AssasultItem) {
						((AssasultItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(SNIPER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SniperItem) {
						((SniperItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(BALROG,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BalrogItem) {
						((BalrogItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(MEANIE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Meanietem) {
						((Meanietem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
	}
}