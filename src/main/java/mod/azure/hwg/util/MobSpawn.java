package mod.azure.hwg.util;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("deprecation")
public class MobSpawn {

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
				SpawnGroup.MONSTER, HWGMobs.MERC, 5, 1, 2);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.SPY.getSpawnGroup()).isEmpty()
								&& context.getBiome().getCategory() == Biome.Category.TAIGA
								|| context.getBiome().getCategory() == Biome.Category.MESA
								|| context.getBiome().getCategory() == Biome.Category.JUNGLE),
				SpawnGroup.MONSTER, HWGMobs.SPY, 5, 1, 2);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.TECHNOLESSER.getSpawnGroup()).isEmpty()
								&& context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, HWGMobs.TECHNOLESSER, 10, 1, 2);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> !context.getBiome().getSpawnSettings()
								.getSpawnEntry(HWGMobs.TECHNOGREATER.getSpawnGroup()).isEmpty()
								&& context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, HWGMobs.TECHNOGREATER, 10, 1, 2);
	}
}