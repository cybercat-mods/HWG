package mod.azure.hwg.util.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.item.ammo.*;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.item.enums.ProjectileEnum;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import mod.azure.hwg.item.weapons.FlareGunItem;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;
import mod.azure.hwg.platform.Services;
import mod.azure.hwg.util.registry.interfaces.CommonItemRegistryInterface;

public record HWGItems() {

    /**
     * Flares
     */
    public static final Supplier<FlareItem> RED_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "red_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> BLUE_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "blue_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> CYAN_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "cyan_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> GRAY_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "gray_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> LIME_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "lime_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> PINK_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "pink_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> BLACK_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "black_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> BROWN_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "brown_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> GREEN_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "green_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> WHITE_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "white_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> ORANGE_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "orange_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> PURPLE_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "purple_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> YELLOW_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "yellow_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> MAGENTA_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "magenta_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> LIGHTBLUE_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "lightblue_flare",
        FlareItem::new
    );

    public static final Supplier<FlareItem> LIGHTGRAY_FLARE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "lightgray_flare",
        FlareItem::new
    );

    /**
     * Ammo
     */
    public static final Supplier<Item> ROCKET = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "rocket",
        () -> new Item(new Item.Properties())
    );

    public static final Supplier<Item> BULLETS = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "bullets",
        () -> new Item(new Item.Properties())
    );

    public static final Supplier<Item> SNIPER_ROUND = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "sniper_round",
        () -> new Item(new Item.Properties())
    );

    public static final Supplier<Item> SHOTGUN_SHELL = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "shotgun_shell",
        () -> new Item(new Item.Properties())
    );

    public static final Supplier<Item> SILVERBULLET = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "silver_bullet",
        () -> new Item(new Item.Properties())
    );

    public static final Supplier<GrenadeEmpItem> G_EMP = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "grenade_emp",
        GrenadeEmpItem::new
    );

    public static final Supplier<GrenadeFragItem> G_FRAG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "grenade_frag",
        GrenadeFragItem::new
    );

    public static final Supplier<GrenadeStunItem> G_STUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "grenade_stun",
        GrenadeStunItem::new
    );

    public static final Supplier<GrenadeSmokeItem> G_SMOKE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "grenade_smoke",
        GrenadeSmokeItem::new
    );

    public static final Supplier<GrenadeNapalmItem> G_NAPALM = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "grenade_napalm",
        GrenadeNapalmItem::new
    );

    public static final Supplier<BlockItem> FUEL_TANK = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "fuel_tank",
        () -> new BlockItem(HWGBlocks.FUEL_TANK.get(), new Item.Properties())
    );

    /**
     * Guns
     */
    public static final Supplier<FlareGunItem> FLARE_GUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "flare_gun",
        FlareGunItem::new
    );

    public static final Supplier<GrenadeLauncherItem> G_LAUNCHER = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "grenade_launcher",
        GrenadeLauncherItem::new
    );

    public static final Supplier<AzureAnimatedGunItem> FLAMETHROWER = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "flamethrower",
        () -> new AzureAnimatedGunItem(
            "flamethrower",
            ProjectileEnum.FLAMES,
            GunTypeEnum.FLAMETHROWER,
            CommonMod.config.gunconfigs.flammerconfigs.flammer_cap,
            SoundEvents.METAL_PLACE,
            SoundEvents.FIRECHARGE_USE
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> MINIGUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "minigun",
        () -> new AzureAnimatedGunItem(
            "minigun",
            ProjectileEnum.BULLET,
            GunTypeEnum.MINIGUN,
            CommonMod.config.gunconfigs.minigunconfigs.minigun_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.MINIGUN.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> LUGER = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "luger",
        () -> new AzureAnimatedGunItem(
            "luger",
            ProjectileEnum.BULLET,
            GunTypeEnum.LUGER,
            CommonMod.config.gunconfigs.lugerconfigs.luger_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.LUGER.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> PISTOL = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "pistol",
        () -> new AzureAnimatedGunItem(
            "pistol",
            ProjectileEnum.BULLET,
            GunTypeEnum.PISTOL,
            CommonMod.config.gunconfigs.pistolconfigs.pistol_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.PISTOL.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> SHOTGUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "shotgun",
        () -> new AzureAnimatedGunItem(
            "shotgun",
            ProjectileEnum.SHELL,
            GunTypeEnum.SHOTGUN,
            CommonMod.config.gunconfigs.shotgunconfigs.shotgun_cap,
            HWGSounds.SHOTGUNRELOAD.get(),
            HWGSounds.SHOTGUN.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> SPISTOL = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "spistol",
        () -> new AzureAnimatedGunItem(
            "spistol",
            ProjectileEnum.BULLET,
            GunTypeEnum.SIL_PISTOL,
            CommonMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_cap,
            HWGSounds.PISTOLRELOAD.get(),
            HWGSounds.SPISTOL.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> SNIPER = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "sniper_rifle",
        () -> new AzureAnimatedGunItem(
            "sniper_rifle",
            ProjectileEnum.BULLET,
            GunTypeEnum.SNIPER,
            CommonMod.config.gunconfigs.sniperconfigs.sniper_cap,
            HWGSounds.SNIPERRELOAD.get(),
            HWGSounds.SNIPER.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> MEANIE1 = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "meanie_gun_1",
        () -> new AzureAnimatedGunItem(
            "meanie_gun_1",
            ProjectileEnum.MEANIE,
            GunTypeEnum.MEANIE,
            CommonMod.config.gunconfigs.meanieconfigs.meanie_cap,
            HWGSounds.PISTOLRELOAD.get(),
            SoundEvents.ARMOR_EQUIP_IRON.value()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> MEANIE2 = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "meanie_gun_2",
        () -> new AzureAnimatedGunItem(
            "meanie_gun_2",
            ProjectileEnum.MEANIE,
            GunTypeEnum.MEANIE,
            CommonMod.config.gunconfigs.meanieconfigs.meanie_cap,
            HWGSounds.PISTOLRELOAD.get(),
            SoundEvents.ARMOR_EQUIP_IRON.value()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> GOLDEN_GUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "golden_gun",
        () -> new AzureAnimatedGunItem(
            "golden_gun",
            ProjectileEnum.BULLET,
            GunTypeEnum.GOLDEN_PISTOL,
            CommonMod.config.gunconfigs.gpistolconfigs.golden_pistol_cap,
            HWGSounds.PISTOLRELOAD.get(),
            HWGSounds.PISTOL.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> ROCKETLAUNCHER = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "rocketlauncher",
        () -> new AzureAnimatedGunItem(
            "rocketlauncher",
            ProjectileEnum.ROCKET,
            GunTypeEnum.ROCKETLAUNCHER,
            CommonMod.config.gunconfigs.rocketlauncherconfigs.rocketlauncherCap,
            HWGSounds.GLAUNCHERRELOAD.get(),
            HWGSounds.RPG.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> HELLHORSE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "hellhorse_revolver",
        () -> new AzureAnimatedGunItem(
            "hellhorse_revolver",
            ProjectileEnum.HELL,
            GunTypeEnum.HELLHORSE,
            CommonMod.config.gunconfigs.hellhorseconfigs.hellhorse_cap,
            HWGSounds.REVOLVERRELOAD.get(),
            HWGSounds.REVOLVER.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> SILVERGUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "silvergun",
        () -> new AzureAnimatedGunItem(
            "silvergun",
            ProjectileEnum.SILVER_BULLET,
            GunTypeEnum.SILVER_PISTOL,
            CommonMod.config.gunconfigs.pistolconfigs.pistol_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.PISTOL.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> SILVERHELLHORSE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "shellhorse_revolver",
        () -> new AzureAnimatedGunItem(
            "shellhorse_revolver",
            ProjectileEnum.SILVER_BULLET,
            GunTypeEnum.SILVER_HELL,
            CommonMod.config.gunconfigs.hellhorseconfigs.hellhorse_cap,
            HWGSounds.REVOLVERRELOAD.get(),
            HWGSounds.REVOLVER.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> AK47 = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "ak47",
        () -> new AzureAnimatedGunItem(
            "ak47",
            ProjectileEnum.BULLET,
            GunTypeEnum.AK7,
            CommonMod.config.gunconfigs.ak47configs.ak47_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.AK.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> SMG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "smg",
        () -> new AzureAnimatedGunItem(
            "smg",
            ProjectileEnum.BULLET,
            GunTypeEnum.SMG,
            CommonMod.config.gunconfigs.smgconfigs.smg_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.SMG.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> TOMMYGUN = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "tommy_gun",
        () -> new AzureAnimatedGunItem(
            "tommy_gun",
            ProjectileEnum.BULLET,
            GunTypeEnum.TOMMYGUN,
            CommonMod.config.gunconfigs.tommyconfigs.tommy_cap,
            HWGSounds.CLIPRELOAD.get(),
            HWGSounds.TOMMY.get()
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> BALROG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "balrog_gun",
        () -> new AzureAnimatedGunItem(
            "balrog_gun",
            ProjectileEnum.BLAZE,
            GunTypeEnum.BALROG,
            CommonMod.config.gunconfigs.balrogconfigs.balrog_cap,
            SoundEvents.FIRECHARGE_USE,
            SoundEvents.FIREWORK_ROCKET_BLAST_FAR
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> BRIMSTONE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "brimstone_gun",
        () -> new AzureAnimatedGunItem(
            "brimstone_gun",
            ProjectileEnum.FIREBALL,
            GunTypeEnum.BRIMSTONE,
            CommonMod.config.gunconfigs.brimstoneconfigs.brimstone_cap,
            SoundEvents.FIRECHARGE_USE,
            SoundEvents.SHULKER_SHOOT
        ) {}
    );

    public static final Supplier<AzureAnimatedGunItem> INCINERATOR = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "nostromo_flamethrower",
        () -> new AzureAnimatedGunItem(
            "nostromo_flamethrower",
            ProjectileEnum.FLAMES,
            GunTypeEnum.FLAMETHROWER,
            CommonMod.config.gunconfigs.flammerconfigs.flammer_cap,
            SoundEvents.METAL_HIT,
            SoundEvents.FIRECHARGE_USE
        ) {}
    );

    /**
     * Blocks
     */
    public static final Supplier<Item> GUN_TABLE = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "gun_table",
        () -> new BlockItem(HWGBlocks.GUN_TABLE.get(), new Item.Properties())
    );

    /**
     * Spawn Eggs
     */
    public static final Supplier<SpawnEggItem> SPY_SPAWN_EGG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "spy_spawn_egg",
        Services.COMMON_REGISTRY.makeSpawnEggFor(
            HWGMobs.SPY,
            11022961,
            11035249,
            new Item.Properties()
        )
    );

    public static final Supplier<SpawnEggItem> MERC_SPAWN_EGG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "merc_spawn_egg",
        Services.COMMON_REGISTRY.makeSpawnEggFor(
            HWGMobs.MERC,
            11022961,
            11035249,
            new Item.Properties()
        )
    );

    public static final Supplier<SpawnEggItem> LESSER_SPAWN_EGG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "lesser_spawn_egg",
        Services.COMMON_REGISTRY.makeSpawnEggFor(
            HWGMobs.TECHNOLESSER,
            11022961,
            11035249,
            new Item.Properties()
        )
    );

    public static final Supplier<SpawnEggItem> GREATER_SPAWN_EGG = CommonItemRegistryInterface.registerItem(
        CommonMod.MOD_ID,
        "greater_spawn_egg",
        Services.COMMON_REGISTRY.makeSpawnEggFor(
            HWGMobs.TECHNOGREATER,
            11022961,
            11035249,
            new Item.Properties()
        )
    );

    public static void init() {}
}
