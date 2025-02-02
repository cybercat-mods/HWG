package mod.azure.hwg.entity.animation;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;

public class AnimationDispatcher {

    private final AzCommand WALK_COMMAND = AzCommand.create("base_controller", "walking", AzPlayBehaviors.LOOP);

    private final AzCommand IDLE_COMMAND = AzCommand.create("base_controller", "idle", AzPlayBehaviors.LOOP);

    private final AzCommand RANGED_COMMAND = AzCommand.create("base_controller", "attacking", AzPlayBehaviors.PLAY_ONCE);

    private final AzCommand MELEE_COMMAND = AzCommand.create("base_controller", "melee", AzPlayBehaviors.PLAY_ONCE);

    private final Entity animatedEntity;

    public AnimationDispatcher(Entity animatedEntity) {
        this.animatedEntity = animatedEntity;
    }

    public void sendWalkAnimation() {
        WALK_COMMAND.sendForEntity(animatedEntity);
    }

    public void sendIdleAnimation() {
        IDLE_COMMAND.sendForEntity(animatedEntity);
    }

    public void sendRangedAnimation() {
        RANGED_COMMAND.sendForEntity(animatedEntity);
    }

    public void sendMeleeAnimation() {
        MELEE_COMMAND.sendForEntity(animatedEntity);
    }
}
