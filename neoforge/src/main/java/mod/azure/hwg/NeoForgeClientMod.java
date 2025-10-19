package mod.azure.hwg;

import com.mojang.blaze3d.platform.InputConstants;
import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.lwjgl.glfw.GLFW;

import mod.azure.hwg.client.HWGKeybinds;
import mod.azure.hwg.client.gui.GunTableScreen;
import mod.azure.hwg.client.render.*;
import mod.azure.hwg.client.render.projectiles.BaseFlareRender;
import mod.azure.hwg.client.render.projectiles.EmptyRender;
import mod.azure.hwg.client.render.projectiles.GrenadeRender;
import mod.azure.hwg.client.render.projectiles.RocketRender;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.network.PacketHandler;
import mod.azure.hwg.particle.BrimParticle;
import mod.azure.hwg.particle.FlareParticle;
import mod.azure.hwg.particle.WFlareParticle;
import mod.azure.hwg.util.registry.*;

@EventBusSubscriber(modid = CommonMod.MOD_ID, value = Dist.CLIENT)
public record NeoForgeClientMod() {

    @SubscribeEvent
    public static void registerKeys(final RegisterKeyMappingsEvent event) {
        HWGKeybinds.RELOAD = new KeyMapping(
            "key.hwg.reload",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.hwg.binds"
        );
        event.register(HWGKeybinds.RELOAD);
        HWGKeybinds.SCOPE = new KeyMapping(
            "key.hwg.scope",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_ALT,
            "category.hwg.binds"
        );
        event.register(HWGKeybinds.SCOPE);
    }

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
        event.registerEntityRenderer(HWGMobs.TECHNOLESSER.get(), TechnodemonRender::new);
        event.registerEntityRenderer(HWGMobs.TECHNOGREATER.get(), TechnodemonGreaterRender::new);
        event.registerEntityRenderer(HWGMobs.MERC.get(), MercRender::new);
        event.registerEntityRenderer(HWGMobs.SPY.get(), SpyRender::new);
        event.registerEntityRenderer(HWGMobs.FUELTANK.get(), FuelTankRender::new);
        AzItemRendererRegistry.register(HWGItems.FLARE_GUN.get(), () -> new GunRender("flare_gun", GunTypeEnum.FLARE));
        AzItemRendererRegistry.register(
            HWGItems.G_LAUNCHER.get(),
            () -> new GunRender("grenade_launcher", GunTypeEnum.NADELAUNCHER)
        );
        AzItemRendererRegistry.register(HWGItems.PISTOL.get(), () -> new GunRender("pistol", GunTypeEnum.PISTOL));
        AzItemRendererRegistry.register(
            HWGItems.FLAMETHROWER.get(),
            () -> new GunRender("flamethrower", GunTypeEnum.FLAMETHROWER)
        );
        AzItemRendererRegistry.register(HWGItems.MINIGUN.get(), () -> new GunRender("minigun", GunTypeEnum.MINIGUN));
        AzItemRendererRegistry.register(HWGItems.LUGER.get(), () -> new GunRender("luger", GunTypeEnum.LUGER));
        AzItemRendererRegistry.register(HWGItems.PISTOL.get(), () -> new GunRender("pistol", GunTypeEnum.PISTOL));
        AzItemRendererRegistry.register(HWGItems.SHOTGUN.get(), () -> new GunRender("shotgun", GunTypeEnum.SHOTGUN));
        AzItemRendererRegistry.register(HWGItems.SPISTOL.get(), () -> new GunRender("spistol", GunTypeEnum.SIL_PISTOL));
        AzItemRendererRegistry.register(HWGItems.SNIPER.get(), () -> new GunRender("sniper_rifle", GunTypeEnum.SNIPER));
        AzItemRendererRegistry.register(
            HWGItems.MEANIE1.get(),
            () -> new GunRender("meanie_gun_1", GunTypeEnum.MEANIE)
        );
        AzItemRendererRegistry.register(
            HWGItems.MEANIE2.get(),
            () -> new GunRender("meanie_gun_2", GunTypeEnum.MEANIE)
        );
        AzItemRendererRegistry.register(
            HWGItems.GOLDEN_GUN.get(),
            () -> new GunRender("golden_gun", GunTypeEnum.GOLDEN_PISTOL)
        );
        AzItemRendererRegistry.register(
            HWGItems.ROCKETLAUNCHER.get(),
            () -> new GunRender("rocketlauncher", GunTypeEnum.ROCKETLAUNCHER)
        );
        AzItemRendererRegistry.register(
            HWGItems.HELLHORSE.get(),
            () -> new GunRender("hellhorse_revolver", GunTypeEnum.HELLHORSE)
        );
        AzItemRendererRegistry.register(
            HWGItems.SILVERGUN.get(),
            () -> new GunRender("silvergun", GunTypeEnum.SILVER_PISTOL)
        );
        AzItemRendererRegistry.register(
            HWGItems.SILVERHELLHORSE.get(),
            () -> new GunRender("shellhorse_revolver", GunTypeEnum.SILVER_HELL)
        );
        AzItemRendererRegistry.register(HWGItems.AK47.get(), () -> new GunRender("ak47", GunTypeEnum.AK7));
        AzItemRendererRegistry.register(HWGItems.SMG.get(), () -> new GunRender("smg", GunTypeEnum.SMG));
        AzItemRendererRegistry.register(
            HWGItems.TOMMYGUN.get(),
            () -> new GunRender("tommy_gun", GunTypeEnum.TOMMYGUN)
        );
        AzItemRendererRegistry.register(HWGItems.BALROG.get(), () -> new GunRender("balrog_gun", GunTypeEnum.BALROG));
        AzItemRendererRegistry.register(
            HWGItems.BRIMSTONE.get(),
            () -> new GunRender("brimstone_gun", GunTypeEnum.BRIMSTONE)
        );
        AzItemRendererRegistry.register(
            HWGItems.INCINERATOR.get(),
            () -> new GunRender("nostromo_flamethrower", GunTypeEnum.FLAMETHROWER)
        );
    }

    @SubscribeEvent
    public static void registerScreens(final RegisterMenuScreensEvent event) {
        event.register(ModScreens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemProperties.register(
            HWGItems.SNIPER.get(),
            ResourceLocation.parse("scoped"),
            (itemStack, clientWorld, livingEntity, seed) -> {
                if (livingEntity != null)
                    return isScoped() ? 1.0F : 0.0F;
                return 0.0F;
            }
        );
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
        return HWGKeybinds.SCOPE.isDown();
    }
}
