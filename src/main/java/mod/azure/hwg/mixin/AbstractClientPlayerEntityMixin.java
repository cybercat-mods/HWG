package mod.azure.hwg.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import mod.azure.hwg.client.ClientInit;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

	public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile,
			PlayerPublicKey publicKey) {
		super(world, pos, yaw, gameProfile, publicKey);
	}

	@Inject(at = @At("HEAD"), method = "getSpeed", cancellable = true)
	private void render(CallbackInfoReturnable<Float> ci) {
		ItemStack itemStack = this.getMainHandStack();
		if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
			if (itemStack.isOf(HWGItems.SNIPER)) {
				ci.setReturnValue(ClientInit.scope.isPressed() ? 0.1F : 1.0F);
			}
		}
	}
}
