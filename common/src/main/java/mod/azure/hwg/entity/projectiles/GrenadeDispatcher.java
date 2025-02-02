package mod.azure.hwg.entity.projectiles;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;

public class GrenadeDispatcher {

    private final AzCommand SPIN_COMMAND = AzCommand.create("base_controller", "spin", AzPlayBehaviors.LOOP);

    private final AzCommand BULLET_COMMAND = AzCommand.create("base_controller", "bullet", AzPlayBehaviors.LOOP);

    private final Entity animatedEntity;

    public GrenadeDispatcher(Entity animatedEntity) {
        this.animatedEntity = animatedEntity;
    }

    public void sendSpinAnimation() {
        SPIN_COMMAND.sendForEntity(animatedEntity);
    }

    public void sendBulletAnimation() {
        BULLET_COMMAND.sendForEntity(animatedEntity);
    }

}
