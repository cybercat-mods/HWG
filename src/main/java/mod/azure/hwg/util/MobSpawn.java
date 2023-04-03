package mod.azure.hwg.util;

import java.util.List;

import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> parseBiomes(HWGConfig.merc_biomes, context)), MobCategory.MONSTER, HWGMobs.MERC, HWGConfig.merc_spawn_weight, HWGConfig.merc_min_group, HWGConfig.merc_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> parseBiomes(HWGConfig.spy_biomes, context)), MobCategory.MONSTER, HWGMobs.SPY, HWGConfig.spy_spawn_weight, HWGConfig.spy_min_group, HWGConfig.spy_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGConfig.lesser_biomes, context)), MobCategory.MONSTER, HWGMobs.TECHNOLESSER, HWGConfig.lesser_spawn_weight, HWGConfig.lesser_min_group, HWGConfig.lesser_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGConfig.greater_biomes, context)), MobCategory.MONSTER, HWGMobs.TECHNOGREATER, HWGConfig.greater_spawn_weight, HWGConfig.greater_min_group, HWGConfig.greater_max_group);

		SpawnPlacements.register(HWGMobs.TECHNOLESSER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonEntity::canNetherSpawn);
		SpawnPlacements.register(HWGMobs.TECHNOGREATER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonGreaterEntity::canNetherSpawn);
		SpawnPlacements.register(HWGMobs.MERC, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MercEntity::canSpawn);
		SpawnPlacements.register(HWGMobs.SPY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpyEntity::canSpawn);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().location().toString());
	}
}