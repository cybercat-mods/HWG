package mod.azure.hwg.item.weapons;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.Keybindings;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.RocketEntity;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RocketLauncher extends HWGGunBase {

    public RocketLauncher() {
        super(new Item.Properties().stacksTo(1).durability(2));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
        if (entityLiving instanceof Player playerentity) {
            if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
                playerentity.getCooldowns().addCooldown(this, 15);
                if (!worldIn.isClientSide) {
                    var rocket = createArrow(worldIn, stack, playerentity);
                    rocket.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
                    rocket.moveTo(entityLiving.getX(), entityLiving.getY(0.95), entityLiving.getZ(), 0, 0);
                    rocket.setBaseDamage(2.5);
                    worldIn.addFreshEntity(rocket);
                    stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), HWGSounds.RPG, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 0.5F);
                }
            }
        }
    }

    public void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof RocketLauncher) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(HWGItems.ROCKET) > 0) {
                removeAmmo(HWGItems.ROCKET, user);
                user.getItemInHand(hand).hurtAndBreak(-2, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
                if (!user.getCooldowns().isOnCooldown(user.getItemInHand(hand).getItem()))
                    user.level().playSound(null, user.getX(), user.getY(), user.getZ(), HWGSounds.GLAUNCHERRELOAD, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.isClientSide)
            if (entity instanceof Player player)
                if (player.getMainHandItem().getItem() instanceof RocketLauncher)
                    if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                        var passedData = new FriendlyByteBuf(Unpooled.buffer());
                        passedData.writeBoolean(true);
                        ClientPlayNetworking.send(HWGMod.ROCKETLAUNCHER, passedData);
                    }
    }

    public RocketEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        var rocket = new RocketEntity(worldIn, shooter);
        return rocket;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        tooltip.add(Component.translatable("hwg.ammo.reloadrockets").withStyle(ChatFormatting.ITALIC));
    }
}