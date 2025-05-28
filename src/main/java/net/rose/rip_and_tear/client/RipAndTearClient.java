package net.rose.rip_and_tear.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.rose.rip_and_tear.client.render.block.EngravedDeepslateBlockEntityRenderer;
import net.rose.rip_and_tear.client.render.entity.WarperProjectileEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rose.rip_and_tear.client.particle.ModClientSideParticles;
import net.rose.rip_and_tear.client.render.entity.mob.StatueEntityModel;
import net.rose.rip_and_tear.client.render.entity.mob.StatueEntityRenderer;
import net.rose.rip_and_tear.common.init.ModBlockEntityTypes;
import net.rose.rip_and_tear.common.init.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;

public class RipAndTearClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(StatueEntityModel.LAYER, () -> StatueEntityModel.getTexturedModelData(false));
        EntityModelLayerRegistry.registerModelLayer(StatueEntityModel.LAYER_SLIM, () -> StatueEntityModel.getTexturedModelData(true));

        EntityRendererRegistry.register(ModEntityTypes.WARPER, WarperProjectileEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.STATUE, StatueEntityRenderer::new);

        BlockEntityRendererFactories.register(
                ModBlockEntityTypes.ENGRAVED_DEEPSLATE,
                EngravedDeepslateBlockEntityRenderer::new
        );

        ModClientSideParticles.register();
    }
}
