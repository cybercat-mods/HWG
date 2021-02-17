package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.util.HWGItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class GrenadeLauncherItem extends Item {

	public static final Predicate<ItemStack> EMP = (stack) -> {
		return stack.getItem() == HWGItems.G_EMP;
	};

	public GrenadeLauncherItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add((new TranslatableText("Ammo:").formatted(Formatting.ITALIC)).append(" ")
				.formatted(Formatting.ITALIC).append(stack.toHoverableText()).formatted(Formatting.ITALIC));
		if (context.isAdvanced() && stack.getItem() == HWGItems.G_EMP) {
			List<Text> list2 = Lists.newArrayList();
			HWGItems.G_EMP.appendTooltip(stack, world, list2, context);
			if (!list2.isEmpty()) {
				for (int i = 0; i < list2.size(); ++i) {
					list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
				}
				tooltip.addAll(list2);
			}
		}
		if (context.isAdvanced() && stack.getItem() == HWGItems.G_FRAG) {
			List<Text> list2 = Lists.newArrayList();
			HWGItems.G_FRAG.appendTooltip(stack, world, list2, context);
			if (!list2.isEmpty()) {
				for (int i = 0; i < list2.size(); ++i) {
					list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
				}
				tooltip.addAll(list2);
			}
		}
		if (context.isAdvanced() && stack.getItem() == HWGItems.G_NAPALM) {
			List<Text> list2 = Lists.newArrayList();
			HWGItems.G_NAPALM.appendTooltip(stack, world, list2, context);
			if (!list2.isEmpty()) {
				for (int i = 0; i < list2.size(); ++i) {
					list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
				}
				tooltip.addAll(list2);
			}
		}
		if (context.isAdvanced() && stack.getItem() == HWGItems.G_SMOKE) {
			List<Text> list2 = Lists.newArrayList();
			HWGItems.G_SMOKE.appendTooltip(stack, world, list2, context);
			if (!list2.isEmpty()) {
				for (int i = 0; i < list2.size(); ++i) {
					list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
				}
				tooltip.addAll(list2);
			}
		}
		if (context.isAdvanced() && stack.getItem() == HWGItems.G_STUN) {
			List<Text> list2 = Lists.newArrayList();
			HWGItems.G_STUN.appendTooltip(stack, world, list2, context);
			if (!list2.isEmpty()) {
				for (int i = 0; i < list2.size(); ++i) {
					list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
				}
				tooltip.addAll(list2);
			}
		}
		if (context.isAdvanced() && stack.getItem() == null) {
			List<Text> list2 = Lists.newArrayList();
			Items.AIR.appendTooltip(stack, world, list2, context);
			if (!list2.isEmpty()) {
				for (int i = 0; i < list2.size(); ++i) {
					list2.set(i, (new LiteralText("  ")).append((Text) list2.get(i)).formatted(Formatting.GRAY));
				}
				tooltip.addAll(list2);
			}
		}
	}

}
