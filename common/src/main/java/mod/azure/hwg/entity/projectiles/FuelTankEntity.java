package mod.azure.hwg.entity.projectiles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.util.registry.HWGMobs;

public class FuelTankEntity extends Entity {

    public FuelTankEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {}

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    public FuelTankEntity(Level worldIn, double x, double y, double z) {
        this(HWGMobs.FUELTANK.get(), worldIn);
        this.absMoveTo(x, y, z);
        var d = level().random.nextDouble() * 6.2831854820251465D;
        this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected void explode() {
        this.level()
            .explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true, Level.ExplosionInteraction.NONE);
    }

    @Override
    public void tick() {
        this.remove(RemovalReason.DISCARDED);
        if (!this.level().isClientSide)
            this.explode();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

}
