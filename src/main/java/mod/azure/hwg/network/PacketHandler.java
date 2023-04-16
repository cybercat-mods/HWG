package mod.azure.hwg.network;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.Assasult1Item;
import mod.azure.hwg.item.weapons.Assasult2Item;
import mod.azure.hwg.item.weapons.AssasultItem;
import mod.azure.hwg.item.weapons.BalrogItem;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.GPistolItem;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import mod.azure.hwg.item.weapons.LugerItem;
import mod.azure.hwg.item.weapons.Meanie1Item;
import mod.azure.hwg.item.weapons.Meanie2Item;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.RocketLauncher;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.item.weapons.ShotgunItem;
import mod.azure.hwg.item.weapons.SilverGunItem;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import mod.azure.hwg.item.weapons.SniperItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

public class PacketHandler {

	public static final ResourceLocation lock_slot = new ResourceLocation(HWGMod.MODID, "select_craft");

	public static void registerMessages() {
		ServerPlayNetworking.registerGlobalReceiver(lock_slot, new C2SMessageSelectCraft());

		ServerPlayNetworking.registerGlobalReceiver(HWGMod.PISTOL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof PistolItem)
				((PistolItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.GPISTOL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof GPistolItem)
				((GPistolItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SILVERGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof SilverGunItem)
				((SilverGunItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SPISTOL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof SPistolItem)
				((SPistolItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.FLAMETHOWER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof FlamethrowerItem)
				((FlamethrowerItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ROCKETLAUNCHER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof RocketLauncher)
				((RocketLauncher) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.MINIGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof Minigun)
				((Minigun) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.BRIMSTONE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof BrimstoneItem)
				((BrimstoneItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SHOTGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof ShotgunItem)
				((ShotgunItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ASSASULT, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof AssasultItem)
				((AssasultItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ASSASULT1, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof Assasult1Item)
				((Assasult1Item) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.ASSASULT2, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof Assasult2Item)
				((Assasult2Item) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SNIPER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof SniperItem)
				((SniperItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.BALROG, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof BalrogItem)
				((BalrogItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.MEANIE1, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof Meanie1Item)
				((Meanie1Item) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.MEANIE2, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof Meanie2Item)
				((Meanie2Item) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.HELL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof HellhorseRevolverItem)
				((HellhorseRevolverItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.LUGER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof LugerItem)
				((LugerItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
		ServerPlayNetworking.registerGlobalReceiver(HWGMod.SILVERHELL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
			if (player.getMainHandItem().getItem() instanceof SilverRevolverItem)
				((SilverRevolverItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
		});
	}
}
