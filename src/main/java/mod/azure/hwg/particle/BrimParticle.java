package mod.azure.hwg.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class BrimParticle extends TextureSheetParticle {
	private BrimParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float red, float green, float blue) {
		super(world, x, y, z);
		this.scale(3.0F);
		this.setSize(0.25F, 0.25F);
		this.lifetime = this.random.nextInt(50) + 80;
		this.gravity = 3.0E-6F;
		this.alpha = 0.75F;
		this.rCol = red;
		this.gCol = green;
		this.bCol = blue;
		this.xd = velocityX;
		this.yd = velocityY + (double) (this.random.nextFloat() / 500.0F);
		this.zd = velocityZ;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ < this.lifetime && this.alpha > 0.0F) {
			this.xd += (double) (this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1));
			this.zd += (double) (this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1));
			this.yd -= (double) this.gravity;
			this.move(this.xd, this.yd, this.zd);
			if (this.age >= this.lifetime - 60 && this.alpha > 0.01F)
				this.alpha -= 0.015F;
		} else
			this.remove();
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public static class RedSmokeFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public RedSmokeFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			var campfireSmokeParticle = new BrimParticle(clientWorld, d, e, f, g, h, i, 60, 68, 170);
			campfireSmokeParticle.pickSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class OrangeSmokeFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public OrangeSmokeFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			var campfireSmokeParticle = new BrimParticle(clientWorld, d, e, f, g, h, i, 58, 179, 216);
			campfireSmokeParticle.pickSprite(this.spriteProvider);
			return campfireSmokeParticle;
		}
	}
}
