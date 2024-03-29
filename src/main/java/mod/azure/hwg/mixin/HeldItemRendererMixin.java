package mod.azure.hwg.mixin;

import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import mod.azure.hwg.item.weapons.HWGGunLoadedBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {

    @Mutable
    @Shadow
    @Final
    private final Minecraft minecraft;
    @Shadow
    private float mainHandHeight;
    @Shadow
    private float offHandHeight;
    @Shadow
    private ItemStack mainHandItem;
    @Shadow
    private ItemStack offHandItem;

    protected HeldItemRendererMixin(Minecraft client) {
        this.minecraft = client;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void fguns$cancelAnimation(CallbackInfo ci) {
        var clientPlayerEntity = this.minecraft.player;
        var itemStack = clientPlayerEntity.getMainHandItem();
        var itemStack2 = clientPlayerEntity.getOffhandItem();
        if ((this.mainHandItem.getItem() instanceof AzureAnimatedGunItem) && ItemStack.isSameItem(mainHandItem, itemStack)) {
            this.mainHandHeight = 1;
            this.mainHandItem = itemStack;
        }
        if (this.mainHandItem.getItem() instanceof HWGGunLoadedBase && ItemStack.isSameItem(mainHandItem, itemStack)) {
            this.mainHandHeight = 1;
            this.mainHandItem = itemStack;
        }
        if ((this.offHandItem.getItem() instanceof AzureAnimatedGunItem) && ItemStack.isSameItem(offHandItem, itemStack2)) {
            this.offHandHeight = 1;
            this.offHandItem = itemStack2;
        }
        if (this.offHandItem.getItem() instanceof HWGGunLoadedBase && ItemStack.isSameItem(offHandItem, itemStack2)) {
            this.offHandHeight = 1;
            this.offHandItem = itemStack2;
        }
    }
}
