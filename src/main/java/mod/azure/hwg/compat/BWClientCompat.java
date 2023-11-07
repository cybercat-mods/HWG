package mod.azure.hwg.compat;

import mod.azure.hwg.client.render.projectiles.SBulletRender;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public record BWClientCompat() {

    public static void onInitializeClient() {
        EntityRendererRegistry.register(BWCompat.SILVERBULLETS, SBulletRender::new);
    }
}
