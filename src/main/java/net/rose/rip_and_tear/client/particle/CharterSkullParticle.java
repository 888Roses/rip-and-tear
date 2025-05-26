package net.rose.rip_and_tear.client.particle;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;
import net.minecraft.particle.SimpleParticleType;
import net.rose.rip_and_tear.common.util.Mathf;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.particle.*;

public class CharterSkullParticle extends SpriteBillboardParticle {
    protected CharterSkullParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);

        this.ascending = true;
        this.velocityY += Mathf.random(
                world,
                CHARTER_SKULL_PARTICLE_VERTICAL_VELOCITY_MIN,
                CHARTER_SKULL_PARTICLE_VERTICAL_VELOCITY_MAX
        ) * CHARTER_SKULL_PARTICLE_VELOCITY_MULTIPLIER;
        this.velocityX += Mathf.random(
                world,
                -CHARTER_SKULL_PARTICLE_HORIZONTAL_VELOCITY,
                CHARTER_SKULL_PARTICLE_HORIZONTAL_VELOCITY
        ) * CHARTER_SKULL_PARTICLE_VELOCITY_MULTIPLIER;
        this.velocityZ += Mathf.random(
                world,
                -CHARTER_SKULL_PARTICLE_HORIZONTAL_VELOCITY,
                CHARTER_SKULL_PARTICLE_HORIZONTAL_VELOCITY
        ) * CHARTER_SKULL_PARTICLE_VELOCITY_MULTIPLIER;

        this.velocityMultiplier = CHARTER_SKULL_PARTICLE_VELOCITY_DAMPEN;
        this.maxAge = world.getRandom().nextBetween(CHARTER_SKULL_PARTICLE_AGE_MIN, CHARTER_SKULL_PARTICLE_AGE_MAX);
        this.scale *= Mathf.random(world, CHARTER_SKULL_PARTICLE_SCALE_MIN, CHARTER_SKULL_PARTICLE_SCALE_MAX);
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
            var particle = new CharterSkullParticle(clientWorld, x, y, z);
            particle.setColor(1.0F, 1.0F, 1.0F);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
