package mod.azure.hwg.item.ammo;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletAmmo extends ArrowItem {

	public final float damage;

	public BulletAmmo(float damageIn) {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
		this.damage = damageIn;
	}

	@Override
	public BulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter);
		arrowentity.setDamage(this.damage);
		return arrowentity;
	}

}