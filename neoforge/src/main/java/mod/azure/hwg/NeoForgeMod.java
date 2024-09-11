package mod.azure.hwg;

import com.google.common.eventbus.Subscribe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.HWGProfession;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, CommonMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CommonMod.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CommonMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CommonMod.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, CommonMod.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CommonMod.MOD_ID);
    public static final DeferredRegister<MenuType<?>> CONTAIN = DeferredRegister.create(BuiltInRegistries.MENU, CommonMod.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CommonMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CommonMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSION = DeferredRegister.create(Registries.VILLAGER_PROFESSION, CommonMod.MOD_ID);
    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, CommonMod.MOD_ID);

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
    }

    @SubscribeEvent
    public static void villagerTrades(final VillagerTradesEvent event){
        if (event.getType() == HWGProfession.GUNSMITH.get()) {
            event.getTrades().put(1, List.of(new VillagerTrades.ItemListing[]{new GunSmithProfession.BuyForOneEmeraldFactory(Items.GUNPOWDER, 1, 16, 2), new GunSmithProfession.SellItemFactory(Items.IRON_NUGGET, 2, 1, 16, 1)}));
            event.getTrades().put(2, List.of(new VillagerTrades.ItemListing[]{new GunSmithProfession.BuyForItemFactory(Items.EMERALD, HWGItems.BULLETS.get(), 2, 16, 10), new GunSmithProfession.BuyForItemFactory(Items.EMERALD, HWGItems.PISTOL.get(), 5, 16, 20), new GunSmithProfession.BuyForItemFactory(Items.EMERALD, HWGItems.LUGER.get(), 5, 16, 20)}));
            event.getTrades().put(3, List.of(new VillagerTrades.ItemListing[]{new GunSmithProfession.BuyForItemsFactory(Items.EMERALD, 2, 1, HWGItems.SHOTGUN_SHELL.get(), 16, 16, 30), new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 3, HWGItems.SMG.get(), 1, 16, 30), new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 3, HWGItems.TOMMYGUN.get(), 1, 16, 30)}));
            event.getTrades().put(4, List.of(new VillagerTrades.ItemListing[]{new GunSmithProfession.BuyForItemsFactory(HWGItems.FUEL_TANK.get(), 1, 4, HWGItems.FLAMETHROWER.get(), 1, 16, 40), new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 6, 4, HWGItems.SHOTGUN.get(), 1, 16, 40), new GunSmithProfession.BuyForItemsFactory(Items.GUNPOWDER, 8, 4, HWGItems.BULLETS.get(), 48, 16, 50)}));
            event.getTrades().put(5, List.of(new VillagerTrades.ItemListing[]{new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.ROCKETLAUNCHER.get(), 1, 16, 60), new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.G_LAUNCHER.get(), 1, 16, 60), new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.SNIPER.get(), 1, 16, 60)}));
        }
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
        event.register(HWGMobs.TECHNOLESSER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonEntity::canNetherSpawn,
                RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(HWGMobs.TECHNOGREATER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TechnodemonGreaterEntity::canNetherSpawn,
                RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(HWGMobs.MERC.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MercEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(HWGMobs.SPY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpyEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.AND);
    }

    record ModEntitySpawn(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawn) implements BiomeModifier {

        public static DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZER = DeferredRegister.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CommonMod.MOD_ID);

        static Supplier<MapCodec<ModEntitySpawn>> JARJAR_SPAWN_CODEC = SERIALIZER.register("mobspawns",
                () -> RecordCodecBuilder.mapCodec(
                        builder -> builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawn::biomes),
                                MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn").forGetter(
                                        ModEntitySpawn::spawn)).apply(builder, ModEntitySpawn::new)));


        @Override
        public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
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
