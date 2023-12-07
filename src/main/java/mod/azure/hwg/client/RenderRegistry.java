package mod.azure.hwg.client;

import mod.azure.hwg.client.render.FuelTankRender;
import mod.azure.hwg.client.render.HWGMobRender;
import mod.azure.hwg.client.render.projectiles.BaseFlareRender;
import mod.azure.hwg.client.render.projectiles.EmptyRender;
import mod.azure.hwg.client.render.projectiles.GrenadeRender;
import mod.azure.hwg.client.render.projectiles.RocketRender;
import mod.azure.hwg.entity.enums.EntityEnum;
import mod.azure.hwg.util.registry.HWGMobs;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public record RenderRegistry() {

    public static void init() {
        EntityRendererRegistry.register(HWGProjectiles.BULLETS, EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FLARE, BaseFlareRender::new);
        EntityRendererRegistry.register(HWGProjectiles.MBULLETS, EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.BLAZEROD, EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.GRENADE, GrenadeRender::new);
        EntityRendererRegistry.register(HWGProjectiles.SHELL, EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FIREBALL, EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FIRING, EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.ROCKETS, RocketRender::new);
        EntityRendererRegistry.register(HWGProjectiles.SILVERBULLETS, EmptyRender::new);
        EntityRendererRegistry.register(HWGMobs.TECHNOLESSER, ctx -> new HWGMobRender(ctx, EntityEnum.DEMON));
        EntityRendererRegistry.register(HWGMobs.TECHNOGREATER, ctx -> new HWGMobRender(ctx, EntityEnum.DEMON));
        EntityRendererRegistry.register(HWGMobs.MERC, ctx -> new HWGMobRender(ctx, EntityEnum.ILLEAGER));
        EntityRendererRegistry.register(HWGMobs.SPY, ctx -> new HWGMobRender(ctx, EntityEnum.ILLEAGER));
        EntityRendererRegistry.register(HWGMobs.FUELTANK, FuelTankRender::new);

    }

}
