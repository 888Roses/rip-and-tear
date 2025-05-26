package net.rose.rip_and_tear.client.particle;

import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.DamageParticle;
import net.minecraft.particle.SimpleParticleType;
import net.rose.rip_and_tear.common.util.Mathf;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

public class CharterHitParticle extends DamageParticle {
    private final float tint;

    public CharterHitParticle(
            ClientWorld world,
            double x, double y, double z,
            double velocityX, double velocityY, double velocityZ
    ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.tint = 1f;
        this.velocityMultiplier = 0.2f;
        this.velocityY = 0;
        resetTint();
    }

    @Override
    public void tick() {
        super.tick();
        resetTint();
    }

    private void resetTint() {
        this.red = this.green = this.blue = this.tint;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(
                SimpleParticleType type, ClientWorld world,
                double x, double y, double z,
                double vx, double vy, double vz
        ) {
            var particle = new CharterHitParticle(world, x, y, z, vx, vy, vz);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}