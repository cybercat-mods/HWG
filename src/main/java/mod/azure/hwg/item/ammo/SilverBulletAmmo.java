package mod.azure.hwg.item.ammo;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SilverBulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SilverBulletAmmo extends ArrowItem {

	public final float damage;

	public SilverBulletAmmo(float damageIn) {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
		this.damage = damageIn;
	}

	@Override
	public SilverBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		SilverBulletEntity arrowentity = new SilverBulletEntity(worldIn, shooter);
		arrowentity.setDamage(this.damage);
		return arrowentity;
	}

}