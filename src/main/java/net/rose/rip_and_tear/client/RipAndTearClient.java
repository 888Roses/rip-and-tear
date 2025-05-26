package net.rose.rip_and_tear.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rose.rip_and_tear.client.content.particle.ModClientSideParticles;
import net.rose.rip_and_tear.client.content.WarperEntityRenderer;
import net.rose.rip_and_tear.content.ModEntities;

public class RipAndTearClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.WARPER, WarperEntityRenderer::new);
        ModClientSideParticles.register();
    }
}
