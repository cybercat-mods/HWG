package mod.azure.hwg.entity;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.*;
import mod.azure.hwg.item.weapons.*;
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
import net.minecraft.world.item.Item;
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
        if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
            return false;
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

    protected boolean shouldDrown() {
        return false;
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    public void setShooting(boolean attacking) {

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

    public int getAttckingState() {
        return entityData.get(STATE);
    }

    public void setAttackingState(int time) {
        entityData.set(STATE, time);
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

    public Projectile getProjectile(Item item) {
        float damage;
        if (item instanceof PistolItem)
            damage = HWGMod.config.gunconfigs.pistolconfigs.pistol_damage;
        else if (item instanceof LugerItem)
            damage = HWGMod.config.gunconfigs.lugerconfigs.luger_damage;
        else if (item instanceof AssasultItem)
            damage = HWGMod.config.gunconfigs.ak47configs.ak47_damage;
        else if (item instanceof Assasult1Item)
            damage = HWGMod.config.gunconfigs.smgconfigs.smg_damage;
        else if (item instanceof GPistolItem)
            damage = HWGMod.config.gunconfigs.gpistolconfigs.golden_pistol_damage;
        else if (item instanceof SPistolItem)
            damage = HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_damage;
        else if (item instanceof HellhorseRevolverItem)
            damage = HWGMod.config.gunconfigs.hellhorseconfigs.hellhorse_damage;
        else if (item instanceof Minigun)
            damage = HWGMod.config.gunconfigs.minigunconfigs.minigun_damage;
        else
            damage = HWGMod.config.gunconfigs.sniperconfigs.sniper_damage;
        if (item instanceof PistolItem || item instanceof LugerItem || item instanceof AssasultItem || item instanceof Assasult1Item || item instanceof GPistolItem || item instanceof SPistolItem || item instanceof SniperItem || item instanceof HellhorseRevolverItem || item instanceof Minigun) {
            return new BulletEntity(level(), this, damage);
        }
        if (item instanceof FlamethrowerItem) {
            return new FlameFiring(level(), this);
        }
        if (item instanceof BrimstoneItem) {
            return new BlazeRodEntity(level(), this);
        }
        if (item instanceof BalrogItem) {
            return new FireballEntity(level(), this);
        }
        return new ShellEntity(level(), this);
    }

    public void shoot() {
        if (!this.level().isClientSide) {
            final var vector3d = getViewVector(1.0F);
            final var bullet = getProjectile(getItemBySlot(EquipmentSlot.MAINHAND).getItem());
            bullet.setPos(this.getX() + vector3d.x * 2, this.getY(0.5), this.getZ() + vector3d.z * 2);
            bullet.shootFromRotation(this, getXRot(), getYRot(), 0.0F, 3.0F, 1.0F);
            if (getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GeoItem weapon)
                weapon.triggerAnim(this, GeoItem.getOrAssignId(getItemBySlot(EquipmentSlot.MAINHAND), (ServerLevel) this.level()), "shoot_controller", "firing");
            this.level().playSound(this, blockPosition(), getDefaultAttackSound(getItemBySlot(EquipmentSlot.MAINHAND).getItem()), SoundSource.HOSTILE, 1.0F, 1.0f);
            this.level().addFreshEntity(bullet);
        }
    }

    public SoundEvent getDefaultAttackSound(Item item) {
        if (item instanceof GPistolItem || item instanceof SPistolItem) {
            return HWGSounds.SPISTOL;
        }
        if (item instanceof PistolItem) {
            return HWGSounds.PISTOL;
        }
        if (item instanceof LugerItem) {
            return HWGSounds.LUGER;
        }
        if (item instanceof AssasultItem) {
            return HWGSounds.AK;
        }
        if (item instanceof Assasult1Item) {
            return HWGSounds.SMG;
        }
        if (item instanceof ShotgunItem) {
            return HWGSounds.SHOTGUN;
        }
        if (item instanceof HellhorseRevolverItem) {
            return HWGSounds.REVOLVER;
        }
        if (item instanceof FlamethrowerItem) {
            return SoundEvents.FIREWORK_ROCKET_BLAST_FAR;
        }
        if (item instanceof BrimstoneItem) {
            return SoundEvents.FIRECHARGE_USE;
        }
        if (item instanceof BalrogItem) {
            return SoundEvents.GENERIC_EXPLODE;
        }
        if (item instanceof Minigun) {
            return HWGSounds.MINIGUN;
        }
        return HWGSounds.SNIPER;
    }

}