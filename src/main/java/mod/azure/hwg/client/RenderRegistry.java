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
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistry {

	public static void init() {
		EntityRendererRegistry.register(HWGProjectiles.BULLETS, (ctx) -> new BulletRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.FLARE, (ctx) -> new BaseFlareRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.MBULLETS, (ctx) -> new MBulletRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.BLAZEROD, (ctx) -> new BlazeRodRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.GRENADE, (ctx) -> new GrenadeRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.SHELL, (ctx) -> new ShellRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.FIREBALL, (ctx) -> new FireballRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.FIRING, (ctx) -> new FlameFiringRender(ctx));

		EntityRendererRegistry.register(HWGProjectiles.ROCKETS, (ctx) -> new RocketRender(ctx));

		EntityRendererRegistry.register(HWGMobs.TECHNOLESSER, (ctx) -> new TechnodemonLesserRender(ctx));

		EntityRendererRegistry.register(HWGMobs.TECHNOGREATER, (ctx) -> new TechnodemonGreaterRender(ctx));

		EntityRendererRegistry.register(HWGMobs.MERC, (ctx) -> new MercRender(ctx));

		EntityRendererRegistry.register(HWGMobs.SPY, (ctx) -> new SpyRender(ctx));

		EntityRendererRegistry.register(HWGMobs.FUELTANK, (ctx) -> new FuelTankRender(ctx));

	}

}
