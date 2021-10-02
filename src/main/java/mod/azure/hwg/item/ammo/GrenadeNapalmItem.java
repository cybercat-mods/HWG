package mod.azure.hwg.item.ammo;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.NapalmGrenadeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrenadeNapalmItem extends Item {

	public GrenadeNapalmItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!user.getItemCooldownManager().isCoolingDown(this)) {
			user.getItemCooldownManager().set(this, 25);
			if (!world.isClient) {
				NapalmGrenadeEntity snowballEntity = new NapalmGrenadeEntity(world, user);
				snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
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
