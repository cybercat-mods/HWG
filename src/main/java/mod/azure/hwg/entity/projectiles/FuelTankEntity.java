package mod.azure.hwg.entity.projectiles;

import dev.architectury.networking.SpawnEntityPacket;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FuelTankEntity extends Entity {

    public FuelTankEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public FuelTankEntity(Level worldIn, double x, double y, double z) {
        this(HWGMobs.FUELTANK, worldIn);
        this.absMoveTo(x, y, z);
        var d = level().random.nextDouble() * 6.2831854820251465D;
        this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        this.remove(Entity.RemovalReason.DISCARDED);
        if (!this.level().isClientSide) this.explode();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return SpawnEntityPacket.create(this);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

}