package mod.azure.hwg.client;

import mod.azure.hwg.client.render.MercRender;
import mod.azure.hwg.client.render.SpyRender;
import mod.azure.hwg.client.render.TechnodemonGreaterRender;
import mod.azure.hwg.client.render.TechnodemonLesserRender;
import mod.azure.hwg.client.render.projectiles.BaseFlareRender;
import mod.azure.hwg.client.render.projectiles.BlazeRodRender;
import mod.azure.hwg.client.render.projectiles.BulletRender;
import mod.azure.hwg.client.render.projectiles.FireballRender;
import mod.azure.hwg.client.render.projectiles.FlameFiringRender;
import mod.azure.hwg.client.render.projectiles.GrenadeRender;
import mod.azure.hwg.client.render.projectiles.MBulletRender;
import mod.azure.hwg.client.render.projectiles.RocketRender;
import mod.azure.hwg.client.render.projectiles.ShellRender;
import mod.azure.hwg.client.render.weapons.FuelTankRender;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistry {

	public static void init() {
		EntityRendererRegistry.register(ProjectilesEntityRegister.BULLETS, (ctx) -> new BulletRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.FLARE, (ctx) -> new BaseFlareRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.MBULLETS, (ctx) -> new MBulletRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.BLAZEROD, (ctx) -> new BlazeRodRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.GRENADE, (ctx) -> new GrenadeRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.SHELL, (ctx) -> new ShellRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.FIREBALL, (ctx) -> new FireballRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.FIRING, (ctx) -> new FlameFiringRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.ROCKETS, (ctx) -> new RocketRender(ctx));

		EntityRendererRegistry.register(HWGMobs.TECHNOLESSER, (ctx) -> new TechnodemonLesserRender(ctx));

		EntityRendererRegistry.register(HWGMobs.TECHNOGREATER, (ctx) -> new TechnodemonGreaterRender(ctx));

		EntityRendererRegistry.register(HWGMobs.MERC, (ctx) -> new MercRender(ctx));

		EntityRendererRegistry.register(HWGMobs.SPY, (ctx) -> new SpyRender(ctx));

		EntityRendererRegistry.register(HWGMobs.FUELTANK, (ctx) -> new FuelTankRender(ctx));

	}

}
