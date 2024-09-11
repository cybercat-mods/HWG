package mod.azure.hwg.network;

import commonnetwork.api.Network;
import mod.azure.azurelib.common.internal.common.network.AbstractPacket;
import mod.azure.azurelib.common.platform.Services;
import mod.azure.hwg.CommonMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketHandler() {

    public static final ResourceLocation lockSlot = CommonMod.modResource("select_craft");
    public static final ResourceLocation reloadGun = CommonMod.modResource("reload");
    public static final ResourceLocation shootGun = CommonMod.modResource("shoot");

    public static void registerMessages() {
        Network.registerPacket(lockSlot, CraftingPacket.class, CraftingPacket::encode, CraftingPacket::decode, CraftingPacket::handle)
                .registerPacket(reloadGun, ReloadPacket.class, ReloadPacket::encode, ReloadPacket::decode, ReloadPacket::handle)
                .registerPacket(shootGun, FiringPacket.class, FiringPacket::encode, FiringPacket::decode, FiringPacket::handle);
    }

    private static <B extends FriendlyByteBuf, P extends AbstractPacket> void registerPacket(CustomPacketPayload.Type<P> payloadType, StreamCodec<B, P> codec) {
        Services.NETWORK.registerPacketInternal(payloadType, codec, true);
    }
}
