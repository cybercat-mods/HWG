package mod.azure.hwg.util.registry;

import java.util.function.Supplier;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.particle.HWGParticleType;
import mod.azure.hwg.util.registry.interfaces.CommonParticleRegistryInterface;

public record HWGParticles() {

    public static final Supplier<HWGParticleType> BRIM_RED = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "brim_red",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> RED_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "red_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> BLUE_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "blue_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> CYAN_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "cyan_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> GRAY_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "gray_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> LIME_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "lime_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> PINK_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "pink_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> BLACK_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "black_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> BRIM_ORANGE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "brim_orange",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> BROWN_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "brown_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> GREEN_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "green_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> WHITE_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "white_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> ORANGE_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "orange_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> PURPLE_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "purple_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> YELLOW_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "yellow_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> MAGENTA_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "magenta_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> LIGHTBLUE_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "lightblue_flare",
        () -> new HWGParticleType(true)
    );

    public static final Supplier<HWGParticleType> LIGHTGRAY_FLARE = CommonParticleRegistryInterface.registerParticle(
        CommonMod.MOD_ID,
        "lightgray_flare",
        () -> new HWGParticleType(true)
    );

    public static void init() {}
}
