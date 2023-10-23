package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.entity.blockentity.GunBlockEntity;
import mod.azure.hwg.entity.blockentity.TickingLightEntity;
import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class HWGMobs {

    public static final EntityType<TechnodemonEntity> TECHNOLESSER = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, "technodemon_lesser_1"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, TechnodemonEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90).trackedUpdateRate(1).build());

    public static final EntityType<TechnodemonGreaterEntity> TECHNOGREATER = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, "technodemon_greater_1"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, TechnodemonGreaterEntity::new).dimensions(EntityDimensions.fixed(1.3f, 3.5F)).fireImmune().trackRangeBlocks(90).trackedUpdateRate(1).build());

    public static final EntityType<MercEntity> MERC = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, "merc"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, MercEntity::new).dimensions(EntityDimensions.fixed(1.1F, 2.1F)).trackRangeBlocks(90).trackedUpdateRate(1).build());

    public static final EntityType<SpyEntity> SPY = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, "spy"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, SpyEntity::new).dimensions(EntityDimensions.fixed(1.1F, 2.1F)).trackRangeBlocks(90).trackedUpdateRate(1).build());

    public static final EntityType<FuelTankEntity> FUELTANK = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(HWGMod.MODID, "fuel_tank"), FabricEntityTypeBuilder.<FuelTankEntity>create(MobCategory.MISC, FuelTankEntity::new).dimensions(EntityDimensions.fixed(0.98F, 0.98F)).trackRangeBlocks(90).trackedUpdateRate(1).build());

    public static final BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, HWGMod.MODID + ":guntable", FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, HWGBlocks.GUN_TABLE).build(null));
    public static final BlockEntityType<TickingLightEntity> TICKING_LIGHT_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, HWGMod.MODID + ":lightblock", FabricBlockEntityTypeBuilder.create(TickingLightEntity::new, HWGBlocks.TICKING_LIGHT_BLOCK).build(null));

}
