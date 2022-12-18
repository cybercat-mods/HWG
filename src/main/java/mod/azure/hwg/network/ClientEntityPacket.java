package mod.azure.hwg.network;

import java.util.UUID;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

public class ClientEntityPacket {
	@Environment(EnvType.CLIENT)
	public static void onPacket(MinecraftClient context, PacketByteBuf byteBuf) {
		EntityType<?> type = Registries.ENTITY_TYPE.get(byteBuf.readVarInt());
		UUID entityUUID = byteBuf.readUuid();
		int entityID = byteBuf.readVarInt();
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		context.execute(() -> {
			ClientWorld world = MinecraftClient.getInstance().world;
			Entity entity = type.create(world);
			if (entity != null) {
				entity.updateTrackedPosition(x, y, z);
				entity.refreshPositionAfterTeleport(x, y, z);
				entity.setPitch((float) (entity.getPitch() * 360) / 256f);
				entity.setYaw((float) (entity.getYaw() * 360) / 256f);
				entity.setId(entityID);
				entity.setUuid(entityUUID);
				world.addEntity(entityID, entity);
			}
		});
	}
}
