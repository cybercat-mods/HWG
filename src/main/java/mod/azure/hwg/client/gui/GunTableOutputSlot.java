package mod.azure.hwg.client.gui;

import mod.azure.hwg.util.recipes.GunTableRecipe.Type;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GunTableOutputSlot extends Slot {
    private final GunTableInventory gunTableInventory;
    private final Player player;
    private int amount;

    public GunTableOutputSlot(Player player, GunTableInventory gunTableInventory, int index, int x, int y) {
        super(gunTableInventory, index, x, y);
        this.player = player;
        this.gunTableInventory = gunTableInventory;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem())
            this.amount += Math.min(amount, this.getItem().getCount());

        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.amount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level(), this.player, this.amount);
        this.amount = 0;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        var optionalGunTableRecipe = player.level().getRecipeManager().getRecipeFor(Type.INSTANCE, gunTableInventory, player.level());
        if (optionalGunTableRecipe.isPresent()) {
            var gunTableRecipe = optionalGunTableRecipe.get().value();
            var defaultedList = gunTableRecipe.getRemainingItems(gunTableInventory);

            for (int i = 0; i < defaultedList.size(); ++i) {
                var itemStack = this.gunTableInventory.getItem(i);
                var itemStack2 = defaultedList.get(i);
                if (!itemStack.isEmpty()) {
                    this.gunTableInventory.removeItem(i, gunTableRecipe.countRequired(i));
                    itemStack = this.gunTableInventory.getItem(i);
                }

                if (!itemStack2.isEmpty())
                    if (itemStack.isEmpty())
                        this.gunTableInventory.setItem(i, itemStack2);
                    else if (ItemStack.isSameItem(itemStack, itemStack2) && ItemStack.isSameItemSameTags(itemStack, itemStack2)) {
                        itemStack2.grow(itemStack.getCount());
                        this.gunTableInventory.setItem(i, itemStack2);
                    } else if (!this.player.getInventory().add(itemStack2))
                        this.player.drop(itemStack2, false);
            }
        }
        this.setChanged();
    }
}
