package mod.azure.hwg.util;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;
import java.util.Optional;

import mod.azure.hwg.platform.Services;
import mod.azure.hwg.util.registry.HWGItems;

public record GunSmithProfession() {

    public static List<VillagerTrades.ItemListing> LEVEL_1 = List.of(
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD.asItem().asItem(), 1),
            new ItemStack(Items.GUNPOWDER, 1),
            16,
            17,
            0.05F
        )
    );

    public static List<VillagerTrades.ItemListing> LEVEL_2 = List.of(
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD.asItem()),
            new ItemStack(HWGItems.BULLETS.get(), 2),
            16,
            27,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD.asItem(), 5),
            new ItemStack(HWGItems.PISTOL.get(), 5),
            16,
            35,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD.asItem(), 5),
            new ItemStack(HWGItems.LUGER.get(), 5),
            16,
            35,
            0.05F
        )
    );

    public static List<VillagerTrades.ItemListing> LEVEL_3 = List.of(
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 1),
            Optional.of(new ItemCost(Items.IRON_INGOT, 3)),
            new ItemStack(HWGItems.SMG.get()),
            0,
            16,
            65,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 1),
            Optional.of(new ItemCost(Items.IRON_INGOT, 3)),
            new ItemStack(HWGItems.TOMMYGUN.get()),
            0,
            16,
            65,
            0.05F
        )
    );

    public static List<VillagerTrades.ItemListing> LEVEL_4 = List.of(
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 4),
            Optional.of(new ItemCost(HWGItems.FUEL_TANK.get(), 1)),
            new ItemStack(HWGItems.FLAMETHROWER.get()),
            0,
            16,
            65,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 4),
            Optional.of(new ItemCost(Items.IRON_INGOT, 6)),
            new ItemStack(HWGItems.SHOTGUN.get()),
            0,
            16,
            65,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 4),
            Optional.of(new ItemCost(Items.GUNPOWDER, 8)),
            new ItemStack(HWGItems.BULLETS.get(), 48),
            0,
            16,
            65,
            0.05F
        )
    );

    public static List<VillagerTrades.ItemListing> LEVEL_5 = List.of(
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 8),
            Optional.of(new ItemCost(Items.IRON_INGOT, 18)),
            new ItemStack(HWGItems.ROCKETLAUNCHER.get()),
            0,
            16,
            65,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 8),
            Optional.of(new ItemCost(Items.IRON_INGOT, 18)),
            new ItemStack(HWGItems.G_LAUNCHER.get()),
            0,
            16,
            65,
            0.05F
        ),
        (entity, randomSource) -> new MerchantOffer(
            new ItemCost(Items.EMERALD, 8),
            Optional.of(new ItemCost(Items.IRON_INGOT, 18)),
            new ItemStack(HWGItems.SNIPER.get()),
            0,
            16,
            65,
            0.05F
        )
    );

    public static void init() {
        Services.COMMON_REGISTRY.registerVillagerTrade(Services.COMMON_REGISTRY::getProfession, 1, LEVEL_1);
        Services.COMMON_REGISTRY.registerVillagerTrade(Services.COMMON_REGISTRY::getProfession, 2, LEVEL_2);
        Services.COMMON_REGISTRY.registerVillagerTrade(Services.COMMON_REGISTRY::getProfession, 3, LEVEL_3);
        Services.COMMON_REGISTRY.registerVillagerTrade(Services.COMMON_REGISTRY::getProfession, 4, LEVEL_4);
        Services.COMMON_REGISTRY.registerVillagerTrade(Services.COMMON_REGISTRY::getProfession, 5, LEVEL_5);
    }

}
