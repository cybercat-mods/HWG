package mod.azure.hwg.entity.blockentity;

import mod.azure.hwg.blocks.TickingLightBlock;
import mod.azure.hwg.util.registry.HWGMobs;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TickingLightEntity extends BlockEntity {
	private int lifespan = 0;

	public TickingLightEntity(BlockPos blockPos, BlockState blockState) {
		super(HWGMobs.TICKING_LIGHT_ENTITY, blockPos, blockState);
	}

	public void refresh(int lifeExtension) {
		lifespan = -lifeExtension;
	}

	private void tick() {
		if (lifespan++ >= 5) {
			if (world.getBlockState(getPos()).getBlock() instanceof TickingLightBlock)
				world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			else
				markRemoved();
		}
	}

	public static void staticTick(World world, BlockPos blockPos, BlockState blockState,
			TickingLightEntity blockEntity) {
		blockEntity.tick();
	}
}
