package mod.azure.hwg.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.entity.blockentity.GunBlockEntity;

public class GunTableBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape X_LENGTH1 = Block.box(0, 0, 1, 16, 17, 16);

    private static final VoxelShape Y_LENGTH1 = Block.box(1, 0, 0, 16, 17, 16);

    public static final MapCodec<Block> CODEC = simpleCodec(GunTableBlock::new);

    public GunTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH));
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull VoxelShape getShape(
        BlockState state,
        @NotNull BlockGetter world,
        @NotNull BlockPos pos,
        @NotNull CollisionContext context
    ) {
        return state.getValue(FACING).getAxis() == Direction.Axis.X ? Y_LENGTH1 : X_LENGTH1;
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GunBlockEntity(pos, state);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(
        @NotNull BlockState state,
        Level level,
        @NotNull BlockPos pos,
        @NotNull Player player,
        @NotNull BlockHitResult hitResult
    ) {
        if (!level.isClientSide) {
            var screenHandlerFactory = state.getMenuProvider(level, pos);
            if (screenHandlerFactory != null)
                player.openMenu(screenHandlerFactory);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, Level world, @NotNull BlockPos pos) {
        return (MenuProvider) world.getBlockEntity(pos);
    }

    @Override
    public void onRemove(
        BlockState state,
        @NotNull Level world,
        @NotNull BlockPos pos,
        BlockState newState,
        boolean moved
    ) {
        if (state.getBlock() != newState.getBlock()) {
            var blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GunBlockEntity) {
                Containers.dropContents(world, pos, (GunBlockEntity) blockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, Level world, @NotNull BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

}
