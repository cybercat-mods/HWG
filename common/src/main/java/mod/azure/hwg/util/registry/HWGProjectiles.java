package mod.azure.hwg.util.registry;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.projectiles.*;
import mod.azure.hwg.util.registry.interfaces.CommonEntityRegistryInterface;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public record HWGProjectiles() {

    public static final Supplier<EntityType<ShellEntity>> SHELL = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "shell", ShellEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<BulletEntity>> BULLETS = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "bullets", BulletEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<RocketEntity>> ROCKETS = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "rockets", RocketEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<GrenadeEntity>> GRENADE = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "grenade", GrenadeEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<BaseFlareEntity>> FLARE = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "flare", BaseFlareEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<FlameFiring>> FIRING = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "flame_firing", FlameFiring::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<MBulletEntity>> MBULLETS = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "mbullets", MBulletEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<BlazeRodEntity>> BLAZEROD = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "blazerod", BlazeRodEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<FireballEntity>> FIREBALL = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "fireball", FireballEntity::new, MobCategory.MISC, 0.5F, 0.5F);
    public static final Supplier<EntityType<SBulletEntity>> SILVERBULLETS = CommonEntityRegistryInterface.registerEntity(
            CommonMod.MOD_ID, "silverbullets", SBulletEntity::new, MobCategory.MISC, 0.5F, 0.5F);

    public static void init() {
    }

}
