package mod.azure.hwg.item.ammo;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FragGrenadeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrenadeFragItem extends Item {

	public GrenadeFragItem() {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!world.isClient) {
			FragGrenadeEntity snowballEntity = new FragGrenadeEntity(world, user);
			snowballEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(snowballEntity);
		}
		if (!user.abilities.creativeMode) {
			itemStack.decrement(1);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}

}
