package mod.azure.hwg.mixin;

import java.util.Map;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

@Mixin(PointOfInterestTypes.class)
public interface PointOfInterestTypesInvoker {

	@Accessor("POI_STATES_TO_TYPE")
	static Map<BlockState, RegistryEntry<PointOfInterestType>> getTypeByState() {
		throw new AssertionError();
	}

	@Invoker("getStatesOfBlock")
	static Set<BlockState> invokeGetBlockStates(Block block) {
		throw new AssertionError();
	}

	@Invoker("registerStates")
	static void invokeRegisterBlockStates(RegistryEntry<PointOfInterestType> poiTypeEntry, Set<BlockState> states) {
		throw new AssertionError();
	}
}
