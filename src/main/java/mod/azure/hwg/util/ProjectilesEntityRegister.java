package mod.azure.hwg.util;

import java.util.LinkedList;
import java.util.List;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.BulletEntity;
import mod.azure.hwg.entity.projectiles.FlameFiring;
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

	public static EntityType<BulletEntity> BULLETS = projectile(BulletEntity::new, "bullets");
	public static EntityType<FlameFiring> FIRING = projectile1(FlameFiring::new, "flame_firing");

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
				.dimensions(new EntityDimensions(1.5F, 1.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(HWGMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}
}
