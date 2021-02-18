package mod.azure.hwg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class GunTableBlock extends Block {// implements BlockEntityProvider {

	public static final DirectionProperty direction = HorizontalFacingBlock.FACING;

	public GunTableBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(direction, Direction.NORTH));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(direction, ctx.getPlayerFacing());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rot) {
		return state.with(direction, rot.rotate(state.get(direction)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.get(direction)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(direction);
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