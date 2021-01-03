package mod.azure.hwg.client;

import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.packet.EntityPacketOnClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

@SuppressWarnings("deprecation")
public class Clientnit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		RenderRegistry.init();
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
			EntityPacketOnClient.onPacket(ctx, buf);
		});
	}

}