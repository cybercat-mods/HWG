package mod.azure.hwg.blocks;

import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FuelTankBlock extends Block {

	public FuelTankBlock() {
		super(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).nonOpaque());
	}

	@Override
	public boolean shouldDropItemsOnExplosion(Explosion explosion) {
		return false;
	}

	private static void primeBlock(World world, BlockPos pos, LivingEntity igniter) {
		if (!world.isClient) {
			FuelTankEntity tntEntity = new FuelTankEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(),
					(double) pos.getZ() + 0.5D, igniter);
			world.spawnEntity(tntEntity);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
			return super.onUse(state, world, pos, player, hand, hit);
		} else {
			primeBlock(world, pos, player);
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
			return ActionResult.success(world.isClient);
		}
	}

	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		if (!world.isClient) {
			Entity entity = projectile.getOwner();
			BlockPos blockPos = hit.getBlockPos();
			primeBlock(world, blockPos, entity instanceof LivingEntity ? (LivingEntity) entity : null);
			world.removeBlock(blockPos, false);
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0.33f, 0f, 0.33f, 0.67f, 1.0f, 0.67f);
	}
}