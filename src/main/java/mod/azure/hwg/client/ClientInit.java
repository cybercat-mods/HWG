package mod.azure.hwg.client;

import org.lwjgl.glfw.GLFW;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.gui.GunTableScreen;
import mod.azure.hwg.client.render.weapons.FlamethrowerRender;
import mod.azure.hwg.client.render.weapons.PistolRender;
import mod.azure.hwg.client.render.weapons.SPistolRender;
import mod.azure.hwg.particle.FlareParticle;
import mod.azure.hwg.particle.WFlareParticle;
import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.HWGParticles;
import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.packet.EntityPacketOnClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

@SuppressWarnings("deprecation")
public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.hwg.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.hwg.binds");

	@Override
	public void onInitializeClient() {
		ModelProviderinit.init();
		RenderRegistry.init();
        ScreenRegistry.register(HWGMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		GeoItemRenderer.registerItemRenderer(HWGItems.PISTOL, new PistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.SPISTOL, new SPistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.FLAMETHROWER, new FlamethrowerRender());
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
			EntityPacketOnClient.onPacket(ctx, buf);
		});
		KeyBindingHelper.registerKeyBinding(reload);
		requestParticleTexture( new Identifier( "hwg:particle/big_smoke_0" ) );
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BLACK_FLARE, FlareParticle.BlackSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BLUE_FLARE, FlareParticle.BlueSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.BROWN_FLARE, FlareParticle.BrownSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.CYAN_FLARE, FlareParticle.CyanSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.GREEN_FLARE, FlareParticle.GreenSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.LIGHTBLUE_FLARE, FlareParticle.LightBlueSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.LIGHTGRAY_FLARE, FlareParticle.LightGraySmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.LIME_FLARE, FlareParticle.LimeSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.MAGENTA_FLARE, FlareParticle.MagentaSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.ORANGE_FLARE, FlareParticle.OrangeSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.PINK_FLARE, FlareParticle.PinkSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.PURPLE_FLARE, FlareParticle.PurpleSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.RED_FLARE, FlareParticle.RedSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.WHITE_FLARE, WFlareParticle.WhiteSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.YELLOW_FLARE, FlareParticle.YellowSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(HWGParticles.GRAY_FLARE, FlareParticle.GraySmokeFactory::new);
	}
	
	public static void requestParticleTexture( Identifier id ) {
	    ClientSpriteRegistryCallback.event(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE).register(((texture, registry) -> registry.register( id )));
	}


}