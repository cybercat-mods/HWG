package mod.azure.hwg.mixin;

import java.util.Map;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Holder;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

@Mixin(PointOfInterestTypes.class)
public interface PointOfInterestTypesInvoker {

	@Accessor("STATE_TO_TYPE")
	static Map<BlockState, Holder<PointOfInterestType>> getTypeByState() {
		throw new AssertionError();
	}

	@Invoker("states")
	static Set<BlockState> invokeGetBlockStates(Block block) {
		throw new AssertionError();
	}

	@Invoker("addStates")
	static void invokeRegisterBlockStates(Holder<PointOfInterestType> holder) {
		throw new AssertionError();
	}
}
