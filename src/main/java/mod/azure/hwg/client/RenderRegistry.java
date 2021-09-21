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

@SuppressWarnings("deprecation")
public class RenderRegistry {

	public static void init() {
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS, (ctx) -> new BulletRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLACK_FLARE,
				(ctx) -> new BlackFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLUE_FLARE,
				(ctx) -> new BlueFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BROWN_FLARE,
				(ctx) -> new BrownFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CYAN_FLARE,
				(ctx) -> new CyanFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.GRAY_FLARE,
				(ctx) -> new GrayFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.GREEN_FLARE,
				(ctx) -> new GreenFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.LIGHTBLUE_FLARE,
				(ctx) -> new LightblueFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.LIGHTGRAY_FLARE,
				(ctx) -> new LightgrayRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.LIME_FLARE,
				(ctx) -> new LimeFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.MAGENTA_FLARE,
				(ctx) -> new MagentaRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ORANGE_FLARE,
				(ctx) -> new OrangeFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.PINK_FLARE,
				(ctx) -> new PinkFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.PURPLE_FLARE,
				(ctx) -> new PurpleFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.RED_FLARE, (ctx) -> new RedFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.WHITE_FLARE,
				(ctx) -> new WhiteFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.YELLOW_FLARE,
				(ctx) -> new YellowFlareRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.MBULLETS, (ctx) -> new MBulletRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLAZEROD, (ctx) -> new BlazeRodRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SMOKE_GRENADE,
				(ctx) -> new GSmokeRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.STUN_GRENADE, (ctx) -> new GStunRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.NAPALM_GRENADE,
				(ctx) -> new GNapalmRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.EMP_GRENADE, (ctx) -> new GEMPRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FRAG_GRENADE, (ctx) -> new GFragRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SHELL, (ctx) -> new ShellRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIREBALL, (ctx) -> new FireballRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIRING, (ctx) -> new FlameFiringRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ROCKETS, (ctx) -> new RocketRender(ctx));

		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOLESSER, (ctx) -> new TechnodemonLesserRender(ctx));

		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOGREATER, (ctx) -> new TechnodemonGreaterRender(ctx));

		EntityRendererRegistry.INSTANCE.register(HWGMobs.MERC, (ctx) -> new MercRender(ctx));

		EntityRendererRegistry.INSTANCE.register(HWGMobs.SPY, (ctx) -> new SpyRender(ctx));

		EntityRendererRegistry.INSTANCE.register(HWGMobs.FUELTANK, (ctx) -> new FuelTankRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SMOKE_GRENADE_S,
				(ctx) -> new GSmokeSRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.STUN_GRENADE_S,
				(ctx) -> new GStunSRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.NAPALM_GRENADE_S,
				(ctx) -> new GNapalmSRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.EMP_GRENADE_S,
				(ctx) -> new GEMPSRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FRAG_GRENADE_S,
				(ctx) -> new GFragSRender(ctx));

	}

}
