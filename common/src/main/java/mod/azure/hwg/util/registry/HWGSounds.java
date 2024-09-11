package mod.azure.hwg.util.registry;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.util.registry.interfaces.CommonSoundRegistryInterface;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public record HWGSounds() {

    public static final Supplier<SoundEvent> AK = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.ak", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.ak")));
    public static final Supplier<SoundEvent> RPG = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.rpg", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.rpg")));
    public static final Supplier<SoundEvent> SMG = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.smg", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.smg")));
    public static final Supplier<SoundEvent> BONK = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.bonk", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.bonk")));
    public static final Supplier<SoundEvent> LUGER = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.luger", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.luger")));
    public static final Supplier<SoundEvent> TOMMY = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.tommy", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.tommy")));
    public static final Supplier<SoundEvent> PISTOL = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.pistol", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.pistol")));
    public static final Supplier<SoundEvent> SNIPER = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.sniper", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.sniper")));
    public static final Supplier<SoundEvent> MINIGUN = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.minigun", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.minigun")));
    public static final Supplier<SoundEvent> SHOTGUN = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.shotgun", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.shotgun")));
    public static final Supplier<SoundEvent> SPISTOL = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.spistol", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.spistol")));
    public static final Supplier<SoundEvent> REVOLVER = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.revolver", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.revolver")));
    public static final Supplier<SoundEvent> FLAREGUN = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.flare_gun", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.flare_gun")));
    public static final Supplier<SoundEvent> CLIPRELOAD = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.clipreload", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.clipreload")));
    public static final Supplier<SoundEvent> PISTOLRELOAD = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.pistolreload", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.pistolreload")));
    public static final Supplier<SoundEvent> SNIPERRELOAD = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.sniperreload", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.sniperreload")));
    public static final Supplier<SoundEvent> FLAMETHROWER = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.flamethrower", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.flamethrower")));
    public static final Supplier<SoundEvent> SHOTGUNRELOAD = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.shotgunreload", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.shotgunreload")));
    public static final Supplier<SoundEvent> GLAUNCHERFIRE = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.glauncher-fire", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.glauncher-fire")));
    public static final Supplier<SoundEvent> REVOLVERRELOAD = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.revolverreload", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.revolverreload")));
    public static final Supplier<SoundEvent> FLAREGUN_SHOOT = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.flare_gun_shoot", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.flare_gun_shoot")));
    public static final Supplier<SoundEvent> GLAUNCHERRELOAD = CommonSoundRegistryInterface.registerSound(CommonMod.MOD_ID, "hwg.glauncher-reload", () -> SoundEvent.createVariableRangeEvent(
            CommonMod.modResource("hwg.glauncher-reload")));

    public static void init() {
    }
}
