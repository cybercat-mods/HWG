package mod.azure.hwg;

import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunBlockEntity;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.RocketLauncher;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.HWGMobs;
import mod.azure.hwg.util.MobAttributes;
import mod.azure.hwg.util.MobSpawn;
import mod.azure.hwg.util.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class HWGMod implements ModInitializer {

	public static HWGItems ITEMS;
	public static ProjectilesEntityRegister PROJECTILES;
	public static HWGMobs MOBS;
	public static ScreenHandlerType<ScreenHandler> SCREEN_HANDLER_TYPE;
	public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
	public static final Block FUEL_TANK = new FuelTankBlock();
	public static final GunTableBlock GUN_TABLE = new GunTableBlock(
			FabricBlockSettings.of(Material.METAL).strength(4.0f));
	public static final String MODID = "hwg";
	public static final ItemGroup WeaponItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(HWGItems.PISTOL)).build();
	public static final Identifier GUN_TABLE_GUI = new Identifier(MODID, "gun_table_gui");
	public static final Identifier PISTOL = new Identifier(MODID, "pistol");
	public static final Identifier SPISTOL = new Identifier(MODID, "spistol");
	public static final Identifier FLAMETHOWER = new Identifier(MODID, "flamethrower");
	public static final Identifier ROCKETLAUNCHER = new Identifier(MODID, "rocketlauncher");

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(MODID, "fuel_tank"), FUEL_TANK);
		ITEMS = new HWGItems();
		MOBS = new HWGMobs();
		PROJECTILES = new ProjectilesEntityRegister();
		// MobAttributes.init();
		GeckoLib.initialize();
		MobSpawn.addSpawnEntries();
//		SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(GUN_TABLE_GUI, (syncId, inventory) -> new GunTableDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
//        Registry.register(Registry.BLOCK, new Identifier(MODID, "gun_table"), GUN_TABLE);
//        Registry.register(Registry.ITEM, new Identifier(MODID, "gun_table"), new BlockItem(GUN_TABLE, new FabricItemSettings().group(ItemGroup.MISC)));
//		GUN_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hwg:guntable",
//				BlockEntityType.Builder.create(GunBlockEntity::new, GUN_TABLE).build(null));
		MobAttributes.init();
		RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, id, biome) -> {
			MobSpawn.addSpawnEntries();
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
	}
}