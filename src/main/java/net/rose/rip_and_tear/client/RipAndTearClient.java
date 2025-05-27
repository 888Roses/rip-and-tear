package net.rose.rip_and_tear.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.rose.rip_and_tear.client.render.entity.WarperProjectileEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rose.rip_and_tear.client.particle.ModClientSideParticles;
import net.rose.rip_and_tear.client.render.entity.mob.StatueEntityModel;
import net.rose.rip_and_tear.client.render.entity.mob.StatueEntityRenderer;
import net.rose.rip_and_tear.common.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;

public class RipAndTearClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(StatueEntityModel.LAYER, () -> StatueEntityModel.getTexturedModelData(false));
        EntityModelLayerRegistry.registerModelLayer(StatueEntityModel.LAYER_SLIM, () -> StatueEntityModel.getTexturedModelData(true));

        EntityRendererRegistry.register(ModEntities.WARPER, WarperProjectileEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.STATUE, StatueEntityRenderer::new);

        ModClientSideParticles.register();
    }
}
