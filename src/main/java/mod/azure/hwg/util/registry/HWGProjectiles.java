package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.LinkedList;
import java.util.List;

public record HWGProjectiles() {

    public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
    public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

    public static EntityType<ShellEntity> SHELL = projectile(ShellEntity::new, "shell");
    public static EntityType<BulletEntity> BULLETS = projectile(BulletEntity::new, "bullets");
    public static EntityType<RocketEntity> ROCKETS = projectile(RocketEntity::new, "rockets");
    public static EntityType<GrenadeEntity> GRENADE = projectile(GrenadeEntity::new, "grenade");    public static EntityType<BaseFlareEntity> FLARE = projectile(BaseFlareEntity::new, "flare");
    public static EntityType<FlameFiring> FIRING = projectile1(FlameFiring::new, "flame_firing");
    public static EntityType<MBulletEntity> MBULLETS = projectile(MBulletEntity::new, "mbullets");
    public static EntityType<BlazeRodEntity> BLAZEROD = projectile(BlazeRodEntity::new, "blazerod");
    public static EntityType<FireballEntity> FIREBALL = projectile(FireballEntity::new, "fireball");

    private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
        return projectile(factory, id, true);
    }

    private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id, boolean itemRender) {

        EntityType<T> type = FabricEntityTypeBuilder.create(MobCategory.MISC, factory).dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer().trackRangeBlocks(90).trackedUpdateRate(1).build();

        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, id), type);

        ENTITY_TYPES.add(type);

        if (itemRender)
            ENTITY_THAT_USE_ITEM_RENDERS.add(type);

        return type;
    }

    private static <T extends Entity> EntityType<T> projectile1(EntityType.EntityFactory<T> factory, String id) {
        return projectile1(factory, id, true);
    }

    private static <T extends Entity> EntityType<T> projectile1(EntityType.EntityFactory<T> factory, String id, boolean itemRender) {

        EntityType<T> type = FabricEntityTypeBuilder.create(MobCategory.MISC, factory).dimensions(new EntityDimensions(1.5F, 1.5F, false)).disableSummon().spawnableFarFromPlayer().fireImmune().trackRangeBlocks(90).trackedUpdateRate(40).build();

        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, id), type);

        ENTITY_TYPES.add(type);

        if (itemRender)
            ENTITY_THAT_USE_ITEM_RENDERS.add(type);

        return type;
    }


}
