package mod.azure.hwg.item.ammo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import mod.azure.hwg.HWGMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FireworkChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class FlareItem extends Item {

	public FlareItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
	}

	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		CompoundTag compoundTag = stack.getSubTag("Fireworks");
		if (compoundTag != null) {
			if (compoundTag.contains("Flight", 99)) {
				tooltip.add((new TranslatableText("item.minecraft.firework_rocket.flight")).append(" ")
						.append(String.valueOf(compoundTag.getByte("Flight"))).formatted(Formatting.GRAY));
			}

			ListTag listTag = compoundTag.getList("Explosions", 10);
			if (!listTag.isEmpty()) {
				for (int i = 0; i < listTag.size(); ++i) {
					CompoundTag compoundTag2 = listTag.getCompound(i);
					List<Text> list = Lists.newArrayList();
					FireworkChargeItem.appendFireworkTooltip(compoundTag2, list);
					if (!list.isEmpty()) {
						for (int j = 1; j < list.size(); ++j) {
							list.set(j, (new LiteralText("  ")).append((Text) list.get(j)).formatted(Formatting.GRAY));
						}

						tooltip.addAll(list);
					}
				}
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private static Text getColorText(int color) {
		DyeColor dyeColor = DyeColor.byFireworkColor(color);
		return dyeColor == null ? new TranslatableText("item.minecraft.firework_star.custom_color")
				: new TranslatableText("item.minecraft.firework_star." + dyeColor.getName());
	}

	public static enum Type {
		SMALL_BALL(0, "small_ball"), LARGE_BALL(1, "large_ball"), STAR(2, "star"), CREEPER(3, "creeper"),
		BURST(4, "burst");

		private static final FlareItem.Type[] TYPES = (FlareItem.Type[]) Arrays.stream(values())
				.sorted(Comparator.comparingInt((type) -> {
					return type.id;
				})).toArray((i) -> {
					return new FlareItem.Type[i];
				});
		private final int id;
		private final String name;

		private Type(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return this.id;
		}

		@Environment(EnvType.CLIENT)
		public String getName() {
			return this.name;
		}

		@Environment(EnvType.CLIENT)
		public static FlareItem.Type byId(int id) {
			return id >= 0 && id < TYPES.length ? TYPES[id] : SMALL_BALL;
		}
	}

}
