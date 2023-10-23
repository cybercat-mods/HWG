package mod.azure.hwg.item.ammo;

import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrenadeFragItem extends Item {

    public GrenadeFragItem() {
        super(new Item.Properties());
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (!user.getCooldowns().isOnCooldown(this)) {
            user.getCooldowns().addCooldown(this, 25);
            if (!world.isClientSide) {
                var nadeEntity = new GrenadeEntity(world, user);
                nadeEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
                nadeEntity.setVariant(2);
                nadeEntity.setState(1);
                world.addFreshEntity(nadeEntity);
            }
            if (!user.getAbilities().instabuild)
                itemStack.shrink(1);
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

}
