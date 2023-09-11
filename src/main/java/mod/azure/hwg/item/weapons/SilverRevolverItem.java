package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.render.weapons.SilverRevolverRender;
import mod.azure.hwg.compat.BWCompat;
import mod.azure.hwg.entity.projectiles.SBulletEntity;
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
import net.minecraft.world.level.Level;

public class SilverRevolverItem extends AnimatedItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public SilverRevolverItem() {
		super(new Item.Properties().stacksTo(1).durability(7));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				playerentity.getCooldowns().addCooldown(this, 5);
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					var result = HWGGunBase.hitscanTrace(playerentity, 64, 1.0F);
					if (result != null) {
						if (result.getEntity() instanceof LivingEntity livingEntity)
							livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), HWGMod.config.pistol_damage);
					} else {
						var bullet = createArrow(worldIn, stack, playerentity);
						bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 20.0F * 3.0F, 1.0F);
						bullet.tickCount = -15;
						if (entityLiving.getRandom().nextInt(1, 101) <= 20)
							bullet.setSecondsOnFire(100);
						worldIn.addFreshEntity(bullet);
					}
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), HWGSounds.REVOLVER, SoundSource.PLAYERS, 0.5F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
				}
				var isInsideWaterBlock = playerentity.level().isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			} else
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 1.0F, 1.5F);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (entity instanceof Player player)
				if (player.getMainHandItem().getItem() instanceof SilverRevolverItem) {
					if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
						var passedData = new FriendlyByteBuf(Unpooled.buffer());
						passedData.writeBoolean(true);
						ClientPlayNetworking.send(HWGMod.SILVERHELL, passedData);
					}
				}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SilverRevolverItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(BWCompat.SILVERBULLET) > 0) {
				removeAmmo(BWCompat.SILVERBULLET, user);
				user.getCooldowns().addCooldown(this, 16);
				user.getItemInHand(hand).hurtAndBreak(-1, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				if (!user.getCooldowns().isOnCooldown(user.getItemInHand(hand).getItem()))
					user.level().playSound((Player) null, user.getX(), user.getY(), user.getZ(), HWGSounds.REVOLVERRELOAD, SoundSource.PLAYERS, 0.5F, 1.0F);
				if (!user.level().isClientSide)
					triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()), "shoot_controller", "reload");
				break;
			}
		}
	}

	public SBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		var bullet = new SBulletEntity(worldIn, shooter, HWGMod.config.pistol_damage);
		return bullet;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("hwg.ammo.reloadsilverbullets").withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final SilverRevolverRender renderer = new SilverRevolverRender();

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