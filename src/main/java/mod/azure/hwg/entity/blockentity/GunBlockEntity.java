package mod.azure.hwg.entity.blockentity;

import mod.azure.hwg.blocks.ImplementedInventory;
import mod.azure.hwg.client.gui.GunTableScreenHandler;
import mod.azure.hwg.util.registry.HWGMobs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GunBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {

	private final NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);

	public GunBlockEntity(BlockPos pos, BlockState state) {
		super(HWGMobs.GUN_TABLE_ENTITY, pos, state);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		ContainerHelper.loadAllItems(nbt, items);
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		ContainerHelper.saveAllItems(nbt, items);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(getBlockState().getBlock().getDescriptionId());
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
		return new GunTableScreenHandler(syncId, inventory, ContainerLevelAccess.create(level, worldPosition));
	}
}