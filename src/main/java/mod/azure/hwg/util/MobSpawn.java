package mod.azure.hwg.util;

import java.util.Arrays;
import java.util.List;

import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;

@SuppressWarnings("deprecation")
public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications
				.addSpawn(
						BiomeSelectors.all()
								.and(context -> parseBiomes(Arrays.asList("#forest", "#plains", "#icy", "#taiga",
										"#jungle", "#desert", "#mesa"), context)),
						SpawnGroup.MONSTER, HWGMobs.MERC, 5, 1, 2);

		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(Arrays.asList("#taiga", "#jungle", "#mesa"), context)),
				SpawnGroup.MONSTER, HWGMobs.SPY, 5, 1, 2);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(Arrays.asList("#nether"), context)),
				SpawnGroup.MONSTER, HWGMobs.TECHNOLESSER, 2, 1, 2);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(Arrays.asList("#nether"), context)),
				SpawnGroup.MONSTER, HWGMobs.TECHNOGREATER, 1, 1, 1);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().getValue().toString())
				|| biomes.contains("#" + biomeContext.getBiome().getCategory().asString());
	}
}