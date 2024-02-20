package mod.azure.hwg.entity;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.hwg.entity.projectiles.*;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class HWGEntity extends Monster implements GeoEntity, NeutralMob, Enemy {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(HWGEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(HWGEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(HWGEntity.class, EntityDataSerializers.INT);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private UUID targetUuid;

    protected HWGEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        getNavigation().setCanFloat(true);
        noCulling = true;
    }

    public static boolean canNetherSpawn(EntityType<? extends HWGEntity> type, LevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL) return false;
        if (spawnReason != MobSpawnType.CHUNK_GENERATION && spawnReason != MobSpawnType.NATURAL)
            return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
        return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ANGER_TIME, 0);
        entityData.define(STATE, 0);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return entityData.get(ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int ticks) {
        entityData.set(ANGER_TIME, ticks);
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return targetUuid;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        targetUuid = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(random));
    }

    public abstract int getVariants();

    public void shoot() {
        if (!this.level().isClientSide) {
            final var vector3d = getViewVector(1.0F);
            if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof AzureAnimatedGunItem weapon) {
                final var bullet = getProjectile(weapon, weapon.getGunTypeEnum());
                bullet.setPos(this.getX() + vector3d.x * 2, this.getY(0.5), this.getZ() + vector3d.z * 2);
                bullet.shootFromRotation(this, getXRot(), getYRot(), 0.0F, 3.0F, 1.0F);
                if (getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GeoItem geoItem)
                    geoItem.triggerAnim(this, GeoItem.getOrAssignId(getItemBySlot(EquipmentSlot.MAINHAND), (ServerLevel) this.level()), "controller", "firing");
                this.level().playSound(this, blockPosition(), getDefaultAttackSound(weapon.getGunTypeEnum()), SoundSource.HOSTILE, 1.0F, 1.0f);
                this.level().addFreshEntity(bullet);
            }
        }
    }

    public Projectile getProjectile(AzureAnimatedGunItem item, GunTypeEnum gunTypeEnum) {
        switch (gunTypeEnum) {
            case PISTOL, LUGER, AK7, SMG, GOLDEN_PISTOL, SIL_PISTOL, HELLHORSE, MINIGUN, SNIPER -> {
                return new BulletEntity(level(), this, item.getAttackDamage());
            }
            case FLAMETHROWER -> {
                return new FlameFiring(level(), this);
            }
            case BRIMSTONE -> {
                return new BlazeRodEntity(level(), this);
            }
            case BALROG -> {
                return new FireballEntity(level(), this);
            }
            default -> {
                return new ShellEntity(level(), this);
            }
        }
    }

    public SoundEvent getDefaultAttackSound(GunTypeEnum gunTypeEnum) {
        switch (gunTypeEnum) {
            case PISTOL, GOLDEN_PISTOL -> {
                return HWGSounds.PISTOL;
            }
            case LUGER -> {
                return HWGSounds.LUGER;
            }
            case AK7 -> {
                return HWGSounds.AK;
            }
            case SMG -> {
                return HWGSounds.SMG;
            }
            case HELLHORSE -> {
                return HWGSounds.REVOLVER;
            }
            case SIL_PISTOL -> {
                return HWGSounds.SPISTOL;
            }
            case FLAMETHROWER -> {
                return SoundEvents.FIREWORK_ROCKET_BLAST_FAR;
            }
            case BRIMSTONE -> {
                return SoundEvents.FIRECHARGE_USE;
            }
            case BALROG -> {
                return SoundEvents.GENERIC_EXPLODE;
            }
            case SNIPER -> {
                return HWGSounds.SNIPER;
            }
            case MINIGUN -> {
                return HWGSounds.MINIGUN;
            }
            default -> {
                return HWGSounds.SHOTGUN;
            }
        }
    }

}