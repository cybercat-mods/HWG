package mod.azure.hwg;

import com.google.common.collect.ImmutableSet;
import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.levelgen.Heightmap;

import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.registry.HWGBlocks;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGMobs;

public final class FabricLibMod implements ModInitializer {

    public static final ResourceKey<PoiType> GUNSMITH_KEY = registerPoiKey();

    public static final PoiType GUNSMITH_POI = registerPOI();

    public static final VillagerProfession GUNSMITH = registerProfession();

    private static ResourceKey<PoiType> registerPoiKey() {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, CommonMod.modResource("gun_smith"));
    }

    private static PoiType registerPOI() {
        return PointOfInterestHelper.register(
            CommonMod.modResource("gun_smith"),
            1,
            1,
            HWGBlocks.GUN_TABLE.get()
        );
    }

    private static VillagerProfession registerProfession() {
        return Registry.register(
            BuiltInRegistries.VILLAGER_PROFESSION,
            CommonMod.modResource("gun_smith"),
            new VillagerProfession(
                "gun_smith",
                entry -> entry.is(FabricLibMod.GUNSMITH_KEY),
                entry -> entry.is(
                    FabricLibMod.GUNSMITH_KEY
                ),
                ImmutableSet.of(),
                ImmutableSet.of(),
                SoundEvents.VILLAGER_WORK_WEAPONSMITH
            )
        );
    }

    @Override
    public void onInitialize() {
        CommonMod.init();
        AzureLib.initialize();
        new PacketHandler().registerMessages();
        AzureLib.hasKeyBindsInitialized = true;
        FabricDefaultAttributeRegistry.register(HWGMobs.SPY.get(), SpyEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(HWGMobs.MERC.get(), MercEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER.get(), TechnodemonEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(
            HWGMobs.TECHNOGREATER.get(),
            TechnodemonGreaterEntity.createMobAttributes()
        );
        addSpawnEntries();
        AzIdentityRegistry.register(
            HWGItems.FLARE_GUN.get(),
            HWGItems.G_LAUNCHER.get(),
            HWGItems.FLAMETHROWER.get(),
            HWGItems.MINIGUN.get(),
            HWGItems.LUGER.get(),
            HWGItems.PISTOL.get(),
            HWGItems.SHOTGUN.get(),
            HWGItems.SPISTOL.get(),
            HWGItems.SNIPER.get(),
            HWGItems.MEANIE1.get(),
            HWGItems.MEANIE2.get(),
            HWGItems.GOLDEN_GUN.get(),
            HWGItems.ROCKETLAUNCHER.get(),
            HWGItems.HELLHORSE.get(),
            HWGItems.SILVERGUN.get(),
            HWGItems.SILVERHELLHORSE.get(),
            HWGItems.AK47.get(),
            HWGItems.SMG.get(),
            HWGItems.TOMMYGUN.get(),
            HWGItems.BALROG.get(),
            HWGItems.BRIMSTONE.get(),
            HWGItems.INCINERATOR.get()
        );
    }

    public static void addSpawnEntries() {
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(CommonMod.MERC_BIOMES),
            MobCategory.MONSTER,
            HWGMobs.MERC.get(),
            CommonMod.config.mobconfigs.mercconfigs.merc_spawn_weight,
            CommonMod.config.mobconfigs.mercconfigs.merc_min_group,
            CommonMod.config.mobconfigs.mercconfigs.merc_max_group
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(CommonMod.SPY_BIOMES),
            MobCategory.MONSTER,
            HWGMobs.SPY.get(),
            CommonMod.config.mobconfigs.spyconfigs.spy_spawn_weight,
            CommonMod.config.mobconfigs.spyconfigs.spy_min_group,
            CommonMod.config.mobconfigs.spyconfigs.spy_max_group
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(CommonMod.TECHNOLESSER_BIOMES),
            MobCategory.MONSTER,
            HWGMobs.TECHNOLESSER.get(),
            CommonMod.config.mobconfigs.lesserconfigs.lesser_spawn_weight,
            CommonMod.config.mobconfigs.lesserconfigs.lesser_min_group,
            CommonMod.config.mobconfigs.lesserconfigs.lesser_max_group
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(CommonMod.TECHNOGREATER_BIOMES),
            MobCategory.MONSTER,
            HWGMobs.TECHNOGREATER.get(),
            CommonMod.config.mobconfigs.greatconfigs.greater_spawn_weight,
            CommonMod.config.mobconfigs.greatconfigs.greater_min_group,
            CommonMod.config.mobconfigs.greatconfigs.greater_max_group
        );

        SpawnPlacements.register(
            HWGMobs.TECHNOLESSER.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            TechnodemonEntity::canNetherSpawn
        );
        SpawnPlacements.register(
            HWGMobs.TECHNOGREATER.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            TechnodemonGreaterEntity::canNetherSpawn
        );
        SpawnPlacements.register(
            HWGMobs.MERC.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            MercEntity::canSpawn
        );
        SpawnPlacements.register(
            HWGMobs.SPY.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            SpyEntity::canSpawn
        );
    }
}
