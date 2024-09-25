package mod.azure.hwg;

import mod.azure.azurelib.common.api.client.helper.ClientUtils;
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
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = CommonMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public record NeoForgeClientMod() {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HWGProjectiles.BULLETS.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGProjectiles.FLARE.get(), BaseFlareRender::new);
        event.registerEntityRenderer(HWGProjectiles.MBULLETS.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGProjectiles.BLAZEROD.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGProjectiles.GRENADE.get(), GrenadeRender::new);
        event.registerEntityRenderer(HWGProjectiles.SHELL.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGProjectiles.FIREBALL.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGProjectiles.FIRING.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGProjectiles.ROCKETS.get(), RocketRender::new);
        event.registerEntityRenderer(HWGProjectiles.SILVERBULLETS.get(), EmptyRender::new);
        event.registerEntityRenderer(HWGMobs.TECHNOLESSER.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.DEMON));
        event.registerEntityRenderer(HWGMobs.TECHNOGREATER.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.DEMON));
        event.registerEntityRenderer(HWGMobs.MERC.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.ILLEAGER));
        event.registerEntityRenderer(HWGMobs.SPY.get(), ctx -> new HWGMobRender<>(ctx, EntityEnum.ILLEAGER));
        event.registerEntityRenderer(HWGMobs.FUELTANK.get(), FuelTankRender::new);
    }

    @SubscribeEvent
    public static void registerScreens(final RegisterMenuScreensEvent event){
        event.register(ModScreens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemProperties.register(
                HWGItems.SNIPER.get(), ResourceLocation.parse("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
                    if (livingEntity != null)
                        return isScoped() ? 1.0F : 0.0F;
                    return 0.0F;
                });
        new PacketHandler().registerMessages();
    }

    @SubscribeEvent
    public static void registry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(HWGParticles.BLACK_FLARE.get(), FlareParticle.BlackSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.BLUE_FLARE.get(), FlareParticle.BlueSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.BROWN_FLARE.get(), FlareParticle.BrownSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.CYAN_FLARE.get(), FlareParticle.CyanSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.GREEN_FLARE.get(), FlareParticle.GreenSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.LIGHTBLUE_FLARE.get(), FlareParticle.LightBlueSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.LIGHTGRAY_FLARE.get(), FlareParticle.LightGraySmokeFactory::new);
        event.registerSpriteSet(HWGParticles.LIME_FLARE.get(), FlareParticle.LimeSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.MAGENTA_FLARE.get(), FlareParticle.MagentaSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.ORANGE_FLARE.get(), FlareParticle.OrangeSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.PINK_FLARE.get(), FlareParticle.PinkSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.PURPLE_FLARE.get(), FlareParticle.PurpleSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.RED_FLARE.get(), FlareParticle.RedSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.WHITE_FLARE.get(), WFlareParticle.WhiteSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.YELLOW_FLARE.get(), FlareParticle.YellowSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.GRAY_FLARE.get(), FlareParticle.GraySmokeFactory::new);
        event.registerSpriteSet(HWGParticles.BRIM_ORANGE.get(), BrimParticle.OrangeSmokeFactory::new);
        event.registerSpriteSet(HWGParticles.BRIM_RED.get(), BrimParticle.RedSmokeFactory::new);
    }

    private static boolean isScoped() {
        return ClientUtils.SCOPE.isDown();
    }
}
