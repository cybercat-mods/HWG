package mod.azure.hwg.client;

import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import com.mojang.blaze3d.platform.InputUtil;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.gui.GunTableScreen;
import mod.azure.hwg.client.render.weapons.AKRender;
import mod.azure.hwg.client.render.weapons.FlareGunRender;
import mod.azure.hwg.client.render.weapons.GPistolRender;
import mod.azure.hwg.client.render.weapons.GrenadeLauncherRender;
import mod.azure.hwg.client.render.weapons.HellRender;
import mod.azure.hwg.client.render.weapons.LugerRender;
import mod.azure.hwg.client.render.weapons.Meanie1Render;
import mod.azure.hwg.client.render.weapons.Meanie2Render;
import mod.azure.hwg.client.render.weapons.MinigunRender;
import mod.azure.hwg.client.render.weapons.PistolRender;
import mod.azure.hwg.client.render.weapons.SMGRender;
import mod.azure.hwg.client.render.weapons.SPistolRender;
import mod.azure.hwg.client.render.weapons.ShotgunRender;
import mod.azure.hwg.client.render.weapons.SniperRender;
import mod.azure.hwg.client.render.weapons.TommyGunRender;
import mod.azure.hwg.compat.BWClientCompat;
import mod.azure.hwg.particle.BrimParticle;
import mod.azure.hwg.particle.FlareParticle;
import mod.azure.hwg.particle.WFlareParticle;
import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.packet.EntityPacketOnClient;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGParticles;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBind;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ClientInit implements ClientModInitializer {

	public static KeyBind reload = new KeyBind("key.hwg.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.hwg.binds");

	public static KeyBind scope = new KeyBind("key.hwg.scope", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
			"category.hwg.binds");

	@Override
	public void onInitializeClient(ModContainer mod) {
		ModelProviderinit.init();
		RenderRegistry.init();
		HandledScreens.register(HWGMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		GeoItemRenderer.registerItemRenderer(HWGItems.PISTOL, new PistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.GOLDEN_GUN, new GPistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.LUGER, new LugerRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.SPISTOL, new SPistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.SHOTGUN, new ShotgunRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.AK47, new AKRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.SMG, new SMGRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.TOMMYGUN, new TommyGunRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.SNIPER, new SniperRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.HELLHORSE, new HellRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.MINIGUN, new MinigunRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.G_LAUNCHER, new GrenadeLauncherRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.MEANIE1, new Meanie1Render());
		GeoItemRenderer.registerItemRenderer(HWGItems.MEANIE2, new Meanie2Render());
		GeoItemRenderer.registerItemRenderer(HWGItems.FLARE_GUN, new FlareGunRender());
		ClientPlayNetworking.registerGlobalReceiver(EntityPacket.ID, (client, handler, buf, responseSender) -> {
			EntityPacketOnClient.onPacket(client, buf);
		});
		if (QuiltLoader.isModLoaded("bewitchment")) {
			BWClientCompat.onInitializeClient();
		}
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(scope);
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)
				.register(((atlasTexture, registry) -> {
					registry.register(new Identifier("hwg", "particle/big_smoke_0"));
				}));
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