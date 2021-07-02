package mod.azure.hwg.client;

import mod.azure.hwg.client.render.MercRender;
import mod.azure.hwg.client.render.SpyRender;
import mod.azure.hwg.client.render.TechnodemonGreaterRender;
import mod.azure.hwg.client.render.TechnodemonLesserRender;
import mod.azure.hwg.client.render.projectiles.BlazeRodRender;
import mod.azure.hwg.client.render.projectiles.BulletRender;
import mod.azure.hwg.client.render.projectiles.FireballRender;
import mod.azure.hwg.client.render.projectiles.FlameFiringRender;
import mod.azure.hwg.client.render.projectiles.GEMPRender;
import mod.azure.hwg.client.render.projectiles.GEMPSRender;
import mod.azure.hwg.client.render.projectiles.GFragRender;
import mod.azure.hwg.client.render.projectiles.GFragSRender;
import mod.azure.hwg.client.render.projectiles.GNapalmRender;
import mod.azure.hwg.client.render.projectiles.GNapalmSRender;
import mod.azure.hwg.client.render.projectiles.GSmokeRender;
import mod.azure.hwg.client.render.projectiles.GSmokeSRender;
import mod.azure.hwg.client.render.projectiles.GStunRender;
import mod.azure.hwg.client.render.projectiles.GStunSRender;
import mod.azure.hwg.client.render.projectiles.MBulletRender;
import mod.azure.hwg.client.render.projectiles.RocketRender;
import mod.azure.hwg.client.render.projectiles.ShellRender;
import mod.azure.hwg.client.render.projectiles.flare.BlackFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.BlueFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.BrownFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.CyanFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.GrayFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.GreenFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.LightblueFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.LightgrayRender;
import mod.azure.hwg.client.render.projectiles.flare.LimeFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.MagentaRender;
import mod.azure.hwg.client.render.projectiles.flare.OrangeFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.PinkFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.PurpleFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.RedFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.WhiteFlareRender;
import mod.azure.hwg.client.render.projectiles.flare.YellowFlareRender;
import mod.azure.hwg.client.render.weapons.FuelTankRender;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class RenderRegistry {

	public static void init() {
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS, (dispatcher, context) -> {
			return new BulletRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLACK_FLARE, (dispatcher, context) -> {
			return new BlackFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLUE_FLARE, (dispatcher, context) -> {
			return new BlueFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BROWN_FLARE, (dispatcher, context) -> {
			return new BrownFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CYAN_FLARE, (dispatcher, context) -> {
			return new CyanFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.GRAY_FLARE, (dispatcher, context) -> {
			return new GrayFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.GREEN_FLARE, (dispatcher, context) -> {
			return new GreenFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.LIGHTBLUE_FLARE, (dispatcher, context) -> {
			return new LightblueFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.LIGHTGRAY_FLARE, (dispatcher, context) -> {
			return new LightgrayRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.LIME_FLARE, (dispatcher, context) -> {
			return new LimeFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.MAGENTA_FLARE, (dispatcher, context) -> {
			return new MagentaRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ORANGE_FLARE, (dispatcher, context) -> {
			return new OrangeFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.PINK_FLARE, (dispatcher, context) -> {
			return new PinkFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.PURPLE_FLARE, (dispatcher, context) -> {
			return new PurpleFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.RED_FLARE, (dispatcher, context) -> {
			return new RedFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.WHITE_FLARE, (dispatcher, context) -> {
			return new WhiteFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.YELLOW_FLARE, (dispatcher, context) -> {
			return new YellowFlareRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.MBULLETS, (dispatcher, context) -> {
			return new MBulletRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLAZEROD, (dispatcher, context) -> {
			return new BlazeRodRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SMOKE_GRENADE, (dispatcher, context) -> {
			return new GSmokeRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.STUN_GRENADE, (dispatcher, context) -> {
			return new GStunRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.NAPALM_GRENADE, (dispatcher, context) -> {
			return new GNapalmRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.EMP_GRENADE, (dispatcher, context) -> {
			return new GEMPRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FRAG_GRENADE, (dispatcher, context) -> {
			return new GFragRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SHELL, (dispatcher, context) -> {
			return new ShellRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIREBALL, (dispatcher, context) -> {
			return new FireballRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIRING, (dispatcher, context) -> {
			return new FlameFiringRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ROCKETS, (dispatcher, context) -> {
			return new RocketRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOLESSER, (dispatcher, context) -> {
			return new TechnodemonLesserRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOGREATER, (dispatcher, context) -> {
			return new TechnodemonGreaterRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.MERC, (dispatcher, context) -> {
			return new MercRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.SPY, (dispatcher, context) -> {
			return new SpyRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.FUELTANK, (dispatcher, context) -> {
			return new FuelTankRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SMOKE_GRENADE_S, (dispatcher, context) -> {
			return new GSmokeSRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.STUN_GRENADE_S, (dispatcher, context) -> {
			return new GStunSRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.NAPALM_GRENADE_S, (dispatcher, context) -> {
			return new GNapalmSRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.EMP_GRENADE_S, (dispatcher, context) -> {
			return new GEMPSRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FRAG_GRENADE_S, (dispatcher, context) -> {
			return new GFragSRender(dispatcher);
		});
	}

}
