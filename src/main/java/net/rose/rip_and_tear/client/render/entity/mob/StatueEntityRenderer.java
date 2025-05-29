package net.rose.rip_and_tear.client.render.entity.mob;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.rose.rip_and_tear.common.entity.mob.StatueEntitySkinType;
import net.rose.rip_and_tear.common.init.ModEntityComponents;

public class StatueEntityRenderer extends BipedEntityRenderer<StatueEntity, StatueEntityRenderState, StatueEntityModel> {
    private static final boolean SLIM = true;
    private static final Identifier
            DEFAULT_SKIN = RipAndTear.id("textures/entity/statue.png"),
            SLIM_SKIN = RipAndTear.id("textures/entity/statue.png");

    public StatueEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new StatueEntityModel(SLIM ? ctx.getPart(StatueEntityModel.LAYER_SLIM) :
                ctx.getPart(StatueEntityModel.LAYER), SLIM), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new ArmorEntityModel(ctx.getPart(SLIM ? EntityModelLayers.PLAYER_SLIM_INNER_ARMOR : EntityModelLayers.PLAYER_INNER_ARMOR)), new ArmorEntityModel(ctx.getPart(SLIM ? EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR : EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getEquipmentRenderer()));
    }

    @Override
    public StatueEntityRenderState createRenderState() {
        return new StatueEntityRenderState();
    }

    @Override
    public Identifier getTexture(StatueEntityRenderState state) {
        return StatueEntitySkinType.values()[state.textureIndex].getIdentifier();
    }

    @Override
    public void render(StatueEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0;
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public void updateRenderState(StatueEntity entity, StatueEntityRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.slim = entity.isSlim();

        ModEntityComponents.STATUE.maybeGet(entity).ifPresent(x -> {
            state.textureIndex = x.getTextureIndex();
            state.pose = x.getForcedPose();
            state.relativeHeadYaw = x.getForcedHeadYaw();
            state.bodyYaw = x.getForcedBodyYaw();
            state.pitch = x.getForcedPitch();
            state.limbSwingAnimationProgress = x.getForcedLimbSwingAnimationProgress();
            state.limbSwingAmplitude = x.getForcedLimbSwingAmplitude();
            state.age = x.getForcedClientAge();
        });
    }

    @Override
    protected void scale(StatueEntityRenderState state, MatrixStack matrices) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }
}