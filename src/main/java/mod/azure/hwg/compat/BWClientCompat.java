package mod.azure.hwg.compat;

import mod.azure.hwg.client.render.projectiles.SBulletRender;
import mod.azure.hwg.client.render.weapons.SilverGunRender;
import mod.azure.hwg.client.render.weapons.SilverRevolverRender;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BWClientCompat {

	public static void onInitializeClient() {
		GeoItemRenderer.registerItemRenderer(BWCompat.SILVERGUN, new SilverGunRender());
		GeoItemRenderer.registerItemRenderer(BWCompat.SILVERHELLHORSE, new SilverRevolverRender());
		EntityRendererRegistry.register(BWCompat.SILVERBULLETS, (ctx) -> new SBulletRender(ctx));
	}
}
