package mod.azure.hwg.entity.goal;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.HWGEntity;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.entity.projectiles.FireballEntity;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import mod.azure.hwg.item.weapons.Assasult1Item;
import mod.azure.hwg.item.weapons.AssasultItem;
import mod.azure.hwg.item.weapons.BalrogItem;
import mod.azure.hwg.item.weapons.BrimstoneItem;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import mod.azure.hwg.item.weapons.GPistolItem;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import mod.azure.hwg.item.weapons.LugerItem;
import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.item.weapons.PistolItem;
import mod.azure.hwg.item.weapons.SPistolItem;
import mod.azure.hwg.item.weapons.ShotgunItem;
import mod.azure.hwg.item.weapons.SniperItem;
import mod.azure.hwg.util.registry.HWGSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class WeaponGoal extends AbstractRangedAttack {
	
	public WeaponGoal(HWGEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
			double zOffSetModifier, float damage) {
		super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
	}

	public WeaponGoal(HWGEntity parentEntity) {
		super(parentEntity);
	}

	@Override
	public AttackSound getDefaultAttackSound() {
		Item heldItem = this.parentEntity.getEquippedStack(EquipmentSlot.MAINHAND).getItem();
		return new AttackSound(heldItem instanceof GPistolItem ? HWGSounds.SPISTOL
				: heldItem instanceof SPistolItem ? HWGSounds.SPISTOL
						: heldItem instanceof PistolItem ? HWGSounds.PISTOL
								: heldItem instanceof LugerItem ? HWGSounds.LUGER
										: heldItem instanceof AssasultItem ? HWGSounds.AK
												: heldItem instanceof Assasult1Item ? HWGSounds.SMG
														: heldItem instanceof ShotgunItem ? HWGSounds.SHOTGUN
																: heldItem instanceof ShotgunItem ? HWGSounds.SHOTGUN
																		: heldItem instanceof HellhorseRevolverItem
																				? HWGSounds.REVOLVER
																				: heldItem instanceof FlamethrowerItem
																						? SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR
																						: heldItem instanceof BrimstoneItem
																								? SoundEvents.ITEM_FIRECHARGE_USE
																								: heldItem instanceof BalrogItem
																										? SoundEvents.ENTITY_GENERIC_EXPLODE
																										: heldItem instanceof Minigun
																												? HWGSounds.MINIGUN
																												: HWGSounds.SNIPER,
				1, 1);
	}

	@Override
	public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
		Item heldItem = this.parentEntity.getEquippedStack(EquipmentSlot.MAINHAND).getItem();
		return (heldItem instanceof PistolItem || heldItem instanceof LugerItem || heldItem instanceof AssasultItem
				|| heldItem instanceof Assasult1Item || heldItem instanceof GPistolItem
				|| heldItem instanceof SPistolItem || heldItem instanceof SniperItem
				|| heldItem instanceof HellhorseRevolverItem || heldItem instanceof Minigun) ? new BulletEntity(world,
						this.parentEntity,
						(heldItem instanceof PistolItem ? HWGMod.config.weapons.pistol_damage
								: heldItem instanceof LugerItem ? HWGMod.config.weapons.luger_damage
										: heldItem instanceof AssasultItem ? HWGMod.config.weapons.ak47_damage
												: heldItem instanceof Assasult1Item ? HWGMod.config.weapons.smg_damage
														: heldItem instanceof GPistolItem
																? HWGMod.config.weapons.golden_pistol_damage
																: heldItem instanceof SPistolItem
																		? HWGMod.config.weapons.silenced_pistol_damage
																		: heldItem instanceof HellhorseRevolverItem
																				? HWGMod.config.weapons.hellhorse_damage
																				: heldItem instanceof Minigun
																						? HWGMod.config.weapons.minigun_damage
																						: HWGMod.config.weapons.sniper_damage))
						: heldItem instanceof FlamethrowerItem ? new FlameFiring(world, this.parentEntity)
								: heldItem instanceof BrimstoneItem ? new BlazeRodEntity(world, this.parentEntity)
										: heldItem instanceof BalrogItem ? new FireballEntity(world, this.parentEntity)
												: new ShellEntity(world, this.parentEntity);
	}
}