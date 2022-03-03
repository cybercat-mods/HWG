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
public class FlareParticle extends SpriteBillboardParticle {
	private FlareParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY,
			double velocityZ, float red, float green, float blue) {
		super(world, x, y, z);
		this.scale(3.0F);
		this.setBoundingBoxSpacing(0.25F, 0.25F);
		this.maxAge = this.random.nextInt(50) + 340;
		this.gravityStrength = 3.0E-6F;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.velocityX = velocityX;
		this.velocityY = velocityY + (double) (this.random.nextFloat() / 500.0F);
		this.velocityZ = velocityZ;
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ < this.maxAge && this.alpha > 0.0F) {
			this.velocityX += (double) (this.random.nextFloat() / 5000.0F
					* (float) (this.random.nextBoolean() ? 1 : -1));
			this.velocityZ += (double) (this.random.nextFloat() / 5000.0F
					* (float) (this.random.nextBoolean() ? 1 : -1));
			this.velocityY -= (double) this.gravityStrength;
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			if (this.age >= this.maxAge - 60 && this.alpha > 0.01F) {
				this.alpha -= 0.015F;
			}

		} else {
			this.markDead();
		}
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public static class BlackSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public BlackSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 0, 0, 0);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class RedSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public RedSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 22, 156, 156);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class GreenSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public GreenSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 199, 78, 189);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class BrownSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public BrownSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 71, 79, 82);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class BlueSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public BlueSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 254, 216, 61);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class PurpleSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public PurpleSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 128, 199, 31);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class CyanSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public CyanSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 176, 46, 38);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class LightGraySmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public LightGraySmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 131, 84, 50);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class GraySmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public GraySmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 157, 157, 151);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class PinkSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public PinkSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 10, 150, 156);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			campfireSmokeParticle.setAlpha(0.4F);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class LimeSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public LimeSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 137, 50, 184);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class YellowSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public YellowSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 60, 68, 170);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class LightBlueSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public LightBlueSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 249, 128, 29);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class MagentaSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public MagentaSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 94, 124, 22);
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
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 58, 179, 216);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class WhiteSmokeFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public WhiteSmokeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 249, 255, 254);
			campfireSmokeParticle.setSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}
}
