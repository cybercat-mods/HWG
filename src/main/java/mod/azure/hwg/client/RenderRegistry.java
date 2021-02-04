package mod.azure.hwg.client;

import mod.azure.hwg.client.render.MercRender;
import mod.azure.hwg.client.render.SpyRender;
import mod.azure.hwg.client.render.TechnodemonGreaterRender;
import mod.azure.hwg.client.render.TechnodemonLesserRender;
import mod.azure.hwg.client.render.projectiles.BulletRender;
import mod.azure.hwg.client.render.projectiles.FlameFiringRender;
import mod.azure.hwg.util.HWGMobs;
import mod.azure.hwg.util.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class RenderRegistry {

	public static void init() {
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS, (dispatcher, context) -> {
			return new BulletRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIRING, (dispatcher, context) -> {
			return new FlameFiringRender(dispatcher);
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
	}

}
