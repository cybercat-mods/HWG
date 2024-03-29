package mod.azure.hwg.blocks;

import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class FuelTankBlock extends Block {

    public FuelTankBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion());
    }

    private static void primeBlock(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            var tntEntity = new FuelTankEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            world.addFreshEntity(tntEntity);
        }
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var itemStack = player.getItemInHand(hand);
        var item = itemStack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE)
            return super.use(state, world, pos, player, hand, hit);
        else {
            primeBlock(world, pos);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide) {
            var blockPos = hit.getBlockPos();
            primeBlock(world, blockPos);
            world.removeBlock(blockPos, false);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.33f, 0f, 0.33f, 0.67f, 1.0f, 0.67f);
    }
}