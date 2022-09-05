package mod.azure.hwg.util;

import java.util.List;

import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.HWGMobs;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld().and(context -> parseBiomes(HWGConfig.merc_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.MERC, HWGConfig.merc_spawn_weight, HWGConfig.merc_min_group,
				HWGConfig.merc_max_group);

		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld().and(context -> parseBiomes(HWGConfig.spy_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.SPY, HWGConfig.spy_spawn_weight, HWGConfig.spy_min_group,
				HWGConfig.spy_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGConfig.lesser_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.TECHNOLESSER, HWGConfig.lesser_spawn_weight, HWGConfig.lesser_min_group,
				HWGConfig.lesser_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGConfig.greater_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.TECHNOGREATER, HWGConfig.greater_spawn_weight, HWGConfig.greater_min_group,
				HWGConfig.greater_max_group);

		SpawnRestriction.register(HWGMobs.TECHNOLESSER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TechnodemonEntity::canNetherSpawn);
		SpawnRestriction.register(HWGMobs.TECHNOGREATER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TechnodemonGreaterEntity::canNetherSpawn);
		SpawnRestriction.register(HWGMobs.MERC, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MercEntity::canSpawn);
		SpawnRestriction.register(HWGMobs.SPY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpyEntity::canSpawn);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().getValue().toString())
				|| biomes.contains("#" + biomeContext.getBiomeHolder().toString());
	}
}