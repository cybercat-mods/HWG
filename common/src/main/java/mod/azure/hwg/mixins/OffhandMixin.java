package mod.azure.hwg.mixins;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.hwg.CommonMod;

@Mixin(LivingEntity.class)
public class OffhandMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void hwg$offHandInventoryTick(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        var stack = livingEntity.getOffhandItem();

        if (!stack.isEmpty()) {
            if (stack.is(CommonMod.IS_WEAPON)) {
                // https://minecraft.wiki/w/Slot 99 seems to be offhand slot
                stack.inventoryTick(livingEntity.level(), livingEntity, 99, false);
                return;
            }
        }
    }
}
