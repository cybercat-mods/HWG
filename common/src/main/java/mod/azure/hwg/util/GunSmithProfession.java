package mod.azure.hwg.util;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGProfession;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record GunSmithProfession() {

    public static void init() {
        VillagerTrades.TRADES.put(HWGProfession.GUNSMITH.get(), copyToFastUtilMap(ImmutableMap.of(
                1, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.EmeraldForItems(Items.GUNPOWDER, 1, 16, 2),
                        new VillagerTrades.ItemsForEmeralds(Items.IRON_NUGGET, 2, 1, 16, 1)},
                2, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.EmeraldForItems(HWGItems.BULLETS.get(), 2, 16, 10),
                        new VillagerTrades.EmeraldForItems(HWGItems.PISTOL.get(), 5, 16, 20),
                        new VillagerTrades.EmeraldForItems(HWGItems.LUGER.get(), 5, 16, 20)},
                3, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.EmeraldForItems(HWGItems.SHOTGUN_SHELL.get(), 16, 16, 30),
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 3, 1, HWGItems.SMG.get(), 1, 16, 30, 0.05F),
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 3, 1, HWGItems.TOMMYGUN.get(), 1, 16, 30, 0.05F)},
                4, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsAndEmeraldsToItems(HWGItems.FUEL_TANK.get(), 1, 4, HWGItems.FLAMETHROWER.get(), 1, 16, 40, 0.05F),
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 6, 4, HWGItems.SHOTGUN.get(), 1, 16, 40, 0.05F),
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.GUNPOWDER, 8, 4, HWGItems.BULLETS.get(), 48, 16, 50, 0.05F)},
                5, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 18, 8, HWGItems.ROCKETLAUNCHER.get(), 1, 16, 60, 0.05F),
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 18, 8, HWGItems.G_LAUNCHER.get(), 1, 16, 60, 0.05F),
                        new VillagerTrades.ItemsAndEmeraldsToItems(Items.IRON_INGOT, 18, 8, HWGItems.SNIPER.get(), 1, 16, 60, 0.05F)}
        )));
    }

    public static Int2ObjectMap<VillagerTrades.ItemListing[]> copyToFastUtilMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> immutableMap) {
        return new Int2ObjectOpenHashMap<>(immutableMap);
    }

}
