package mod.azure.hwg;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.platform.NeoForgeCommonRegistry;
import mod.azure.hwg.util.registry.HWGBlocks;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGMobs;

@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
        Registries.ENTITY_TYPE,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        BuiltInRegistries.BLOCK,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        BuiltInRegistries.ITEM,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(
        BuiltInRegistries.PARTICLE_TYPE,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(
        BuiltInRegistries.RECIPE_SERIALIZER,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<MenuType<?>> CONTAIN = DeferredRegister.create(
        BuiltInRegistries.MENU,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
        BuiltInRegistries.SOUND_EVENT,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<VillagerProfession> PROFESSION = DeferredRegister.create(
        Registries.VILLAGER_PROFESSION,
        CommonMod.MOD_ID
    );

    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(
        Registries.POINT_OF_INTEREST_TYPE,
        CommonMod.MOD_ID
    );

    public static final Holder<PoiType> GUNSMITH_POI = POI_TYPE.register(
        "gun_smith",
        () -> new PoiType(
            ImmutableSet.copyOf(
                HWGBlocks.GUN_TABLE.get().getStateDefinition().getPossibleStates()
            ),
            1,
            1
        )
    );

    public static final Supplier<VillagerProfession> GUNSMITH = PROFESSION.register(
        "gun_smith",
        () -> new VillagerProfession(
            "gun_smith",
            entry -> entry.is(GUNSMITH_POI),
            entry -> entry.is(
                GUNSMITH_POI
            ),
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.VILLAGER_WORK_WEAPONSMITH
        )
    );

    public NeoForgeMod(IEventBus modEventBus) {
        CommonMod.init();
        AzureLib.initialize();
        ModEntitySpawn.SERIALIZER.register(modEventBus);
        modEventBus.addListener(this::setup);
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        SOUNDS.register(modEventBus);
        ENTITIES.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        PARTICLES.register(modEventBus);
        TABS.register(modEventBus);
        RECIPE_SERIALIZER.register(modEventBus);
        CONTAIN.register(modEventBus);
        POI_TYPE.register(modEventBus);
        PROFESSION.register(modEventBus);
        AzureLib.hasKeyBindsInitialized = true;
        modEventBus.addListener(this::createEntityAttributes);
        modEventBus.addListener(this::createSpawnPlacements);
        NeoForge.EVENT_BUS.addListener(this::addCustomTrades);
        modEventBus.addListener(this::commonSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
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

    public void addCustomTrades(final VillagerTradesEvent event) {
        var trades = event.getTrades();

        NeoForgeCommonRegistry.getVillagerTradeData()
            .forEach(entry -> {
                // entry.getKey() = Supplier<VillagerProfession>
                // entry.getValue() = LevelTrades(level, trades)
                if (event.getType() == entry.getKey().get()) {
                    var levelTrades = entry.getValue();
                    trades.get(levelTrades.level()).addAll(levelTrades.trades());
                }
            });
    }

    public void setup(final FMLCommonSetupEvent event) {
        new PacketHandler().registerMessages();
    }

    public void createEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(HWGMobs.SPY.get(), SpyEntity.createMobAttributes().build());
        event.put(HWGMobs.MERC.get(), MercEntity.createMobAttributes().build());
        event.put(HWGMobs.TECHNOLESSER.get(), TechnodemonEntity.createMobAttributes().build());
        event.put(HWGMobs.TECHNOGREATER.get(), TechnodemonGreaterEntity.createMobAttributes().build());
    }

    public void createSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
            HWGMobs.TECHNOLESSER.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            TechnodemonEntity::canNetherSpawn,
            RegisterSpawnPlacementsEvent.Operation.AND
        );
        event.register(
            HWGMobs.TECHNOGREATER.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            TechnodemonGreaterEntity::canNetherSpawn,
            RegisterSpawnPlacementsEvent.Operation.AND
        );
        event.register(
            HWGMobs.MERC.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            MercEntity::canSpawn,
            RegisterSpawnPlacementsEvent.Operation.AND
        );
        event.register(
            HWGMobs.SPY.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            SpyEntity::canSpawn,
            RegisterSpawnPlacementsEvent.Operation.AND
        );
    }

    record ModEntitySpawn(
        HolderSet<Biome> biomes,
        MobSpawnSettings.SpawnerData spawn
    ) implements BiomeModifier {

        public static DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZER = DeferredRegister.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
            CommonMod.MOD_ID
        );

        static Supplier<MapCodec<ModEntitySpawn>> JARJAR_SPAWN_CODEC = SERIALIZER.register(
            "mobspawns",
            () -> RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawn::biomes),
                    MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn")
                        .forGetter(
                            ModEntitySpawn::spawn
                        )
                ).apply(builder, ModEntitySpawn::new)
            )
        );

        @Override
        public void modify(
            @NotNull Holder<Biome> biome,
            @NotNull Phase phase,
            ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder
        ) {
            if (phase == Phase.ADD && this.biomes.contains(biome)) {
                builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn);
            }
        }

        @Override
        public @NotNull MapCodec<? extends BiomeModifier> codec() {
            return JARJAR_SPAWN_CODEC.get();
        }
    }
}
