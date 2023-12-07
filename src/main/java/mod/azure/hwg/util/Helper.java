package mod.azure.hwg.util;

import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.platform.Services;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.*;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class Helper {

    private Helper() {
    }

    public static void setOnFire(Entity projectile) {
        if (projectile.isOnFire())
            projectile.level().getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(2)).forEach(e -> {
                if (e.isAlive() && !(e instanceof Player)) e.setRemainingFireTicks(90);
            });
    }

    /**
     * Removes matching item from offhand first then checks inventory for item
     *
     * @param ammo         Item you want to be used as ammo
     * @param playerEntity Player whose inventory is being checked.
     */
    public static void removeAmmo(Item ammo, Player playerEntity) {
        if (playerEntity.getItemInHand(playerEntity.getUsedItemHand()).getItem() instanceof AzureAnimatedGunItem && !playerEntity.isCreative()) { // Creative mode reloading breaks things
            for (var item : playerEntity.getInventory().offhand) {
                if (item.getItem() == ammo) {
                    item.shrink(1);
                    break;
                }
                for (var item1 : playerEntity.getInventory().items) {
                    if (item1.getItem() == ammo) {
                        item1.shrink(1);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Call wherever you are firing weapon to place the half tick light-block, making sure do so only on the server.
     *
     * @param entity         Usually the player or mob that is using the weapon
     * @param isInWaterBlock Checks if it's in a water block to refresh faster.
     */
    public static void spawnLightSource(Entity entity, Boolean isInWaterBlock) {
        BlockPos lightBlockPos = null;
        if (lightBlockPos == null) {
            lightBlockPos = findFreeSpace(entity.level(), entity.blockPosition());
            if (lightBlockPos == null) return;
            entity.level().setBlockAndUpdate(lightBlockPos, Services.PLATFORM.getTickingLightBlock().defaultBlockState());
        } else if (checkDistance(lightBlockPos, entity.blockPosition())) {
            var blockEntity = entity.level().getBlockEntity(lightBlockPos);
            if (blockEntity instanceof TickingLightEntity tickingLightEntity)
                tickingLightEntity.refresh(Boolean.TRUE.equals(isInWaterBlock) ? 20 : 0);
        }
    }

    private static boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB) {
        return Math.abs(blockPosA.getX() - blockPosB.getX()) <= 2 && Math.abs(blockPosA.getY() - blockPosB.getY()) <= 2 && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= 2;
    }

    private static BlockPos findFreeSpace(Level world, BlockPos blockPos) {
        if (blockPos == null) return null;

        var offsets = new int[2 * 2 + 1];
        offsets[0] = 0;
        for (int i = 2; i <= 2 * 2; i += 2) {
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

    public static EntityHitResult hitscanTrace(Player player, double range, float ticks) {
        var look = player.getViewVector(ticks);
        var start = player.getEyePosition(ticks);
        var end = new Vec3(player.getX() + look.x * range, player.getEyeY() + look.y * range, player.getZ() + look.z * range);
        var traceDistance = player.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)).getLocation().distanceToSqr(end);
        for (var possible : player.level().getEntities(player, player.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(3.0D, 3.0D, 3.0D), (entity -> !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity))) {
            var clip = possible.getBoundingBox().inflate(0.3D).clip(start, end);
            if (clip.isPresent() && start.distanceToSqr(clip.get()) < traceDistance)
                return ProjectileUtil.getEntityHitResult(player.level(), player, start, end, player.getBoundingBox().expandTowards(look.scale(traceDistance)).inflate(3.0D, 3.0D, 3.0D), target -> !target.isSpectator() && player.isAttackable() && player.hasLineOfSight(target));
        }
        return null;
    }

    public static BulletEntity createBullet(Level worldIn, LivingEntity shooter, float damage) {
        return new BulletEntity(worldIn, shooter, damage);
    }

    public static BlazeRodEntity createBlazeRod(Level worldIn, LivingEntity shooter) {
        return new BlazeRodEntity(worldIn, shooter);
    }

    public static FireballEntity createFireball(Level worldIn, LivingEntity shooter) {
        return new FireballEntity(worldIn, shooter);
    }

    public static FlameFiring createFlame(Level worldIn, LivingEntity shooter) {
        return new FlameFiring(worldIn, shooter);
    }

    public static MBulletEntity createMeanieBullet(Level worldIn, LivingEntity shooter) {
        return new MBulletEntity(worldIn, shooter);
    }

    public static ShellEntity createShell(Level worldIn, LivingEntity shooter) {
        return new ShellEntity(worldIn, shooter);
    }

    public static RocketEntity createRocket(Level worldIn, LivingEntity shooter) {
        return new RocketEntity(worldIn, shooter);
    }

    public static SBulletEntity createSilverBullet(Level worldIn, LivingEntity shooter) {
        return new SBulletEntity(worldIn, shooter, HWGMod.config.gunconfigs.hellhorseconfigs.hellhorse_damage);
    }
}
