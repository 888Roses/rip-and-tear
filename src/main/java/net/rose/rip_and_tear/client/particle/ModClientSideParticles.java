package net.rose.rip_and_tear.client.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.rose.rip_and_tear.common.init.ModParticleTypes;

public class ModClientSideParticles {
    public static void register() {
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.CHARTER_SKULL, CharterSkullParticle.Factory::new
        );
        ParticleFactoryRegistry.getInstance().register(
                ModParticleTypes.CHARTER_HIT, CharterHitParticle.Factory::new
        );
    }
}
