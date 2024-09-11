package mod.azure.hwg.network;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import net.minecraft.network.FriendlyByteBuf;

public class FiringPacket {
    public FiringPacket() {
    }

    public static FiringPacket decode(FriendlyByteBuf buf) {
        return new FiringPacket();
    }

    public static void handle(PacketContext<FiringPacket> ctx) {
        if (Side.SERVER.equals(ctx.side()) && ctx.sender().getMainHandItem().getItem() instanceof AzureAnimatedGunItem)
            AzureAnimatedGunItem.shoot(ctx.sender());
    }

    public void encode(FriendlyByteBuf buf) {

    }
}
