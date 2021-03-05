package mod.azure.hwg.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.config.HWGConfig.Spawning;
import mod.azure.hwg.util.registry.HWGMobs;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;

public class MobSpawn {

	private static Spawning config = HWGMod.config.spawn;

	public static void addSpawnEntries() {

		for (Biome biome : BuiltinRegistries.BIOME) {
			if (biome.getCategory() == Biome.Category.FOREST) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
			}
			if (biome.getCategory() == Biome.Category.TAIGA) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.SPY,
						config.spy_spawn_weight, config.spy_min_group, config.spy_max_group));
			}
			if (biome.getCategory() == Biome.Category.DESERT) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
			}
			if (biome.getCategory() == Biome.Category.MESA) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.SPY,
						config.spy_spawn_weight, config.spy_min_group, config.spy_max_group));
			}
			if (biome.getCategory() == Biome.Category.JUNGLE) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.SPY,
						config.spy_spawn_weight, config.spy_min_group, config.spy_max_group));
			}
			if (biome.getCategory() == Biome.Category.PLAINS) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
			}
			if (biome.getCategory() == Biome.Category.ICY) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.MERC,
						config.merc_spawn_weight, config.merc_min_group, config.merc_max_group));
			}
			if (biome.getCategory() == Biome.Category.NETHER) {
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.TECHNOLESSER,
						config.lesser_spawn_weight, config.lesser_min_group, config.lesser_max_group));
				addMobSpawnToBiome(biome, SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(HWGMobs.TECHNOGREATER,
						config.greater_spawn_weight, config.greater_min_group, config.greater_max_group));
			}
		}
//		BiomeModifications.addSpawn(
//				BiomeSelectors.foundInOverworld()
//						.and(context -> !context.getBiome().getSpawnSettings()
//								.getSpawnEntry(HWGMobs.MERC.getSpawnGroup()).isEmpty()
//								&& (context.getBiome().getCategory() == Biome.Category.FOREST
//										|| context.getBiome().getCategory() == Biome.Category.TAIGA
//										|| context.getBiome().getCategory() == Biome.Category.DESERT
//										|| context.getBiome().getCategory() == Biome.Category.MESA
//										|| context.getBiome().getCategory() == Biome.Category.JUNGLE
//										|| context.getBiome().getCategory() == Biome.Category.PLAINS
//										|| context.getBiome().getCategory() == Biome.Category.ICY)),
//				SpawnGroup.MONSTER, HWGMobs.MERC, config.merc_spawn_weight, config.merc_min_group,
//				config.merc_max_group);
//		BiomeModifications.addSpawn(
//				BiomeSelectors.foundInOverworld()
//						.and(context -> !context.getBiome().getSpawnSettings()
//								.getSpawnEntry(HWGMobs.SPY.getSpawnGroup()).isEmpty()
//								&& context.getBiome().getCategory() == Biome.Category.TAIGA
//								|| context.getBiome().getCategory() == Biome.Category.MESA
//								|| context.getBiome().getCategory() == Biome.Category.JUNGLE),
//				SpawnGroup.MONSTER, HWGMobs.SPY, config.spy_spawn_weight, config.spy_min_group, config.spy_max_group);
//		BiomeModifications.addSpawn(
//				BiomeSelectors.foundInTheNether()
//						.and(context -> !context.getBiome().getSpawnSettings()
//								.getSpawnEntry(HWGMobs.TECHNOLESSER.getSpawnGroup()).isEmpty()
//								&& context.getBiome().getCategory() == Biome.Category.NETHER),
//				SpawnGroup.MONSTER, HWGMobs.TECHNOLESSER, config.lesser_spawn_weight, config.lesser_min_group,
//				config.lesser_max_group);
//		BiomeModifications.addSpawn(
//				BiomeSelectors.foundInTheNether()
//						.and(context -> !context.getBiome().getSpawnSettings()
//								.getSpawnEntry(HWGMobs.TECHNOGREATER.getSpawnGroup()).isEmpty()
//								&& context.getBiome().getCategory() == Biome.Category.NETHER),
//				SpawnGroup.MONSTER, HWGMobs.TECHNOGREATER, config.greater_spawn_weight, config.greater_min_group,
//				config.greater_max_group);
	}

	public static void addMobSpawnToBiome(Biome biome, SpawnGroup classification,
			SpawnSettings.SpawnEntry... spawnInfos) {
		convertImmutableSpawners(biome);
		List<SpawnSettings.SpawnEntry> spawnersList = new ArrayList<>(
				biome.getSpawnSettings().spawners.get(classification));
		spawnersList.addAll(Arrays.asList(spawnInfos));
		biome.getSpawnSettings().spawners.put(classification, spawnersList);
	}

	private static void convertImmutableSpawners(Biome biome) {
		if (biome.getSpawnSettings().spawners instanceof ImmutableMap) {
			biome.getSpawnSettings().spawners = new HashMap<>(biome.getSpawnSettings().spawners);
		}
	}
}