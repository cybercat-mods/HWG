package mod.azure.hwg.util.registry;

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
import mod.azure.hwg.entity.projectiles.flare.BlackFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.BlueFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.BrownFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.CyanFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.GrayFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.GreenFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.LightblueFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.LightgrayFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.LimeFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.MagentaFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.OrangeFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.PinkFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.PurpleFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.RedFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.WhiteFlareEntity;
import mod.azure.hwg.entity.projectiles.flare.YellowFlareEntity;
import mod.azure.hwg.entity.projectiles.launcher.EMPGEntity;
import mod.azure.hwg.entity.projectiles.launcher.FragGEntity;
import mod.azure.hwg.entity.projectiles.launcher.NapalmGEntity;
import mod.azure.hwg.entity.projectiles.launcher.SmokeGEntity;
import mod.azure.hwg.entity.projectiles.launcher.StunGEntity;
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
	public static EntityType<BlueFlareEntity> BLUE_FLARE = projectile(BlueFlareEntity::new, "blue_flare");
	public static EntityType<CyanFlareEntity> CYAN_FLARE = projectile(CyanFlareEntity::new, "cyan_flare");
	public static EntityType<GrayFlareEntity> GRAY_FLARE = projectile(GrayFlareEntity::new, "gray_flare");
	public static EntityType<LimeFlareEntity> LIME_FLARE = projectile(LimeFlareEntity::new, "lime_flare");
	public static EntityType<BlackFlareEntity> BLACK_FLARE = projectile(BlackFlareEntity::new, "black_flare");
	public static EntityType<BrownFlareEntity> BROWN_FLARE = projectile(BrownFlareEntity::new, "brown_flare");
	public static EntityType<GreenFlareEntity> GREEN_FLARE = projectile(GreenFlareEntity::new, "green_flare");
	public static EntityType<LightblueFlareEntity> LIGHTBLUE_FLARE = projectile(LightblueFlareEntity::new, "lightblue_flare");
	public static EntityType<LightgrayFlareEntity> LIGHTGRAY_FLARE = projectile(LightgrayFlareEntity::new, "lightgray_flare");
	public static EntityType<PinkFlareEntity> PINK_FLARE = projectile(PinkFlareEntity::new, "pink_flare");
	public static EntityType<PurpleFlareEntity> PURPLE_FLARE = projectile(PurpleFlareEntity::new, "purple_flare");
	public static EntityType<OrangeFlareEntity> ORANGE_FLARE = projectile(OrangeFlareEntity::new, "orange_flare");
	public static EntityType<MagentaFlareEntity> MAGENTA_FLARE = projectile(MagentaFlareEntity::new, "magenta_flare");
	public static EntityType<RedFlareEntity> RED_FLARE = projectile(RedFlareEntity::new, "red_flare");
	public static EntityType<WhiteFlareEntity> WHITE_FLARE = projectile(WhiteFlareEntity::new, "white_flare");
	public static EntityType<YellowFlareEntity> YELLOW_FLARE = projectile(YellowFlareEntity::new, "yellow_flare");
	public static EntityType<EMPGrenadeEntity> EMP_GRENADE = projectile(EMPGrenadeEntity::new, "emp_grenade");
	public static EntityType<FragGrenadeEntity> FRAG_GRENADE = projectile(FragGrenadeEntity::new, "frag_grenade");
	public static EntityType<StunGrenadeEntity> STUN_GRENADE = projectile(StunGrenadeEntity::new, "stun_grenade");
	public static EntityType<SmokeGrenadeEntity> SMOKE_GRENADE = projectile(SmokeGrenadeEntity::new, "smoke_grenade");
	public static EntityType<NapalmGrenadeEntity> NAPALM_GRENADE = projectile(NapalmGrenadeEntity::new, "napalm_grenade");
	public static EntityType<EMPGEntity> EMP_GRENADE_S = projectile(EMPGEntity::new, "emp_grenade_s");
	public static EntityType<FragGEntity> FRAG_GRENADE_S = projectile(FragGEntity::new, "frag_grenade_s");
	public static EntityType<StunGEntity> STUN_GRENADE_S = projectile(StunGEntity::new, "stun_grenade_s");
	public static EntityType<SmokeGEntity> SMOKE_GRENADE_S = projectile(SmokeGEntity::new, "smoke_grenade_s");
	public static EntityType<NapalmGEntity> NAPALM_GRENADE_S = projectile(NapalmGEntity::new, "napalm_grenade_s");

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(1).build();

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
				.fireImmune().trackRangeBlocks(90).trackedUpdateRate(1).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(HWGMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}
}
