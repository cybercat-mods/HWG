package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGMobs {

	public static final EntityType<TechnodemonEntity> TECHNOLESSER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_lesser_1"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<TechnodemonGreaterEntity> TECHNOGREATER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_greater_1"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonGreaterEntity::new)
					.dimensions(EntityDimensions.fixed(1.3f, 3.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<MercEntity> MERC = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "merc"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MercEntity::new)
					.dimensions(EntityDimensions.fixed(1.1F, 2.1F)).trackRangeBlocks(90).trackedUpdateRate(4).build());

	public static final EntityType<SpyEntity> SPY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "spy"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SpyEntity::new)
					.dimensions(EntityDimensions.fixed(1.1F, 2.1F)).trackRangeBlocks(90).trackedUpdateRate(4).build());

	public static final EntityType<FuelTankEntity> FUELTANK = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "fuel_tank"),
			FabricEntityTypeBuilder.<FuelTankEntity>create(SpawnGroup.MISC, FuelTankEntity::new)
					.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).trackRangeBlocks(90).trackedUpdateRate(4)
					.build());
}
