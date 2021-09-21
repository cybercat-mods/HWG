package mod.azure.hwg.blocks;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class GunBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(6, ItemStack.EMPTY);

	public GunBlockEntity(BlockPos pos, BlockState state) {
		super(HWGMod.GUN_TABLE_ENTITY, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, items);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, items);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new GunTableScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos));
	}
}