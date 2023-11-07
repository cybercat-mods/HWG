package mod.azure.hwg.util;

import mod.azure.hwg.entity.MercEntity;
import mod.azure.hwg.entity.SpyEntity;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.util.registry.HWGMobs;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public record MobAttributes() {

    public static void init() {
        FabricDefaultAttributeRegistry.register(HWGMobs.SPY, SpyEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(HWGMobs.MERC, MercEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOLESSER, TechnodemonEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(HWGMobs.TECHNOGREATER, TechnodemonGreaterEntity.createMobAttributes());
    }

}
