package mod.azure.hwg.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class HWGEquipmentUtils {
	public static String TAG = "hwgguntag";

	public static boolean ruinedItemHasEnchantment(ItemStack ruinedItem, Enchantment enchantment) {
		if (ruinedItem.getTag() == null)
			return false;
		var tagString = ruinedItem.getTag().getString(TAG);
		var enchantMap = HWGEquipmentUtils.processEncodedEnchantments(tagString);
		if (enchantMap == null)
			return false;
		for (Enchantment e : enchantMap.keySet())
			if (e == enchantment)
				return true;
		return false;
	}

	public static int compareItemsById(Item i1, Item i2) {
		return BuiltInRegistries.ITEM.getKey(i1).compareTo(BuiltInRegistries.ITEM.getKey(i2));
	}

	public static int generateRepairLevelCost(ItemStack repaired, int maxLevel) {
		var targetLevel = maxLevel * (repaired.getMaxDamage() - repaired.getDamageValue()) / repaired.getMaxDamage();
		return Math.max(targetLevel, 1);
	}

	public static ItemStack generateRepairedItemForAnvilByFraction(ItemStack leftStack, double damageFraction) {
		var maxDamage = new ItemStack(HWGItems.getItemMap().get(leftStack.getItem())).getMaxDamage();
		return generateRepairedItemForAnvilByDamage(leftStack, (int) (damageFraction * (double) maxDamage));
	}

	public static ItemStack generateRepairedItemForAnvilByDamage(ItemStack leftStack, int targetDamage) {

		var repaired = new ItemStack(HWGItems.getItemMap().get(leftStack.getItem()));
		var tag = leftStack.getOrCreateTag();
		var encodedEnch = tag.getString(TAG);
		if (!encodedEnch.isEmpty())
			tag.remove(TAG);
		var enchantMap = HWGEquipmentUtils.processEncodedEnchantments(encodedEnch);
		if (enchantMap != null)
			for (Map.Entry<Enchantment, Integer> enchant : enchantMap.entrySet())
				repaired.enchant(enchant.getKey(), enchant.getValue());
		repaired.setTag(repaired.getOrCreateTag().merge(tag));
		repaired.setDamageValue(targetDamage);
		return repaired;
	}

	public static Map<Enchantment, Integer> processEncodedEnchantments(String encodedEnchants) {
		if (encodedEnchants.isEmpty())
			return null;
		Map<Enchantment, Integer> enchants = new HashMap<>();
		for (String encodedEnchant : encodedEnchants.split(",")) {
			var enchantItem = encodedEnchant.split(">");
			var enchantKey = enchantItem[0].split(":");
			var enchantLevel = Integer.parseInt(enchantItem[1]);
			enchants.put(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(enchantKey[0], enchantKey[1])), enchantLevel);
		}
		return enchants.isEmpty() ? null : enchants;
	}

	public static void onSendEquipmentBreakStatusImpl(ServerPlayer serverPlayer, ItemStack breakingStack, boolean forceSet) {
		for (Map.Entry<Item, Item> itemMap : HWGItems.getItemMap().entrySet()) {
			if (isVanillaItemStackBreaking(breakingStack, itemMap.getValue())) {
				var ruinedStack = new ItemStack(itemMap.getKey());
				var breakingNBT = breakingStack.getOrCreateTag();
				if (breakingNBT.contains("Damage"))
					breakingNBT.remove("Damage");
				if (breakingNBT.contains("RepairCost"))
					breakingNBT.remove("RepairCost");
				var enchantTag = getTagForEnchantments(breakingStack, ruinedStack);
				if (enchantTag != null)
					breakingNBT.merge(enchantTag);
				if (breakingNBT.contains("Enchantments"))
					breakingNBT.remove("Enchantments");
				ruinedStack.setTag(breakingNBT);
				serverPlayer.getInventory().placeItemBackInInventory(ruinedStack);
			}
		}
	}

	public static CompoundTag getTagForEnchantments(ItemStack breakingStack, ItemStack ruinedStack) {
		Set<String> enchantmentStrings = new HashSet<>();
		for (Map.Entry<Enchantment, Integer> ench : EnchantmentHelper.getEnchantments(breakingStack).entrySet()) {
			var enchantString = BuiltInRegistries.ENCHANTMENT.getKey(ench.getKey()) + ">" + ench.getValue();
			enchantmentStrings.add(enchantString);
		}
		if (!enchantmentStrings.isEmpty()) {
			var tag = ruinedStack.getTag();
			if (tag == null)
				tag = new CompoundTag();
			tag.putString(TAG, String.join(",", enchantmentStrings));
			return tag;
		}
		return null;
	}

	public static boolean isVanillaItemStackBreaking(ItemStack breakingStack, Item vanillaItem) {
		return !new ItemStack(vanillaItem).isEmpty() && breakingStack.is(vanillaItem.asItem()) && breakingStack.getMaxDamage() - breakingStack.getDamageValue() <= 0;
	}
}
