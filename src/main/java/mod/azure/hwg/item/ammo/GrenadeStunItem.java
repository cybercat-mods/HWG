package mod.azure.hwg.item.ammo;

import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrenadeStunItem extends Item {

	public GrenadeStunItem() {
		super(new Item.Settings());
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!user.getItemCooldownManager().isCoolingDown(this)) {
			user.getItemCooldownManager().set(this, 25);
			if (!world.isClient) {
				GrenadeEntity snowballEntity = new GrenadeEntity(world, user);
				snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
				snowballEntity.setVariant(5);
				snowballEntity.setState(1);
				world.spawnEntity(snowballEntity);
			}
			if (!user.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}
			return TypedActionResult.success(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}

}
