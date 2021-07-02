package mod.azure.hwg.network;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult1Item;
import mod.azure.hwg.item.weapons.Assasult2Item;
import mod.azure.hwg.item.weapons.AssasultItem;
import mod.azure.hwg.item.weapons.BalrogItem;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import mod.azure.hwg.item.weapons.LugerItem;
import mod.azure.hwg.item.weapons.Meanie1Item;
import mod.azure.hwg.item.weapons.Meanie2Item;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.RocketLauncher;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.item.weapons.ShotgunItem;
import mod.azure.hwg.item.weapons.SniperItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PacketHandler {

	public static final Identifier lock_slot = new Identifier(HWGMod.MODID, "select_craft");

	public static void registerMessages() {
		ServerPlayNetworking.registerGlobalReceiver(lock_slot, new C2SMessageSelectCraft());

		ServerPlayNetworking.registerGlobalReceiver(HWGMod.PISTOL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof PistolItem) {
						((PistolItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
//		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SILVERGUN,
//				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//					if (player.getMainHandStack().getItem() instanceof SilverGunItem) {
//						((SilverGunItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//					}
//				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SPISTOL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SPistolItem) {
						((SPistolItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.FLAMETHOWER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof FlamethrowerItem) {
						((FlamethrowerItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ROCKETLAUNCHER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof RocketLauncher) {
						((RocketLauncher) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.MINIGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Minigun) {
						((Minigun) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.BRIMSTONE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BrimstoneItem) {
						((BrimstoneItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof ShotgunItem) {
						((ShotgunItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ASSASULT,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof AssasultItem) {
						((AssasultItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ASSASULT1,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Assasult1Item) {
						((Assasult1Item) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ASSASULT2,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Assasult2Item) {
						((Assasult2Item) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SNIPER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SniperItem) {
						((SniperItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.BALROG,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BalrogItem) {
						((BalrogItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.MEANIE1,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Meanie1Item) {
						((Meanie1Item) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.MEANIE2,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Meanie2Item) {
						((Meanie1Item) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.HELL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof HellhorseRevolverItem) {
						((HellhorseRevolverItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.LUGER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof LugerItem) {
						((LugerItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
//		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SILVERHELL,
//				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//					if (player.getMainHandStack().getItem() instanceof SilverRevolverItem) {
//						((SilverRevolverItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//					}
//				});
	}
}
