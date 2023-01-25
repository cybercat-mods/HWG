package mod.azure.hwg.client;

import org.lwjgl.glfw.GLFW;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.gui.GunTableScreen;
import mod.azure.hwg.compat.BWClientCompat;
import mod.azure.hwg.particle.BrimParticle;
import mod.azure.hwg.particle.FlareParticle;
import mod.azure.hwg.particle.WFlareParticle;
import mod.azure.hwg.util.registry.HWGParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.hwg.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.hwg.binds");

	public static KeyBinding scope = new KeyBinding("key.hwg.scope", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
			"category.hwg.binds");

	@Override
	public void onInitializeClient() {
		ModelProviderinit.init();
		RenderRegistry.init();
		HandledScreens.register(HWGMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		if (FabricLoader.getInstance().isModLoaded("bewitchment")) {
			BWClientCompat.onInitializeClient();
		}
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(scope);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BLACK_FLARE, FlareParticle.BlackSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BLUE_FLARE, FlareParticle.BlueSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BROWN_FLARE, FlareParticle.BrownSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.CYAN_FLARE, FlareParticle.CyanSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.GREEN_FLARE, FlareParticle.GreenSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.LIGHTBLUE_FLARE,
				FlareParticle.LightBlueSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.LIGHTGRAY_FLARE,
				FlareParticle.LightGraySmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.LIME_FLARE, FlareParticle.LimeSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.MAGENTA_FLARE,
				FlareParticle.MagentaSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.ORANGE_FLARE,
				FlareParticle.OrangeSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.PINK_FLARE, FlareParticle.PinkSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.PURPLE_FLARE,
				FlareParticle.PurpleSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.RED_FLARE, FlareParticle.RedSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.WHITE_FLARE, WFlareParticle.WhiteSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.YELLOW_FLARE,
				FlareParticle.YellowSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.GRAY_FLARE, FlareParticle.GraySmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BRIM_ORANGE, BrimParticle.OrangeSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BRIM_RED, BrimParticle.RedSmokeFactory::new);
	}

}