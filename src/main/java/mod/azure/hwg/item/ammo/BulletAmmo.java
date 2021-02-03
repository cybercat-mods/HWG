package mod.azure.hwg.item.ammo;

import java.util.List;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class BulletAmmo extends ArrowItem {

	public final float damage;

	public BulletAmmo(float damageIn) {
		super(new Item.Settings().group(HWGMod.WeaponItemGroup));
		this.damage = damageIn;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("\u00A7o" + "Used for the Pistol."));
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public BulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter);
		arrowentity.setDamage(this.damage);
		return arrowentity;
	}

}