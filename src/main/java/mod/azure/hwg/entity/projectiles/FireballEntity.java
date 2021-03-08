package mod.azure.hwg.entity.projectiles;

import java.util.List;

import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.registry.HWGItems;
import mod.azure.hwg.util.registry.HWGParticles;
import mod.azure.hwg.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FireballEntity extends PersistentProjectileEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private LivingEntity shooter;

	public FireballEntity(EntityType<? extends FireballEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public FireballEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.FIREBALL, owner, world);
		this.shooter = owner;
	}

	protected FireballEntity(EntityType<? extends FireballEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected FireballEntity(EntityType<? extends FireballEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	@Override
	public boolean doesRenderOnFire() {
		return true;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
			this.remove();
		}
	}

	@Override
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.ticksInAir = tag.getShort("life");
	}

	@Override
	public void tick() {
		super.tick();
		this.isOnFire();
		boolean bl = this.isNoClip();
		Vec3d vec3d = this.getVelocity();
		if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
			float f = MathHelper.sqrt(squaredHorizontalLength(vec3d));
			this.yaw = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D);
			this.pitch = (float) (MathHelper.atan2(vec3d.y, (double) f) * 57.2957763671875D);
			this.prevYaw = this.yaw;
			this.prevPitch = this.pitch;
		}
		if (this.age >= 50) {
			this.remove();
		}
		if (this.inAir && !bl) {
			this.age();
			++this.timeInAir;
		} else {
			this.timeInAir = 0;
			Vec3d vec3d3 = this.getPos();
			Vec3d vector3d3 = vec3d3.add(vec3d);
			HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vector3d3,
					RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			if (((HitResult) hitResult).getType() != HitResult.Type.MISS) {
				vector3d3 = ((HitResult) hitResult).getPos();
			}
			while (!this.removed) {
				EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vector3d3);
				if (entityHitResult != null) {
					hitResult = entityHitResult;
				}
				if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult) hitResult).getEntity();
					Entity entity2 = this.getOwner();
					if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity
							&& !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
						hitResult = null;
						entityHitResult = null;
					}
				}
				if (hitResult != null && !bl) {
					this.onCollision((HitResult) hitResult);
					this.velocityDirty = true;
				}
				if (entityHitResult == null || this.getPierceLevel() <= 0) {
					break;
				}
				hitResult = null;
			}
			vec3d = this.getVelocity();
			double d = vec3d.x;
			double e = vec3d.y;
			double g = vec3d.z;
			double h = this.getX() + d;
			double j = this.getY() + e;
			double k = this.getZ() + g;
			float l = MathHelper.sqrt(squaredHorizontalLength(vec3d));
			if (bl) {
				this.yaw = (float) (MathHelper.atan2(-d, -g) * 57.2957763671875D);
			} else {
				this.yaw = (float) (MathHelper.atan2(d, g) * 57.2957763671875D);
			}
			this.pitch = (float) (MathHelper.atan2(e, (double) l) * 57.2957763671875D);
			this.pitch = updateRotation(this.prevPitch, this.pitch);
			this.yaw = updateRotation(this.prevYaw, this.yaw);
			float m = 0.99F;

			this.setVelocity(vec3d.multiply((double) m));
			if (!this.hasNoGravity() && !bl) {
				Vec3d vec3d5 = this.getVelocity();
				this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
			}
			this.updatePosition(h, j, k);
			this.checkBlockCollision();
			float q = 4.0F;
			int k2 = MathHelper.floor(this.getX() - (double) q - 1.0D);
			int l2 = MathHelper.floor(this.getX() + (double) q + 1.0D);
			int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
			int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
			int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
			int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
			List<Entity> list = this.world.getOtherEntities(this,
					new Box((double) k2, (double) t, (double) v, (double) l2, (double) u, (double) w));
			Vec3d vec3d2 = new Vec3d(this.getX(), this.getY(), this.getZ());
			for (int x = 0; x < list.size(); ++x) {
				Entity entity = (Entity) list.get(x);
				double y = (double) (MathHelper.sqrt(entity.squaredDistanceTo(vec3d2)) / q);
				if (y <= 1.0D) {
					if (this.world.isClient) {
						double d2 = this.getX()
								+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
						double e2 = this.getY() + 0.05D + this.random.nextDouble();
						double f2 = this.getZ()
								+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
						this.world.addParticle(ParticleTypes.FLAME, true, d2, e2, f2, 0, 0, 0);
						this.world.addParticle(HWGParticles.BRIM_ORANGE, true, d2, e2, f2, 0, 0, 0);
						this.world.addParticle(HWGParticles.BRIM_RED, true, d2, e2, f2, 0, 0, 0);
					}
				}
			}

			List<Entity> list1 = this.world.getOtherEntities(this, new Box(this.getBlockPos().up()).expand(1D, 5D, 1D));
			for (int x = 0; x < list1.size(); ++x) {
				Entity entity = (Entity) list1.get(x);
				double y = (double) (MathHelper.sqrt(entity.distanceTo(this)));
				if (y <= 1.0D) {
					if (entity.isAlive()) {
						entity.damage(DamageSource.magic(this, this.shooter), 3);
						if (!(entity instanceof FireballEntity && this.getOwner() instanceof PlayerEntity)) {
							entity.setFireTicks(90);
						}
					}
				}
			}
		}
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HWGItems.BULLETS) {
		}
	}

	@Override
	public boolean hasNoGravity() {
		if (this.isSubmergedInWater()) {
			return false;
		} else {
			return true;
		}
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.BLOCK_FIRE_AMBIENT;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			Entity entity = this.getOwner();
			if (entity == null || !(entity instanceof MobEntity)
					|| this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
				if (this.world.isAir(blockPos)) {
					this.world.setBlockState(blockPos, AbstractFireBlock.getState(this.world, blockPos));
				}
			}
			this.remove();
		}
		this.setSound(SoundEvents.BLOCK_FIRE_AMBIENT);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			this.remove();
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(HWGItems.BULLETS);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}