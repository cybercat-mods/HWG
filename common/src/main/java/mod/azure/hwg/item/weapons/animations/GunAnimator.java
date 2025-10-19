package mod.azure.hwg.item.weapons.animations;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.common.animation.impl.AzItemAnimator;
import mod.azure.azurelib.common.util.client.ClientUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import mod.azure.hwg.item.weapons.FlareGunItem;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;

public class GunAnimator extends AzItemAnimator {

    private static final ResourceLocation PISTOL_ANIMATIONS = CommonMod.modResource(
        "animations/item/pistol/pistol.animation.json"
    );

    private static final ResourceLocation MEANIE_ANIMATIONS = CommonMod.modResource(
        "animations/item/meanie/meanie.animation.json"
    );

    private static final ResourceLocation HELL_ANIMATIONS = CommonMod.modResource(
        "animations/item/hellhorse_revolver/hellhorse_revolver.animation.json"
    );

    private static final ResourceLocation GOLDEN_PISTOL_ANIMATIONS = CommonMod.modResource(
        "animations/item/golden_gun/golden_gun.animation.json"
    );

    private static final ResourceLocation FLARE_GUN_ANIMATIONS = CommonMod.modResource(
        "animations/item/flare_gun/flare_gun.animation.json"
    );

    private static final ResourceLocation FLAMETHROWER_ANIMATIONS = CommonMod.modResource(
        "animations/item/flamethrower/flamethrower.animation.json"
    );

    private static final ResourceLocation BRIMSTONE_GUN_ANIMATIONS = CommonMod.modResource(
        "animations/item/brimstone_gun/brimstone_gun.animation.json"
    );

    private static final ResourceLocation BALROG_GUN_ANIMATIONS = CommonMod.modResource(
        "animations/item/balrog_gun/balrog_gun.animation.json"
    );

    private static final ResourceLocation AK47_ANIMATIONS = CommonMod.modResource(
        "animations/item/ak47/ak47.animation.json"
    );

    private static final ResourceLocation GRENADE_LAUNCHER_ANIMATIONS = CommonMod.modResource(
        "animations/item/grenade_launcher/grenade_launcher.animation.json"
    );

    private static final ResourceLocation LUGER_ANIMATIONS = CommonMod.modResource(
        "animations/item/luger/luger.animation.json"
    );

    private static final ResourceLocation MINIGUN_ANIMATIONS = CommonMod.modResource(
        "animations/item/minigun/minigun.animation.json"
    );

    private static final ResourceLocation NOSTROMO_FLAMETHROWER_ANIMATIONS = CommonMod.modResource(
        "animations/item/nostromo_flamethrower/nostromo_flamethrower.animation.json"
    );

    private static final ResourceLocation ROCKET_LAUNCHER_ANIMATIONS = CommonMod.modResource(
        "animations/item/rocketlauncher/rocketlauncher.animation.json"
    );

    private static final ResourceLocation SHOTGUN_ANIMATIONS = CommonMod.modResource(
        "animations/item/shotgun/shotgun.animation.json"
    );

    private static final ResourceLocation SMG_ANIMATIONS = CommonMod.modResource(
        "animations/item/smg/smg.animation.json"
    );

    private static final ResourceLocation SNIPER_RIFLE_ANIMATIONS = CommonMod.modResource(
        "animations/item/sniper_rifle/sniper_rifle.animation.json"
    );

    private static final ResourceLocation TOMMY_GUN_ANIMATIONS = CommonMod.modResource(
        "animations/item/tommy_gun/tommy_gun.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .setTransitionLength(0)
                .setKeyframeCallbacks(
                    AzKeyframeCallbacks.<ItemStack>builder()
                        .setSoundKeyframeHandler(
                            event -> {
                                if (event.getKeyframeData().getSound().equals("tank")) {
                                    ClientUtils.getClientPlayer()
                                        .level()
                                        .playLocalSound(
                                            ClientUtils.getClientPlayer().getX(),
                                            ClientUtils.getClientPlayer().getY(),
                                            ClientUtils.getClientPlayer().getZ(),
                                            SoundEvents.METAL_PLACE,
                                            SoundSource.HOSTILE,
                                            0.5F,
                                            1.0F,
                                            true
                                        );
                                }
                            }
                        )
                        .build()
                )
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(ItemStack animatable) {
        if (animatable.getItem() instanceof FlareGunItem flareGunItem) {
            return FLARE_GUN_ANIMATIONS;
        }
        if (animatable.getItem() instanceof GrenadeLauncherItem grenadeLauncherItem) {
            return GRENADE_LAUNCHER_ANIMATIONS;
        }
        if (animatable.getItem() instanceof AzureAnimatedGunItem gunItem) {
            return switch (gunItem.getGunTypeEnum()) {
                case PISTOL, SIL_PISTOL, SILVER_PISTOL -> PISTOL_ANIMATIONS;
                case MEANIE -> MEANIE_ANIMATIONS;
                case HELLHORSE, SILVER_HELL -> HELL_ANIMATIONS;
                case ROCKETLAUNCHER -> ROCKET_LAUNCHER_ANIMATIONS;
                case AK7 -> AK47_ANIMATIONS;
                case SMG -> SMG_ANIMATIONS;
                case LUGER -> LUGER_ANIMATIONS;
                case BALROG -> BALROG_GUN_ANIMATIONS;
                case SNIPER -> SNIPER_RIFLE_ANIMATIONS;
                case SHOTGUN -> SHOTGUN_ANIMATIONS;
                case MINIGUN -> MINIGUN_ANIMATIONS;
                case TOMMYGUN -> TOMMY_GUN_ANIMATIONS;
                case BRIMSTONE -> BRIMSTONE_GUN_ANIMATIONS;
                case FLAMETHROWER -> FLAMETHROWER_ANIMATIONS;
                case GOLDEN_PISTOL -> GOLDEN_PISTOL_ANIMATIONS;
                default -> NOSTROMO_FLAMETHROWER_ANIMATIONS;
            };
        }
        return PISTOL_ANIMATIONS;
    }
}
