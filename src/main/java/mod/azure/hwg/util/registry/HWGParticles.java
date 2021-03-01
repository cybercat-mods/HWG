package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGParticles {

	public static final DefaultParticleType BLACK_FLARE = register(new Identifier(HWGMod.MODID, "black_flare"), false);
	public static final DefaultParticleType RED_FLARE = register(new Identifier(HWGMod.MODID, "red_flare"), false);
	public static final DefaultParticleType GREEN_FLARE = register(new Identifier(HWGMod.MODID, "green_flare"), false);
	public static final DefaultParticleType BROWN_FLARE = register(new Identifier(HWGMod.MODID, "brown_flare"), false);
	public static final DefaultParticleType BLUE_FLARE = register(new Identifier(HWGMod.MODID, "blue_flare"), false);
	public static final DefaultParticleType PURPLE_FLARE = register(new Identifier(HWGMod.MODID, "purple_flare"), false);
	public static final DefaultParticleType CYAN_FLARE = register(new Identifier(HWGMod.MODID, "cyan_flare"), false);
	public static final DefaultParticleType LIGHTGRAY_FLARE = register(new Identifier(HWGMod.MODID, "lightgray_flare"), false);
	public static final DefaultParticleType GRAY_FLARE = register(new Identifier(HWGMod.MODID, "gray_flare"), false);
	public static final DefaultParticleType PINK_FLARE = register(new Identifier(HWGMod.MODID, "pink_flare"), false);
	public static final DefaultParticleType LIME_FLARE = register(new Identifier(HWGMod.MODID, "lime_flare"), false);
	public static final DefaultParticleType YELLOW_FLARE = register(new Identifier(HWGMod.MODID, "yellow_flare"), false);
	public static final DefaultParticleType LIGHTBLUE_FLARE = register(new Identifier(HWGMod.MODID, "lightblue_flare"), false);
	public static final DefaultParticleType MAGENTA_FLARE = register(new Identifier(HWGMod.MODID, "magenta_flare"), false);
	public static final DefaultParticleType ORANGE_FLARE = register(new Identifier(HWGMod.MODID, "orange_flare"), false);
	public static final DefaultParticleType WHITE_FLARE = register(new Identifier(HWGMod.MODID, "white_flare"), false);
	public static final DefaultParticleType BRIM_ORANGE = register(new Identifier(HWGMod.MODID, "brim_orange"), false);
	public static final DefaultParticleType BRIM_RED = register(new Identifier(HWGMod.MODID, "brim_red"), false);

	private static DefaultParticleType register(Identifier identifier, boolean alwaysSpawn) {
		return Registry.register(Registry.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
