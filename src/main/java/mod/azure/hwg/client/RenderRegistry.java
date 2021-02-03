package mod.azure.hwg.client;

import mod.azure.hwg.client.render.TechnodemonGreater1Render;
import mod.azure.hwg.client.render.TechnodemonGreater2Render;
import mod.azure.hwg.client.render.TechnodemonLesser1Render;
import mod.azure.hwg.client.render.TechnodemonLesser2Render;
import mod.azure.hwg.client.render.TechnodemonLesser3Render;
import mod.azure.hwg.client.render.TechnodemonLesser4Render;
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
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOLESSER1, (dispatcher, context) -> {
			return new TechnodemonLesser1Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOLESSER2, (dispatcher, context) -> {
			return new TechnodemonLesser2Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOLESSER3, (dispatcher, context) -> {
			return new TechnodemonLesser3Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOLESSER4, (dispatcher, context) -> {
			return new TechnodemonLesser4Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOGREATER1, (dispatcher, context) -> {
			return new TechnodemonGreater1Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(HWGMobs.TECHNOGREATER2, (dispatcher, context) -> {
			return new TechnodemonGreater2Render(dispatcher);
		});
	}

}
