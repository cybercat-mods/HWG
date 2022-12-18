package mod.azure.hwg.compat;

import mod.azure.hwg.client.render.projectiles.SBulletRender;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BWClientCompat {

	public static void onInitializeClient() {
		EntityRendererRegistry.register(BWCompat.SILVERBULLETS, (ctx) -> new SBulletRender(ctx));
	}
}
