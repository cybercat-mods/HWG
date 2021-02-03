package mod.azure.hwg.util;

import mod.azure.hwg.entity.Technodemon2Entity;
import mod.azure.hwg.entity.Technodemon3Entity;
import mod.azure.hwg.entity.Technodemon4Entity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreater2Entity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class MobAttributes {

	public static void init() {
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER1, TechnodemonEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER2, Technodemon2Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER3, Technodemon3Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER4, Technodemon4Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOGREATER1, TechnodemonGreaterEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOGREATER2, TechnodemonGreater2Entity.createMobAttributes());

	}

}
