package mod.azure.hwg.util;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

    public static void addSpawnEntries() {
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGMod.MERC_BIOMES, context)), MobCategory.MONSTER, HWGMobs.MERC, HWGMod.config.mobconfigs.mercconfigs.merc_spawn_weight, HWGMod.config.mobconfigs.mercconfigs.merc_min_group, HWGMod.config.mobconfigs.mercconfigs.merc_max_group);

        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGMod.SPY_BIOMES, context)), MobCategory.MONSTER, HWGMobs.SPY, HWGMod.config.mobconfigs.spyconfigs.spy_spawn_weight, HWGMod.config.mobconfigs.spyconfigs.spy_min_group, HWGMod.config.mobconfigs.spyconfigs.spy_max_group);

        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGMod.TECHNOLESSER_BIOMES, context)), MobCategory.MONSTER, HWGMobs.TECHNOLESSER, HWGMod.config.mobconfigs.lesserconfigs.lesser_spawn_weight, HWGMod.config.mobconfigs.lesserconfigs.lesser_min_group, HWGMod.config.mobconfigs.lesserconfigs.lesser_max_group);

        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(HWGMod.TECHNOGREATER_BIOMES, context)), MobCategory.MONSTER, HWGMobs.TECHNOGREATER, HWGMod.config.mobconfigs.greatconfigs.greater_spawn_weight, HWGMod.config.mobconfigs.greatconfigs.greater_min_group, HWGMod.config.mobconfigs.greatconfigs.greater_max_group);

        SpawnPlacements.register(HWGMobs.TECHNOLESSER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonEntity::canNetherSpawn);
        SpawnPlacements.register(HWGMobs.TECHNOGREATER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonGreaterEntity::canNetherSpawn);
        SpawnPlacements.register(HWGMobs.MERC, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MercEntity::canSpawn);
        SpawnPlacements.register(HWGMobs.SPY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpyEntity::canSpawn);
    }

    private static boolean parseBiomes(TagKey<Biome> biomes, BiomeSelectionContext biomeContext) {
        return biomeContext.hasTag(biomes);
    }
}