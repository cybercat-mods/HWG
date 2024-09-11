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
        VillagerTrades.TRADES.put(HWGProfession.GUNSMITH.get(), copyToFastUtilMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new BuyForOneEmeraldFactory(Items.GUNPOWDER, 1, 16, 2), new SellItemFactory(Items.IRON_NUGGET, 2, 1, 16, 1)}, 2, new VillagerTrades.ItemListing[]{new BuyForItemFactory(Items.EMERALD, HWGItems.BULLETS.get(), 2, 16, 10), new BuyForItemFactory(Items.EMERALD, HWGItems.PISTOL.get(), 5, 16, 20), new BuyForItemFactory(Items.EMERALD, HWGItems.LUGER.get(), 5, 16, 20)}, 3, new VillagerTrades.ItemListing[]{new BuyForItemsFactory(Items.EMERALD, 2, 1, HWGItems.SHOTGUN_SHELL.get(), 16, 16, 30), new BuyForItemsFactory(Items.IRON_INGOT, 3, HWGItems.SMG.get(), 1, 16, 30), new BuyForItemsFactory(Items.IRON_INGOT, 3, HWGItems.TOMMYGUN.get(), 1, 16, 30)}, 4, new VillagerTrades.ItemListing[]{new BuyForItemsFactory(HWGItems.FUEL_TANK.get(), 1, 4, HWGItems.FLAMETHROWER.get(), 1, 16, 40), new BuyForItemsFactory(Items.IRON_INGOT, 6, 4, HWGItems.SHOTGUN.get(), 1, 16, 40), new BuyForItemsFactory(Items.GUNPOWDER, 8, 4, HWGItems.BULLETS.get(), 48, 16, 50)}, 5, new VillagerTrades.ItemListing[]{new BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.ROCKETLAUNCHER.get(), 1, 16, 60), new BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.G_LAUNCHER.get(), 1, 16, 60), new BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.SNIPER.get(), 1, 16, 60)})));
    }

    public static Int2ObjectMap<VillagerTrades.ItemListing[]> copyToFastUtilMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> immutableMap) {
        return new Int2ObjectOpenHashMap<>(immutableMap);
    }

    public static class BuyForItemsFactory implements VillagerTrades.ItemListing {
        protected final ItemStack secondBuy;
        protected final int secondCount;
        protected final int price;
        protected final ItemStack sell;
        protected final int sellCount;
        protected final int maxUses;
        protected final int experience;
        protected final float multiplier;

        public BuyForItemsFactory(ItemLike item, int secondCount, Item sellItem, int sellCount, int maxUses, int experience) {
            this(item, secondCount, 1, sellItem, sellCount, maxUses, experience);
        }

        public BuyForItemsFactory(ItemLike item, int secondCount, int price, Item sellItem, int sellCount, int maxUses, int experience) {
            this.secondBuy = new ItemStack(item);
            this.secondCount = secondCount;
            this.price = price;
            this.sell = new ItemStack(sellItem);
            this.sellCount = sellCount;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource random) {
            return new MerchantOffer(new ItemCost(Items.EMERALD, this.price), Optional.of(new ItemCost(this.secondBuy.getItem())), new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class BuyForOneEmeraldFactory implements VillagerTrades.ItemListing {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemLike item, int price, int maxUses, int experience) {
            this.buy = item.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource random) {
            ItemStack itemStack = new ItemStack(this.buy, this.price);
            return new MerchantOffer(new ItemCost(itemStack.getItem()), new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class BuyForItemFactory implements VillagerTrades.ItemListing {
        private final Item buy;
        private final Item sell;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForItemFactory(ItemLike item, ItemLike sell, int price, int maxUses, int experience) {
            this.buy = item.asItem();
            this.sell = sell.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource random) {
            ItemStack itemStack = new ItemStack(this.buy, this.price);
            return new MerchantOffer(new ItemCost(itemStack.getItem()), new ItemStack(sell.asItem()), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class SellItemFactory implements VillagerTrades.ItemListing {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
            this(new ItemStack(item), price, count, maxUses, experience);
        }

        public SellItemFactory(ItemStack itemStack, int price, int count, int maxUses, int experience) {
            this(itemStack, price, count, maxUses, experience, 0.05F);
        }

        public SellItemFactory(ItemStack itemStack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = itemStack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource random) {
            return new MerchantOffer(new ItemCost(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }

}
