package mod.azure.hwg.util.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.entity.blockentity.GunBlockEntity;
import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import mod.azure.hwg.util.registry.interfaces.CommonBlockEntityRegistryInterface;
import mod.azure.hwg.util.registry.interfaces.CommonEntityRegistryInterface;

public record HWGMobs() {

    public static final Supplier<EntityType<TechnodemonEntity>> TECHNOLESSER = CommonEntityRegistryInterface
        .registerEntity(
            CommonMod.MOD_ID,
            "technodemon_lesser_1",
            TechnodemonEntity::new,
            MobCategory.MONSTER,
            0.9f,
            2.5F
        );

    public static final Supplier<EntityType<TechnodemonGreaterEntity>> TECHNOGREATER = CommonEntityRegistryInterface
        .registerEntity(
            CommonMod.MOD_ID,
            "technodemon_greater_1",
            TechnodemonGreaterEntity::new,
            MobCategory.MONSTER,
            1.3f,
            3.5F
        );

    public static final Supplier<EntityType<MercEntity>> MERC = CommonEntityRegistryInterface.registerEntity(
        CommonMod.MOD_ID,
        "merc",
        MercEntity::new,
        MobCategory.MONSTER,
        1.1F,
        2.1F
    );

    public static final Supplier<EntityType<SpyEntity>> SPY = CommonEntityRegistryInterface.registerEntity(
        CommonMod.MOD_ID,
        "spy",
        SpyEntity::new,
        MobCategory.MONSTER,
        1.1F,
        2.1F
    );

    public static final Supplier<EntityType<FuelTankEntity>> FUELTANK = CommonEntityRegistryInterface.registerEntity(
        CommonMod.MOD_ID,
        "fuel_tank",
        FuelTankEntity::new,
        MobCategory.MISC,
        0.98F,
        0.98F
    );

    public static final Supplier<BlockEntityType<GunBlockEntity>> GUN_TABLE_ENTITY = CommonBlockEntityRegistryInterface
        .registerBlockEntity(
            CommonMod.MOD_ID,
            "guntable",
            () -> BlockEntityType.Builder.of(GunBlockEntity::new, HWGBlocks.GUN_TABLE.get()).build(null)
        );

    public static void init() {}
}
