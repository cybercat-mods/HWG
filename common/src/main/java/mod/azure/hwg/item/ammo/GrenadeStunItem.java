package mod.azure.hwg.item.ammo;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import mod.azure.hwg.util.registry.HWGProjectiles;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GrenadeStunItem extends Item {

    public GrenadeStunItem() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player user && !user.getCooldowns().isOnCooldown(this)) {
            user.getCooldowns().addCooldown(this, CommonMod.config.gunconfigs.grenades_throw_cooldown);
            if (!level.isClientSide) {
                var nadeEntity = HWGProjectiles.GRENADE.get().create(level);
                nadeEntity.setOwner(user);
                nadeEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
                nadeEntity.setVariant(5);
                nadeEntity.setState(1);
                level.addFreshEntity(nadeEntity);
            }
            if (!user.getAbilities().instabuild)
                stack.shrink(1);
        }
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

}
