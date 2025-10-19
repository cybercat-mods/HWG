package mod.azure.hwg.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.hwg.client.HWGKeybinds;
import mod.azure.hwg.util.registry.HWGItems;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin extends Player {

    protected AbstractClientPlayerEntityMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "getFieldOfViewModifier", cancellable = true)
    private void render(CallbackInfoReturnable<Float> ci) {
        if (
            Minecraft.getInstance().options.getCameraType().isFirstPerson() && this.getMainHandItem()
                .is(HWGItems.SNIPER.get())
        )
            ci.setReturnValue(HWGKeybinds.SCOPE.isDown() ? 0.1F : 1.0F);
    }
}
