package mod.azure.hwg.util;

import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class Helper {
    private static BlockPos lightBlockPos = null;
    public static void setOnFire(Entity projectile) {
        if (projectile.isOnFire())
            projectile.level().getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(2)).forEach(e -> {
                if (e.isAlive())
                    e.setRemainingFireTicks(90);
            });
    }

    public static void spawnLightSource(Entity entity, Boolean isInWaterBlock) {
        if (lightBlockPos == null) {
            lightBlockPos = findFreeSpace(entity.level(), entity.blockPosition(), 2);
            if (lightBlockPos == null)
                return;
            entity.level().setBlockAndUpdate(lightBlockPos, Services.PLATFORM.getTickingLightBlock().defaultBlockState());
        } else if (checkDistance(lightBlockPos, entity.blockPosition(), 2)) {
            var blockEntity = entity.level().getBlockEntity(lightBlockPos);
            if (blockEntity instanceof TickingLightEntity tickingLightEntity)
                tickingLightEntity.refresh(isInWaterBlock ? 20 : 0);
            else
                lightBlockPos = null;
        } else
            lightBlockPos = null;
    }

    private static boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
        return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
    }

    private static BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
        if (blockPos == null)
            return null;

        var offsets = new int[maxDistance * 2 + 1];
        offsets[0] = 0;
        for (int i = 2; i <= maxDistance * 2; i += 2) {
            offsets[i - 1] = i / 2;
            offsets[i] = -i / 2;
        }
        for (int x : offsets)
            for (int y : offsets)
                for (int z : offsets) {
                    var offsetPos = blockPos.offset(x, y, z);
                    var state = world.getBlockState(offsetPos);
                    if (state.isAir() || state.getBlock().equals(Services.PLATFORM.getTickingLightBlock()))
                        return offsetPos;
                }
        return null;
    }
}
