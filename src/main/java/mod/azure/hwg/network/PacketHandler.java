package mod.azure.hwg.network;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.*;
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
        ServerPlayNetworking.registerGlobalReceiver(HWGMod.MEANIE,
                (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
                    if (player.getMainHandStack().getItem() instanceof Meanietem) {
                        ((Meanietem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
                    }
                });
    }
}
