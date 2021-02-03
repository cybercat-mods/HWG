package mod.azure.hwg;

import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class HWGMod implements ModInitializer {

	public static HWGItems ITEMS;
	public static ProjectilesEntityRegister PROJECTILES;
	// public static MobEntityRegister MOBS;
	public static final String MODID = "hwg";
	public static final ItemGroup WeaponItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(HWGItems.PISTOL)).build();
	public static final Identifier PISTOL = new Identifier(MODID, "pistol");
	public static final Identifier SPISTOL = new Identifier(MODID, "spistol");

	@Override
	public void onInitialize() {
		ITEMS = new HWGItems();
		// MOBS = new MobEntityRegister();
		PROJECTILES = new ProjectilesEntityRegister();
		// MobAttributes.init();
		GeckoLib.initialize();
		GeoItemRenderer.registerItemRenderer(HWGItems.PISTOL, new PistolRender());
		RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, id, biome) -> {
			// MobSpawn.addSpawnEntries();
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
	}
}