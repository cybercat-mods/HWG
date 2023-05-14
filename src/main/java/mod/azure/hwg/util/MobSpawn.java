package mod.azure.hwg.util;

import java.util.Arrays;
import java.util.List;

import mod.azure.hwg.HWGMod;
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
		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> parseBiomes(HWGMod.config.merc_biomes, context)), MobCategory.MONSTER, HWGMobs.MERC, HWGMod.config.merc_spawn_weight, HWGMod.config.merc_min_group, HWGMod.config.merc_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> parseBiomes(HWGMod.config.spy_biomes, context)), MobCategory.MONSTER, HWGMobs.SPY, HWGMod.config.spy_spawn_weight, HWGMod.config.spy_min_group, HWGMod.config.spy_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGMod.config.lesser_biomes, context)), MobCategory.MONSTER, HWGMobs.TECHNOLESSER, HWGMod.config.lesser_spawn_weight, HWGMod.config.lesser_min_group, HWGMod.config.lesser_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGMod.config.greater_biomes, context)), MobCategory.MONSTER, HWGMobs.TECHNOGREATER, HWGMod.config.greater_spawn_weight, HWGMod.config.greater_min_group, HWGMod.config.greater_max_group);

		SpawnPlacements.register(HWGMobs.TECHNOLESSER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonEntity::canNetherSpawn);
		SpawnPlacements.register(HWGMobs.TECHNOGREATER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonGreaterEntity::canNetherSpawn);
		SpawnPlacements.register(HWGMobs.MERC, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MercEntity::canSpawn);
		SpawnPlacements.register(HWGMobs.SPY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpyEntity::canSpawn);
	}

	private static boolean parseBiomes(String[] biomes, BiomeSelectionContext biomeContext) {
		List<String> biomelist = Arrays.asList(biomes);
		return biomelist.contains(biomeContext.getBiomeKey().location().toString());
	}
}