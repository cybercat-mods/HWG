package mod.azure.hwg.item.weapons.animations;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class GunDispatcher {

    private final AzCommand IDLE_COMMAND = AzCommand.create("base_controller", "idle", AzPlayBehaviors.LOOP);

    private final AzCommand FIRING_COMMAND = AzCommand.create("base_controller", AzureAnimatedGunItem.firing, AzPlayBehaviors.PLAY_ONCE);

    private final AzCommand TOMMYRELOAD_COMMAND = AzCommand.create("base_controller", "tommyreload2", AzPlayBehaviors.PLAY_ONCE);

    private final AzCommand RELOAD_COMMAND = AzCommand.create("base_controller", "reload", AzPlayBehaviors.PLAY_ONCE);

    private final AzCommand LOADING_COMMAND = AzCommand.create("base_controller", "loading", AzPlayBehaviors.PLAY_ONCE);

    public void sendIdleCommand(Entity entity, ItemStack stack) {
        IDLE_COMMAND.sendForItem(entity, stack);
    }

    public void sendFiringCommand(Entity entity, ItemStack stack) {
        FIRING_COMMAND.sendForItem(entity, stack);
    }

    public void sendTommyReloadCommand(Entity entity, ItemStack stack) {
        TOMMYRELOAD_COMMAND.sendForItem(entity, stack);
    }

    public void sendReloadCommand(Entity entity, ItemStack stack) {
        RELOAD_COMMAND.sendForItem(entity, stack);
    }

    public void sendLoadingCommand(Entity entity, ItemStack stack) {
        LOADING_COMMAND.sendForItem(entity, stack);
    }
}
