package mod.azure.hwg.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.client.render.weapons.AKRender;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class AssasultItem extends AnimatedItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public int maxammo;
	public int cooldown;
	public String animation;

	public AssasultItem(int maxammo, int cooldown, String animation) {
		super(new Item.Properties().stacksTo(1).durability(maxammo));
		this.maxammo = maxammo;
		this.cooldown = cooldown;
		this.animation = animation;
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean canBeHurtBy(DamageSource source) {
		return false;
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1) && !playerentity.getCooldowns().isOnCooldown(this)) {
				playerentity.getCooldowns().addCooldown(this, this.cooldown);
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					var result = HWGGunBase.hitscanTrace(playerentity, 64, 1.0F);
					if (result != null) {
						if (result.getEntity() instanceof LivingEntity livingEntity) {
							livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), HWGMod.config.gunconfigs.ak47configs.ak47_damage);
							if (HWGMod.config.gunconfigs.bullets_disable_iframes_on_players == true || !(livingEntity instanceof Player)) {
								livingEntity.invulnerableTime = 0;
								livingEntity.setDeltaMovement(0, 0, 0);
							}
						}
					} else {
						var bullet = createArrow(worldIn, stack, playerentity);
						bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 20.0F * 3.0F, 1.0F);
						bullet.tickCount = -15;
						worldIn.addFreshEntity(bullet);
					}
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), HWGSounds.AK, SoundSource.PLAYERS, 0.25F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", this.animation);
				}
				var isInsideWaterBlock = playerentity.level().isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE).triggerableAnim(this.animation, RawAnimation.begin().then(this.animation, LoopType.PLAY_ONCE)).triggerableAnim("akreload", RawAnimation.begin().then("akreload", LoopType.PLAY_ONCE)));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (entity instanceof Player player)
				if (player.getMainHandItem().getItem() instanceof AssasultItem) {
					if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
						FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
						passedData.writeBoolean(true);
						ClientPlayNetworking.send(HWGMod.ASSASULT, passedData);
					}
				}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof AssasultItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(HWGItems.BULLETS) > 0) {
				removeAmmo(HWGItems.BULLETS, user);
				user.getItemInHand(hand).hurtAndBreak(-1, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				if (!user.getCooldowns().isOnCooldown(user.getItemInHand(hand).getItem()))
					user.level().playSound((Player) null, user.getX(), user.getY(), user.getZ(), HWGSounds.CLIPRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
				if (!user.level().isClientSide)
					triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()), "shoot_controller", "akreload");
			}
		}
	}

	public BulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		var bullet = new BulletEntity(worldIn, shooter, HWGMod.config.gunconfigs.ak47configs.ak47_damage);
		return bullet;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("hwg.ammo.reloadbullets").withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final AKRender renderer = new AKRender();

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