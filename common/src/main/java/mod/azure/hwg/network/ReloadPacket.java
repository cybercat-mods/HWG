package mod.azure.hwg.network;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;

public class ReloadPacket {

    public ReloadPacket() {
    }

    public static ReloadPacket decode(FriendlyByteBuf buf) {
        return new ReloadPacket();
    }

    public static void handle(PacketContext<ReloadPacket> ctx) {
        if (Side.SERVER.equals(ctx.side()) && ctx.sender().getMainHandItem().getItem() instanceof AzureAnimatedGunItem)
            AzureAnimatedGunItem.reload(ctx.sender(), InteractionHand.MAIN_HAND);
    }

    public void encode(FriendlyByteBuf buf) {

    }
}
