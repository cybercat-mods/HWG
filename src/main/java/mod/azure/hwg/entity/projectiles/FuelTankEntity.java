package mod.azure.hwg.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class FuelTankEntity extends Entity {

	@Nullable
	private LivingEntity causingEntity;

	public FuelTankEntity(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true,
				Level.ExplosionInteraction.NONE);
	}

	public FuelTankEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(HWGMobs.FUELTANK, worldIn);
		this.absMoveTo(x, y, z);
		double d = level.random.nextDouble() * 6.2831854820251465D;
		this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.causingEntity = igniter;
	}

	@Nullable
	public LivingEntity getCausingEntity() {
		return this.causingEntity;
	}

	@Override
	protected void defineSynchedData() {
	}

	public void tick() {
		this.remove(Entity.RemovalReason.DISCARDED);
		if (!this.level.isClientSide) {
			this.explode();
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

}