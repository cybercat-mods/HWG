package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public record HWGParticles() {

    public static final SimpleParticleType BRIM_RED = register(new ResourceLocation(HWGMod.MODID, "brim_red"), false);
    public static final SimpleParticleType RED_FLARE = register(new ResourceLocation(HWGMod.MODID, "red_flare"), false);
    public static final SimpleParticleType BLUE_FLARE = register(new ResourceLocation(HWGMod.MODID, "blue_flare"), false);
    public static final SimpleParticleType CYAN_FLARE = register(new ResourceLocation(HWGMod.MODID, "cyan_flare"), false);
    public static final SimpleParticleType GRAY_FLARE = register(new ResourceLocation(HWGMod.MODID, "gray_flare"), false);
    public static final SimpleParticleType LIME_FLARE = register(new ResourceLocation(HWGMod.MODID, "lime_flare"), false);
    public static final SimpleParticleType PINK_FLARE = register(new ResourceLocation(HWGMod.MODID, "pink_flare"), false);
    public static final SimpleParticleType BLACK_FLARE = register(new ResourceLocation(HWGMod.MODID, "black_flare"), false);
    public static final SimpleParticleType BRIM_ORANGE = register(new ResourceLocation(HWGMod.MODID, "brim_orange"), false);
    public static final SimpleParticleType BROWN_FLARE = register(new ResourceLocation(HWGMod.MODID, "brown_flare"), false);
    public static final SimpleParticleType GREEN_FLARE = register(new ResourceLocation(HWGMod.MODID, "green_flare"), false);
    public static final SimpleParticleType WHITE_FLARE = register(new ResourceLocation(HWGMod.MODID, "white_flare"), false);
    public static final SimpleParticleType ORANGE_FLARE = register(new ResourceLocation(HWGMod.MODID, "orange_flare"), false);
    public static final SimpleParticleType PURPLE_FLARE = register(new ResourceLocation(HWGMod.MODID, "purple_flare"), false);
    public static final SimpleParticleType YELLOW_FLARE = register(new ResourceLocation(HWGMod.MODID, "yellow_flare"), false);
    public static final SimpleParticleType MAGENTA_FLARE = register(new ResourceLocation(HWGMod.MODID, "magenta_flare"), false);
    public static final SimpleParticleType LIGHTBLUE_FLARE = register(new ResourceLocation(HWGMod.MODID, "lightblue_flare"), false);
    public static final SimpleParticleType LIGHTGRAY_FLARE = register(new ResourceLocation(HWGMod.MODID, "lightgray_flare"), false);

    private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
    }

    public static void initialize() {
    }
}
