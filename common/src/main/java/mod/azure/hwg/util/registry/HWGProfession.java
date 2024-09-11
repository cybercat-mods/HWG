package mod.azure.hwg.util.registry;

import com.google.common.collect.ImmutableSet;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.mixins.PointOfInterestTypesInvoker;
import mod.azure.hwg.particle.HWGParticleType;
import mod.azure.hwg.platform.Services;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.function.Supplier;

public class HWGProfession {

    static <T extends VillagerProfession> Supplier<T> registerProfession(String modID, String professionName, Supplier<T> supplier) {
        return Services.COMMON_REGISTRY.registerProfession(modID, professionName, supplier);
    }

    static <T extends PoiType> Supplier<T> registerPOI(String modID, String poiName, Supplier<T> supplier) {
        return Services.COMMON_REGISTRY.registerPOI(modID, poiName, supplier);
    }
    public static final Supplier<PoiType> GUNSMITH_POI = HWGProfession.registerPOI(
            CommonMod.MOD_ID, "gun_smith", () -> new PoiType(PointOfInterestTypesInvoker.invokeGetBlockStates(HWGBlocks.GUN_TABLE.get()), 1, 10));

    public static final Supplier<VillagerProfession> GUNSMITH = HWGProfession.registerProfession(
            CommonMod.MOD_ID, "gun_smith", () -> new VillagerProfession("gun_smith", holder -> holder.value().equals(GUNSMITH_POI.get()), holder -> holder.value().equals(GUNSMITH_POI.get()), ImmutableSet.of(), ImmutableSet.of(HWGBlocks.GUN_TABLE.get()), SoundEvents.ITEM_FRAME_REMOVE_ITEM));

    public static void init() {
    }
}
