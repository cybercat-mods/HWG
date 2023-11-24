package mod.azure.hwg.item.weapons;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.render.weapons.SPistolRender;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SPistolItem extends AnimatedItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public SPistolItem() {
        super(new Item.Properties().stacksTo(1).durability(HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_cap + 1));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
        if (entityLiving instanceof Player playerentity) {
            if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
                playerentity.getCooldowns().addCooldown(this, HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_cooldown);
                if (!worldIn.isClientSide) {
                    stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    var result = HWGGunBase.hitscanTrace(playerentity, 64, 1.0F);
                    if (result != null) {
                        if (result.getEntity() instanceof LivingEntity livingEntity) {
                            if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), stack) > 0)
                                livingEntity.setSecondsOnFire(100);
                            livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_damage);
                        }
                    } else {
                        var bullet = createArrow(worldIn, stack, playerentity);
                        bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 20.0F * 3.0F, 1.0F);
                        bullet.tickCount = -15;
                        if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), stack) > 0)
                            bullet.setSecondsOnFire(100);
                        worldIn.addFreshEntity(bullet);
                    }
                    worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), HWGSounds.SPISTOL, SoundSource.PLAYERS, 0.5F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 0.5F);
                    triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
                }
                var isInsideWaterBlock = playerentity.level().isWaterAt(playerentity.blockPosition());
                spawnLightSource(entityLiving, isInsideWaterBlock);
            } else
                worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 1.0F, 1.5F);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.isClientSide && entity instanceof Player player && player.getMainHandItem().getItem() instanceof SPistolItem) {
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                var passedData = new FriendlyByteBuf(Unpooled.buffer());
                passedData.writeBoolean(true);
                ClientPlayNetworking.send(HWGMod.SPISTOL, passedData);
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 3.0F, 1.5F);
            }
        }
    }

    public void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof SPistolItem) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(HWGItems.BULLETS) > 0) {
                removeAmmo(HWGItems.BULLETS, user);
                user.getItemInHand(hand).hurtAndBreak(-1, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
                user.level().playSound(null, user.getX(), user.getY(), user.getZ(), HWGSounds.PISTOLRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
                if (!user.level().isClientSide)
                    triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()), "shoot_controller", "reload");
            }
        }
    }

    public BulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        return new BulletEntity(worldIn, shooter, HWGMod.config.gunconfigs.silencedpistolconfigs.silenced_pistol_damage);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        tooltip.add(Component.translatable("hwg.ammo.reloadbullets").withStyle(ChatFormatting.ITALIC));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final SPistolRender renderer = new SPistolRender();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
}