package mod.azure.hwg.entity.projectiles.flare;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.HWGSounds;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WhiteFlareEntity extends PersistentProjectileEntity {

	public int life;

	public WhiteFlareEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ProjectilesEntityRegister.WHITE_FLARE, world);
		this.updatePosition(x, y, z);
	}

	public WhiteFlareEntity(EntityType<? extends WhiteFlareEntity> entityType, World world) {
		super(entityType, world);
	}

	public WhiteFlareEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
		this(world, x, y, z, stack);
		this.setOwner(entity);
	}

	public WhiteFlareEntity(World world, ItemStack stack, LivingEntity shooter) {
		this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
	}

	public WhiteFlareEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
		this(world, x, y, z, stack);
	}

	public WhiteFlareEntity(World world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		this(world, stack, x, y, z, shotAtAngle);
		this.setOwner(entity);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 800) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.life == 0 && !this.isSilent()) {
			this.world.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), HWGSounds.FLAREGUN,
					SoundCategory.AMBIENT, 3.0F, 3.0F);
		}
		setNoGravity(false);

		++this.life;
		if (this.world.isClient) {
			this.world.addParticle(HWGParticles.WHITE_FLARE, true, this.getX(), this.getY() - 0.3D, this.getZ(),
					this.random.nextGaussian() * 0.05D, -this.getVelocity().y * 0.07D,
					this.random.nextGaussian() * 0.05D);
		}

		Vec3d vec3d = this.getVelocity();
		vec3d = this.getVelocity();
		this.setVelocity(vec3d.multiply((double) 0.99F));
		Vec3d vec3d5 = this.getVelocity();
		this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
	}

	@Override
	public void handleStatus(byte status) {
		super.handleStatus(status);
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return HWGSounds.FLAREGUN;
	}

	@Override
	public void onRemoved() {
		if (this.getBlockStateAtPos().getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().up()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().up()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().down()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().down(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().north()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().north(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().south()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().south(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().east()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().east(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		if (this.world.getBlockState(this.getBlockPos().west()).getBlock() instanceof LightBlock) {
			world.setBlockState(this.getBlockPos().west(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
		}
		super.onRemoved();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (this.isAlive() && world.getBlockState(blockHitResult.getBlockPos().up()).getBlock() instanceof AirBlock) {
			world.setBlockState(blockHitResult.getBlockPos().up(), Blocks.LIGHT.getDefaultState(),
					Block.NOTIFY_NEIGHBORS);
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSound(HWGSounds.FLAREGUN);
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected ItemStack asItemStack() {
		return new ItemStack(HWGItems.WHITE_FLARE);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		return false;
	}

}