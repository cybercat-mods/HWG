package mod.azure.hwg.client;

import mod.azure.hwg.client.render.projectiles.SilverBulletRender;
import mod.azure.hwg.client.render.weapons.SHellRender;
import mod.azure.hwg.client.render.weapons.SilverGunRender;
import mod.azure.hwg.util.registry.BWCompatItems;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class BWClientCompat {

	public static void onInitializeClient() {
		GeoItemRenderer.registerItemRenderer(BWCompatItems.SILVERGUN, new SilverGunRender());
		GeoItemRenderer.registerItemRenderer(BWCompatItems.SILVERHELLHORSE, new SHellRender());
		EntityRendererRegistry.INSTANCE.register(BWCompatItems.SILVERBULLETS, (dispatcher, context) -> {
			return new SilverBulletRender(dispatcher);
		});
	}
}
