package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.Technodemon2Entity;
import mod.azure.hwg.entity.TechnodemonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGMobs {

	public static final EntityType<TechnodemonEntity> TECHNOLESSER1 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_lesser_1"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<Technodemon2Entity> TECHNOLESSER2 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_lesser_2"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Technodemon2Entity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<TechnodemonEntity> TECHNOLESSER3 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_lesser_3"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<TechnodemonEntity> TECHNOLESSER4 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_lesser_4"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());
	
	public static final EntityType<TechnodemonEntity> TECHNOGREATER1 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_greater_1"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<TechnodemonEntity> TECHNOGREATER2 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(HWGMod.MODID, "technodemon_greater_2"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TechnodemonEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());
}
