package net.rose.rip_and_tear.client;

import net.rose.rip_and_tear.client.render.entity.WarperProjectileEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rose.rip_and_tear.client.particle.ModClientSideParticles;
import net.rose.rip_and_tear.common.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;

public class RipAndTearClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.WARPER, WarperProjectileEntityRenderer::new);
        ModClientSideParticles.register();
    }
}
