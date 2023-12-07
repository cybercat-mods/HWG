package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public record HWGParticles() {

    public static final SimpleParticleType BRIM_RED = register(HWGMod.modResource("brim_red"), false);
    public static final SimpleParticleType RED_FLARE = register(HWGMod.modResource("red_flare"), false);
    public static final SimpleParticleType BLUE_FLARE = register(HWGMod.modResource("blue_flare"), false);
    public static final SimpleParticleType CYAN_FLARE = register(HWGMod.modResource("cyan_flare"), false);
    public static final SimpleParticleType GRAY_FLARE = register(HWGMod.modResource("gray_flare"), false);
    public static final SimpleParticleType LIME_FLARE = register(HWGMod.modResource("lime_flare"), false);
    public static final SimpleParticleType PINK_FLARE = register(HWGMod.modResource("pink_flare"), false);
    public static final SimpleParticleType BLACK_FLARE = register(HWGMod.modResource("black_flare"), false);
    public static final SimpleParticleType BRIM_ORANGE = register(HWGMod.modResource("brim_orange"), false);
    public static final SimpleParticleType BROWN_FLARE = register(HWGMod.modResource("brown_flare"), false);
    public static final SimpleParticleType GREEN_FLARE = register(HWGMod.modResource("green_flare"), false);
    public static final SimpleParticleType WHITE_FLARE = register(HWGMod.modResource("white_flare"), false);
    public static final SimpleParticleType ORANGE_FLARE = register(HWGMod.modResource("orange_flare"), false);
    public static final SimpleParticleType PURPLE_FLARE = register(HWGMod.modResource("purple_flare"), false);
    public static final SimpleParticleType YELLOW_FLARE = register(HWGMod.modResource("yellow_flare"), false);
    public static final SimpleParticleType MAGENTA_FLARE = register(HWGMod.modResource("magenta_flare"), false);
    public static final SimpleParticleType LIGHTBLUE_FLARE = register(HWGMod.modResource("lightblue_flare"), false);
    public static final SimpleParticleType LIGHTGRAY_FLARE = register(HWGMod.modResource("lightgray_flare"), false);

    private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
    }

    public static void initialize() {
    }
}
