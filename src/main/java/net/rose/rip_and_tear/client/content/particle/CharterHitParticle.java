package net.rose.rip_and_tear.client.content.particle;

import net.minecraft.client.particle.DamageParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class CharterHitParticle extends DamageParticle {
    private final float tint;

    public CharterHitParticle(
            ClientWorld world,
            double x, double y, double z,
            double velocityX, double velocityY, double velocityZ
    ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        tint = MathHelper.nextFloat(random, 0.9F, 1);
        this.velocityMultiplier = 0.2F;
        this.velocityY = 0;
        resetTint();
    }

    @Override
    public void tick() {
        super.tick();
        resetTint();
    }

    private void resetTint() {
        red = green = blue = tint;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType type, ClientWorld world, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            CharterHitParticle particle = new CharterHitParticle(world, x, y, z, velocityX, velocityY, velocityZ);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}