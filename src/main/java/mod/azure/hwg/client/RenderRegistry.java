package mod.azure.hwg.client;

import mod.azure.hwg.client.render.FuelTankRender;
import mod.azure.hwg.client.render.MercRender;
import mod.azure.hwg.client.render.SpyRender;
import mod.azure.hwg.client.render.TechnodemonGreaterRender;
import mod.azure.hwg.client.render.TechnodemonLesserRender;
import mod.azure.hwg.client.render.projectiles.BlazeRodRender;
import mod.azure.hwg.client.render.projectiles.BulletRender;
import mod.azure.hwg.client.render.projectiles.FireballRender;
import mod.azure.hwg.client.render.projectiles.FlameFiringRender;
import mod.azure.hwg.client.render.projectiles.GEMPRender;
import mod.azure.hwg.client.render.projectiles.GFragRender;
import mod.azure.hwg.client.render.projectiles.GNapalmRender;
import mod.azure.hwg.client.render.projectiles.GSmokeRender;
import mod.azure.hwg.client.render.projectiles.GStunRender;
import mod.azure.hwg.client.render.projectiles.MBulletRender;
import mod.azure.hwg.client.render.projectiles.RocketRender;
import mod.azure.hwg.client.render.projectiles.ShellRender;
import mod.azure.hwg.util.HWGMobs;
import mod.azure.hwg.util.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class RenderRegistry {

	public static void init() {
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS, (dispatcher, context) -> {
			return new BulletRender(dispatcher);
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
	}

}
