package mod.azure.hwg.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class FlareParticle extends TextureSheetParticle {
    private FlareParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float red, float green, float blue) {
        super(world, x, y, z);
        this.scale(3.0F);
        this.setSize(0.25F, 0.25F);
        this.lifetime = this.random.nextInt(50) + 780;
        this.gravity = 3.0E-6F;
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.alpha = 0.75F;
        this.xd = velocityX;
        this.yd = velocityY + (double) (this.random.nextFloat() / 500.0F);
        this.zd = velocityZ;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && this.alpha > 0.0F) {
            this.xd += this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1);
            this.zd += this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1);
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.age >= this.lifetime && this.alpha > 0.01F)
                this.alpha -= 0.015F;
        } else
            this.remove();
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class BlackSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public BlackSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 0, 0, 0);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class RedSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public RedSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 22, 156, 156);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class GreenSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public GreenSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 199, 78, 189);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class BrownSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public BrownSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 71, 79, 82);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class BlueSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public BlueSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 254, 216, 61);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class PurpleSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public PurpleSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 128, 199, 31);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class CyanSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public CyanSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 176, 46, 38);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class LightGraySmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public LightGraySmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 131, 84, 50);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class GraySmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public GraySmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            FlareParticle campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 157, 157, 151);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class PinkSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public PinkSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 10, 150, 156);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            campfireSmokeParticle.setAlpha(0.4F);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class LimeSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public LimeSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 137, 50, 184);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class YellowSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public YellowSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 60, 68, 170);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class LightBlueSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public LightBlueSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 249, 128, 29);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class MagentaSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public MagentaSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 94, 124, 22);
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
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 58, 179, 216);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class WhiteSmokeFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public WhiteSmokeFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            var campfireSmokeParticle = new FlareParticle(clientWorld, d, e, f, g, h, i, 249, 255, 254);
            campfireSmokeParticle.pickSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }
}
