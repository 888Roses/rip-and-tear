package net.rose.rip_and_tear.client.content.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.rose.rip_and_tear.util.Mathf;

public class CharterSkullParticle extends SpriteBillboardParticle {
    protected CharterSkullParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        ascending = true;

        final float globalMul = 0.15F;
        final float minVelY = 0.0075F;
        final float maxVelY = 0.01F;
        final float horizontalVel = 0.001F;

        var rand = world.getRandom();
        velocityY += Mathf.remap01(rand.nextFloat(), minVelY, maxVelY) * globalMul;
        velocityX += Mathf.remap01(rand.nextFloat(), -horizontalVel, horizontalVel) * globalMul;
        velocityZ += Mathf.remap01(rand.nextFloat(), -horizontalVel, horizontalVel) * globalMul;
        velocityMultiplier = 0.875F;
        maxAge = rand.nextBetween(12, 16);
        scale *= Mathf.remap01(rand.nextFloat(), 0.75F, 1.1F);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientWorld clientWorld,
                double x,
                double y,
                double z,
                double velX,
                double velY,
                double velZ
        ) {
            var particle = new CharterSkullParticle(clientWorld, x, y + 0.5, z);
            particle.setSprite(this.spriteProvider);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}
