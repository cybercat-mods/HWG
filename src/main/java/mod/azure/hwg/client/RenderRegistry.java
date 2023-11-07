package mod.azure.hwg.client;

import mod.azure.hwg.client.render.MercRender;
import mod.azure.hwg.client.render.SpyRender;
import mod.azure.hwg.client.render.TechnodemonGreaterRender;
import mod.azure.hwg.client.render.TechnodemonLesserRender;
import mod.azure.hwg.client.render.projectiles.*;
import mod.azure.hwg.client.render.weapons.FuelTankRender;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public record RenderRegistry() {

    public static void init() {
        EntityRendererRegistry.register(HWGProjectiles.BULLETS, BulletRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FLARE, BaseFlareRender::new);
        EntityRendererRegistry.register(HWGProjectiles.MBULLETS, MBulletRender::new);
        EntityRendererRegistry.register(HWGProjectiles.BLAZEROD, BlazeRodRender::new);
        EntityRendererRegistry.register(HWGProjectiles.GRENADE, GrenadeRender::new);
        EntityRendererRegistry.register(HWGProjectiles.SHELL, ShellRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FIREBALL, FireballRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FIRING, FlameFiringRender::new);
        EntityRendererRegistry.register(HWGProjectiles.ROCKETS, RocketRender::new);
        EntityRendererRegistry.register(HWGMobs.TECHNOLESSER, TechnodemonLesserRender::new);
        EntityRendererRegistry.register(HWGMobs.TECHNOGREATER, TechnodemonGreaterRender::new);
        EntityRendererRegistry.register(HWGMobs.MERC, MercRender::new);
        EntityRendererRegistry.register(HWGMobs.SPY, SpyRender::new);
        EntityRendererRegistry.register(HWGMobs.FUELTANK, FuelTankRender::new);

    }

}
