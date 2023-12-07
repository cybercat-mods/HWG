package mod.azure.hwg.network;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

public record PacketHandler() {

    public static final ResourceLocation lockSlot = HWGMod.modResource("select_craft");
    public static final ResourceLocation reloadGun = HWGMod.modResource("reload");
    public static final ResourceLocation shootGun = HWGMod.modResource("shoot");

    public static void registerMessages() {
        ServerPlayNetworking.registerGlobalReceiver(lockSlot, new C2SMessageSelectCraft());
        ServerPlayNetworking.registerGlobalReceiver(reloadGun, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof AzureAnimatedGunItem)
                AzureAnimatedGunItem.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(shootGun, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof AzureAnimatedGunItem)
                AzureAnimatedGunItem.shoot(player);
        });
    }
}
