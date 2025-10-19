package mod.azure.hwg.network;

import commonnetwork.api.Network;
import net.minecraft.resources.ResourceLocation;

import mod.azure.hwg.CommonMod;

public record PacketHandler() {

    public static final ResourceLocation lockSlot = CommonMod.modResource("select_craft");

    public static final ResourceLocation reloadGun = CommonMod.modResource("reload");

    public static void registerMessages() {
        Network.registerPacket(
            lockSlot,
            CraftingPacket.class,
            CraftingPacket::encode,
            CraftingPacket::decode,
            CraftingPacket::handle
        )
            .registerPacket(
                reloadGun,
                ReloadPacket.class,
                ReloadPacket::encode,
                ReloadPacket::decode,
                ReloadPacket::handle
            );
    }
}
