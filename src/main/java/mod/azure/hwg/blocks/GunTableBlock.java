package mod.azure.hwg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GunTableBlock extends Block  {//implements BlockEntityProvider {

	public GunTableBlock(Settings settings) {
		super(settings);
	}

//	@Override
//	public BlockEntity createBlockEntity(BlockView world) {
//		return new GunBlockEntity();
//	}
//	
//	@Override
//	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
//			BlockHitResult hit) {
//		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
//		return ActionResult.SUCCESS;
//	}

}