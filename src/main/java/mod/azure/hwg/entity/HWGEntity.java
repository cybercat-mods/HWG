package mod.azure.hwg.entity;

import mod.azure.hwg.util.packet.EntityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class HWGEntity extends HostileEntity {

	protected HWGEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}