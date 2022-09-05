package mod.azure.hwg.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class BrimParticle extends SpriteBillboardParticle {
	private BrimParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY,
			double velocityZ, float red, float green, float blue) {
		super(world, x, y, z);
		this.scale(3.0F);
		this.setBoundingBoxSpacing(0.25F, 0.25F);
		this.maxAge = this.random.nextInt(50) + 80;
		this.gravityStrength = 3.0E-6F;
		this.colorAlpha = 0.75F;
		this.colorRed = red;
		this.colorGreen = green;
		this.colorBlue = blue;
		this.velocityX = velocityX;
		this.velocityY = velocityY + (double) (this.random.nextFloat() / 500.0F);
		this.velocityZ = velocityZ;
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ < this.maxAge && this.colorAlpha > 0.0F) {
			this.velocityX += (double) (this.random.nextFloat() / 5000.0F
					* (float) (this.random.nextBoolean() ? 1 : -1));
			this.velocityZ += (double) (this.random.nextFloat() / 5000.0F
					* (float) (this.random.nextBoolean() ? 1 : -1));
			this.velocityY -= (double) this.gravityStrength;
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			if (this.age >= this.maxAge - 60 && this.colorAlpha > 0.01F) {
				this.colorAlpha -= 0.015F;
			}

		} else {
			this.markDead();
		}
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public static class RedSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public RedSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			BrimParticle campfireSmokeParticle = new BrimParticle(clientWorld, d, e, f, g, h, i, 60, 68, 170);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class OrangeSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public OrangeSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			BrimParticle campfireSmokeParticle = new BrimParticle(clientWorld, d, e, f, g, h, i, 58, 179, 216);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}
}
