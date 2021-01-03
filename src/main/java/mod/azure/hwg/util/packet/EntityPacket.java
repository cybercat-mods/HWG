package mod.azure.hwg.util.packet;

import io.netty.buffer.Unpooled;
import mod.azure.hwg.HWGMod;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("deprecation")
public class EntityPacket {
	public static final Identifier ID = new Identifier(HWGMod.MODID, "spawn_entity");

	public static Packet<?> createPacket(Entity entity) {
		PacketByteBuf buf = createBuffer();
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
		buf.writeUuid(entity.getUuid());
		buf.writeVarInt(entity.getEntityId());
		buf.writeDouble(entity.getX());
		buf.writeDouble(entity.getY());
		buf.writeDouble(entity.getZ());
		buf.writeByte(MathHelper.floor(entity.pitch * 256.0F / 360.0F));
		buf.writeByte(MathHelper.floor(entity.yaw * 256.0F / 360.0F));
		buf.writeFloat(entity.pitch);
		buf.writeFloat(entity.yaw);
		return ServerSidePacketRegistry.INSTANCE.toPacket(ID, buf);
	}

	private static PacketByteBuf createBuffer() {
		return new PacketByteBuf(Unpooled.buffer());
	}
}
