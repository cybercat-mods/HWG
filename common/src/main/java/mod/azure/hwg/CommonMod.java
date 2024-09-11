package mod.azure.hwg;

import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import mod.azure.hwg.config.HWGConfig;
import mod.azure.hwg.util.GunSmithProfession;
import mod.azure.hwg.util.registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;

public class CommonMod {
    public static HWGConfig config;
    public static final String MOD_ID = "hwg";
    protected final RandomSource random = RandomSource.create();
    public static final TagKey<Enchantment> HAS_MENDING = TagKey.create(Registries.ENCHANTMENT, modResource("has_mending"));
    public static final TagKey<Item> IS_WEAPON = TagKey.create(Registries.ITEM, modResource("is_weapon"));
    public static final TagKey<Biome> SPY_BIOMES = TagKey.create(Registries.BIOME, CommonMod.modResource("spy_biomes"));
    public static final TagKey<Biome> MERC_BIOMES = TagKey.create(Registries.BIOME, CommonMod.modResource("merc_biomes"));
    public static final TagKey<Biome> TECHNOLESSER_BIOMES = TagKey.create(Registries.BIOME, CommonMod.modResource("technolesser_biomes"));
    public static final TagKey<Biome> TECHNOGREATER_BIOMES = TagKey.create(Registries.BIOME, CommonMod.modResource("technogreater_biomes"));

    public static final ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init(){
        config = AzureLibMod.registerConfig(HWGConfig.class, ConfigFormats.json()).getConfigInstance();
        HWGSounds.init();
        HWGBlocks.init();
        HWGMobs.init();
        HWGProjectiles.init();
        HWGItems.init();
        HWGParticles.init();
        ModRecipes.init();
        ModScreens.init();
        HWGBlocks.init();
        ModTabs.init();
        HWGProfession.init();
    }
}
