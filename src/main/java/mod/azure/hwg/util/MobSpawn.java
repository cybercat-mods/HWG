package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.config.HWGConfig.Spawning;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("deprecation")
public class MobSpawn {

	private static Spawning config = HWGMod.config.spawn;

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.MERC.getSpawnGroup()).isEmpty()
								&& (context.getBiome().getCategory() == Biome.Category.FOREST
										|| context.getBiome().getCategory() == Biome.Category.TAIGA
										|| context.getBiome().getCategory() == Biome.Category.DESERT
										|| context.getBiome().getCategory() == Biome.Category.MESA
										|| context.getBiome().getCategory() == Biome.Category.JUNGLE
										|| context.getBiome().getCategory() == Biome.Category.PLAINS
										|| context.getBiome().getCategory() == Biome.Category.ICY)),
				SpawnGroup.MONSTER, HWGMobs.MERC, config.merc_spawn_weight, config.merc_min_group,
				config.merc_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.SPY.getSpawnGroup()).isEmpty()
								&& context.getBiome().getCategory() == Biome.Category.TAIGA
								|| context.getBiome().getCategory() == Biome.Category.MESA
								|| context.getBiome().getCategory() == Biome.Category.JUNGLE),
				SpawnGroup.MONSTER, HWGMobs.SPY, config.spy_spawn_weight, config.spy_min_group, config.spy_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.TECHNOLESSER.getSpawnGroup()).isEmpty()
								&& context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, HWGMobs.TECHNOLESSER, config.lesser_spawn_weight, config.lesser_min_group,
				config.lesser_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.TECHNOGREATER.getSpawnGroup()).isEmpty()
								&& context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, HWGMobs.TECHNOGREATER, config.greater_spawn_weight, config.greater_min_group,
				config.greater_max_group);
	}
}