package mod.azure.hwg.util;

import java.util.LinkedList;
import java.util.List;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BlazeRodEntity;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.entity.projectiles.EMPGrenadeEntity;
import mod.azure.hwg.entity.projectiles.FireballEntity;
import mod.azure.hwg.entity.projectiles.FlameFiring;
import mod.azure.hwg.entity.projectiles.FragGrenadeEntity;
import mod.azure.hwg.entity.projectiles.MBulletEntity;
import mod.azure.hwg.entity.projectiles.NapalmGrenadeEntity;
import mod.azure.hwg.entity.projectiles.RocketEntity;
import mod.azure.hwg.entity.projectiles.ShellEntity;
import mod.azure.hwg.entity.projectiles.SmokeGrenadeEntity;
import mod.azure.hwg.entity.projectiles.StunGrenadeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ProjectilesEntityRegister {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

	public static EntityType<ShellEntity> SHELL = projectile(ShellEntity::new, "shell");
	public static EntityType<BulletEntity> BULLETS = projectile(BulletEntity::new, "bullets");
	public static EntityType<RocketEntity> ROCKETS = projectile(RocketEntity::new, "rockets");
	public static EntityType<FlameFiring> FIRING = projectile1(FlameFiring::new, "flame_firing");
	public static EntityType<MBulletEntity> MBULLETS = projectile(MBulletEntity::new, "mbullets");
	public static EntityType<BlazeRodEntity> BLAZEROD = projectile(BlazeRodEntity::new, "blazerod");
	public static EntityType<FireballEntity> FIREBALL = projectile(FireballEntity::new, "fireball");
	public static EntityType<EMPGrenadeEntity> EMP_GRENADE = projectile(EMPGrenadeEntity::new, "emp_grenade");
	public static EntityType<FragGrenadeEntity> FRAG_GRENADE = projectile(FragGrenadeEntity::new, "frag_grenade");
	public static EntityType<StunGrenadeEntity> STUN_GRENADE = projectile(StunGrenadeEntity::new, "stun_grenade");
	public static EntityType<SmokeGrenadeEntity> SMOKE_GRENADE = projectile(SmokeGrenadeEntity::new, "smoke_grenade");
	public static EntityType<NapalmGrenadeEntity> NAPALM_GRENADE = projectile(NapalmGrenadeEntity::new, "napalm_grenade");

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(HWGMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}

	private static <T extends Entity> EntityType<T> projectile1(EntityType.EntityFactory<T> factory, String id) {
		return projectile1(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile1(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(1.5F, 1.5F, false)).disableSummon().spawnableFarFromPlayer()
				.fireImmune().trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(HWGMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}
}
