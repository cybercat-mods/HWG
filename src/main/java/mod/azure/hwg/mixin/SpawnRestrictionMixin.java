package mod.azure.hwg.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.azure.hwg.util.HWGMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {
	@Shadow
	private static <T extends MobEntity> void register(EntityType<T> type, SpawnRestriction.Location location,
			Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate) {
	}

	static {
		register(HWGMobs.TECHNOLESSER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				HostileEntity::canSpawnInDark);
		register(HWGMobs.TECHNOGREATER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				HostileEntity::canSpawnInDark);
	}
}