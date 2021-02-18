package mod.azure.hwg.entity.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class CustomFireworkEntity extends FireworkRocketEntity {

	public CustomFireworkEntity(World world, ItemStack stack, Entity entity, double x, double y, double z,
			boolean shotAtAngle) {
		super(world, stack, entity, x, y, z, shotAtAngle);
	}

	public CustomFireworkEntity(EntityType<? extends FireworkRocketEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if (this.world.isClient) {
			double d2 = this.getX()
					+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
			double e2 = this.getY() + 0.05D + this.random.nextDouble();
			double f2 = this.getZ()
					+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
			this.world.addParticle(ParticleTypes.FLAME, true, d2, e2, f2, 0, 0, 0);
		}
		super.tick();
	}

}
