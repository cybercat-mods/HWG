package mod.azure.hwg.util;

import java.util.List;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.config.HWGConfig.Spawning;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

@SuppressWarnings("deprecation")
public class MobSpawn {

	private static Spawning config = HWGMod.config.spawn;

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld().and(context -> parseBiomes(config.merc_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.MERC, config.merc_spawn_weight, config.merc_min_group,
				config.merc_max_group);

		BiomeModifications.addSpawn(
				BiomeSelectors.foundInOverworld().and(context -> parseBiomes(config.spy_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.SPY, config.spy_spawn_weight, config.spy_min_group, config.spy_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.lesser_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.TECHNOLESSER, config.lesser_spawn_weight, config.lesser_min_group,
				config.lesser_max_group);

		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.greater_biomes, context)),
				SpawnGroup.MONSTER, HWGMobs.TECHNOGREATER, config.greater_spawn_weight, config.greater_min_group,
				config.greater_max_group);
		SpawnRestrictionAccessor.callRegister(HWGMobs.TECHNOLESSER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TechnodemonEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(HWGMobs.TECHNOGREATER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TechnodemonGreaterEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(HWGMobs.MERC, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MercEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(HWGMobs.SPY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpyEntity::canSpawn);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().getValue().toString())
				|| biomes.contains("#" + biomeContext.getBiome().getCategory().asString());
	}
}