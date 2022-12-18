package mod.azure.hwg.compat;

import java.util.LinkedList;
import java.util.List;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.SBulletEntity;
import mod.azure.hwg.item.ammo.BulletAmmo;
import mod.azure.hwg.item.weapons.SilverGunItem;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BWCompat {

	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

	public static SilverGunItem SILVERGUN = item(new SilverGunItem(), "silvergun");
	public static BulletAmmo SILVERBULLET = item(new BulletAmmo(), "silver_bullet");
	public static SilverRevolverItem SILVERHELLHORSE = item(new SilverRevolverItem(), "shellhorse_revolver");
	public static EntityType<SBulletEntity> SILVERBULLETS = projectile(SBulletEntity::new, "silverbullets");

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registries.ITEM, new Identifier(HWGMod.MODID, id), c);
		return c;
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registries.ENTITY_TYPE, new Identifier(HWGMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}
}
