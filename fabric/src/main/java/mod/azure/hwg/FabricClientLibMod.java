package mod.azure.hwg;

import mod.azure.azurelib.common.api.client.helper.ClientUtils;
import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.hwg.client.gui.GunTableScreen;
import mod.azure.hwg.client.render.FuelTankRender;
import mod.azure.hwg.client.render.HWGMobRender;
import mod.azure.hwg.client.render.projectiles.BaseFlareRender;
import mod.azure.hwg.client.render.projectiles.EmptyRender;
import mod.azure.hwg.client.render.projectiles.GrenadeRender;
import mod.azure.hwg.client.render.projectiles.RocketRender;
import mod.azure.hwg.entity.enums.EntityEnum;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.particle.BrimParticle;
import mod.azure.hwg.particle.FlareParticle;
import mod.azure.hwg.particle.WFlareParticle;
import mod.azure.hwg.util.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class FabricClientLibMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AzureLib.hasKeyBindsInitialized = true;
        new PacketHandler().registerMessages();
        MenuScreens.register(ModScreens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.BLACK_FLARE.get(), FlareParticle.BlackSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.BLUE_FLARE.get(), FlareParticle.BlueSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.BROWN_FLARE.get(), FlareParticle.BrownSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.CYAN_FLARE.get(), FlareParticle.CyanSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.GREEN_FLARE.get(), FlareParticle.GreenSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.LIGHTBLUE_FLARE.get(), FlareParticle.LightBlueSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.LIGHTGRAY_FLARE.get(), FlareParticle.LightGraySmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.LIME_FLARE.get(), FlareParticle.LimeSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.MAGENTA_FLARE.get(), FlareParticle.MagentaSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.ORANGE_FLARE.get(), FlareParticle.OrangeSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.PINK_FLARE.get(), FlareParticle.PinkSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.PURPLE_FLARE.get(), FlareParticle.PurpleSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.RED_FLARE.get(), FlareParticle.RedSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.WHITE_FLARE.get(), WFlareParticle.WhiteSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.YELLOW_FLARE.get(), FlareParticle.YellowSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.GRAY_FLARE.get(), FlareParticle.GraySmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.BRIM_ORANGE.get(), BrimParticle.OrangeSmokeFactory::new);
        ParticleFactoryRegistry.getInstance().register(HWGParticles.BRIM_RED.get(), BrimParticle.RedSmokeFactory::new);
        ItemProperties.register(
                HWGItems.SNIPER.get(), ResourceLocation.parse("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
                    if (livingEntity != null)
                        return isScoped() ? 1.0F : 0.0F;
                    return 0.0F;
                });
        EntityRendererRegistry.register(HWGProjectiles.BULLETS.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FLARE.get(), BaseFlareRender::new);
        EntityRendererRegistry.register(HWGProjectiles.MBULLETS.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.BLAZEROD.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.GRENADE.get(), GrenadeRender::new);
        EntityRendererRegistry.register(HWGProjectiles.SHELL.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FIREBALL.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.FIRING.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGProjectiles.ROCKETS.get(), RocketRender::new);
        EntityRendererRegistry.register(HWGProjectiles.SILVERBULLETS.get(), EmptyRender::new);
        EntityRendererRegistry.register(HWGMobs.TECHNOLESSER.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.DEMON));
        EntityRendererRegistry.register(HWGMobs.TECHNOGREATER.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.DEMON));
        EntityRendererRegistry.register(HWGMobs.MERC.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.ILLEAGER));
        EntityRendererRegistry.register(HWGMobs.SPY.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.ILLEAGER));
        EntityRendererRegistry.register(HWGMobs.FUELTANK.get(), FuelTankRender::new);
    }

    private static boolean isScoped() {
        return ClientUtils.SCOPE.isDown();
    }
}
