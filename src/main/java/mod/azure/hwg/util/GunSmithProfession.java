package mod.azure.hwg.util;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.mixin.PointOfInterestTypesInvoker;
import mod.azure.hwg.util.registry.HWGBlocks;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class GunSmithProfession {

	public static final Supplier<PoiType> GUNSMITH_POI = registerPoiType("gun_smith",
			() -> new PoiType(PointOfInterestTypesInvoker.invokeGetBlockStates(HWGBlocks.GUN_TABLE), 1, 10));
	public static final Supplier<VillagerProfession> GUNSMITH = registerProfession("gun_smith",
			() -> new VillagerProfession("gun_smith", holder -> holder.value().equals(GUNSMITH_POI.get()),
					holder -> holder.value().equals(GUNSMITH_POI.get()), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.ITEM_FRAME_REMOVE_ITEM));

	public static Supplier<VillagerProfession> registerProfession(String name,
			Supplier<VillagerProfession> profession) {
		var registry = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION,
				new ResourceLocation(HWGMod.MODID, name), profession.get());
		return () -> registry;
	}

	public static Supplier<PoiType> registerPoiType(String name, Supplier<PoiType> poiType) {
		var resourceKey = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE,
				new ResourceLocation(HWGMod.MODID, name));
		var registry = Registry.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, resourceKey, poiType.get());
		PointOfInterestTypesInvoker.invokeRegisterBlockStates(
				BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(resourceKey),
				PoiTypes.getBlockStates(HWGBlocks.GUN_TABLE));
		return () -> registry;
	}

	public static void init() {
		VillagerTrades.TRADES.put(GUNSMITH.get(),
				copyToFastUtilMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[] {
						new GunSmithProfession.BuyForOneEmeraldFactory(Items.GUNPOWDER, 1, 16, 2),
						new GunSmithProfession.SellItemFactory(Items.IRON_NUGGET, 2, 1, 16, 1) }, 2,
						new VillagerTrades.ItemListing[] {
								new GunSmithProfession.BuyForItemFactory(Items.EMERALD, HWGItems.BULLETS, 2, 16, 10),
								new GunSmithProfession.BuyForItemFactory(Items.EMERALD, HWGItems.PISTOL, 5, 16, 20),
								new GunSmithProfession.BuyForItemFactory(Items.EMERALD, HWGItems.LUGER, 5, 16, 20) },
						3,
						new VillagerTrades.ItemListing[] {
								new GunSmithProfession.BuyForItemsFactory(Items.EMERALD, 2, 1, HWGItems.SHOTGUN_SHELL,
										16, 16, 30),
								new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 3, HWGItems.SMG, 1, 16, 30),
								new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 3, HWGItems.TOMMYGUN, 1, 16,
										30) },
						4,
						new VillagerTrades.ItemListing[] {
								new GunSmithProfession.BuyForItemsFactory(HWGItems.FUEL_TANK, 1, 4,
										HWGItems.FLAMETHROWER, 1, 16, 40),
								new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 6, 4, HWGItems.SHOTGUN, 1,
										16, 40),
								new GunSmithProfession.BuyForItemsFactory(Items.GUNPOWDER, 8, 4, HWGItems.BULLETS, 48,
										16, 50) },
						5,
						new VillagerTrades.ItemListing[] {
								new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 18, 8,
										HWGItems.ROCKETLAUNCHER, 1, 16, 60),
								new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.G_LAUNCHER,
										1, 16, 60),
								new GunSmithProfession.BuyForItemsFactory(Items.IRON_INGOT, 18, 8, HWGItems.SNIPER, 1,
										16, 60) })));
	}

	public static Int2ObjectMap<VillagerTrades.ItemListing[]> copyToFastUtilMap(
			ImmutableMap<Integer, VillagerTrades.ItemListing[]> immutableMap) {
		return new Int2ObjectOpenHashMap<ItemListing[]>(immutableMap);
	}

	public static class BuyForItemsFactory implements VillagerTrades.ItemListing {
		private final ItemStack secondBuy;
		private final int secondCount;
		private final int price;
		private final ItemStack sell;
		private final int sellCount;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public BuyForItemsFactory(ItemLike item, int secondCount, Item sellItem, int sellCount, int maxUses,
				int experience) {
			this(item, secondCount, 1, sellItem, sellCount, maxUses, experience);
		}

		public BuyForItemsFactory(ItemLike item, int secondCount, int price, Item sellItem, int sellCount, int maxUses,
				int experience) {
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
		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price),
					new ItemStack(this.secondBuy.getItem(), this.secondCount),
					new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
		}
	}

	static class BuyForOneEmeraldFactory implements VillagerTrades.ItemListing {
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

		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience,
					this.multiplier);
		}
	}

	static class BuyForItemFactory implements VillagerTrades.ItemListing {
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

		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new MerchantOffer(itemStack, new ItemStack(sell.asItem()), this.maxUses, this.experience,
					this.multiplier);
		}
	}

	static class SellItemFactory implements VillagerTrades.ItemListing {
		private final ItemStack sell;
		private final int price;
		private final int count;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public SellItemFactory(Block block, int price, int count, int maxUses, int experience) {
			this(new ItemStack(block), price, count, maxUses, experience);
		}

		public SellItemFactory(Item item, int price, int count, int experience) {
			this((ItemStack) (new ItemStack(item)), price, count, 12, experience);
		}

		public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
			this(new ItemStack(item), price, count, maxUses, experience);
		}

		public SellItemFactory(ItemStack itemStack, int price, int count, int maxUses, int experience) {
			this(itemStack, price, count, maxUses, experience, 0.05F);
		}

		public SellItemFactory(ItemStack itemStack, int price, int count, int maxUses, int experience,
				float multiplier) {
			this.sell = itemStack;
			this.price = price;
			this.count = count;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price),
					new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
		}
	}

}
