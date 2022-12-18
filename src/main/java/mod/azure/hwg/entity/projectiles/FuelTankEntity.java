package mod.azure.hwg.entity.projectiles;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.network.HWGEntityPacket;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.world.World;

public class FuelTankEntity extends Entity {

	@Nullable
	private LivingEntity causingEntity;

	public FuelTankEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4.0F, true,
				World.ExplosionSourceType.NONE);
	}

	public FuelTankEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(HWGMobs.FUELTANK, worldIn);
		this.updatePosition(x, y, z);
		double d = world.random.nextDouble() * 6.2831854820251465D;
		this.setVelocity(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
		this.causingEntity = igniter;
	}

	@Nullable
	public LivingEntity getCausingEntity() {
		return this.causingEntity;
	}

	@Override
	protected void initDataTracker() {
	}

	public void tick() {
		this.remove(Entity.RemovalReason.DISCARDED);
		if (!this.world.isClient) {
			this.explode();
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
	}

	@Override
	public Packet<ClientPlayPacketListener> createSpawnPacket() {
		return HWGEntityPacket.createPacket(this);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}