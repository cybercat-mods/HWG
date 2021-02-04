package mod.azure.hwg.util;

import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class MobAttributes {

	public static void init() {
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER, TechnodemonEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOGREATER, TechnodemonGreaterEntity.createMobAttributes());

	}

}
