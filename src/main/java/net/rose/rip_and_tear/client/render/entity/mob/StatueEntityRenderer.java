package net.rose.rip_and_tear.client.render.entity.mob;


import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;

public class StatueEntityRenderer extends BipedEntityRenderer<StatueEntity, StatueEntityRenderState, StatueEntityModel> {
    private static final boolean SLIM = true;
    private static final Identifier
            DEFAULT_SKIN = RipAndTear.id("textures/entity/statue.png"),
            SLIM_SKIN = RipAndTear.id("textures/entity/statue.png");

    public StatueEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new StatueEntityModel(SLIM ? ctx.getPart(StatueEntityModel.LAYER_SLIM) : ctx.getPart(StatueEntityModel.LAYER), SLIM), 0.5F);
    }

    @Override
    public StatueEntityRenderState createRenderState() {
        return new StatueEntityRenderState();
    }

    @Override
    public Identifier getTexture(StatueEntityRenderState state) {
        return state.slim ? SLIM_SKIN : DEFAULT_SKIN;
    }

    @Override
    public void updateRenderState(StatueEntity entity, StatueEntityRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.slim = entity.isSlim();
        state.pose = entity.forcedPose;
        state.relativeHeadYaw = entity.forcedHeadYaw;
        state.bodyYaw = entity.forcedBodyYaw;
        state.pitch = entity.forcedPitch;
        state.limbSwingAnimationProgress = entity.forcedLimbSwingAnimationProgress;
        state.limbSwingAmplitude = entity.forcedLimbSwingAmplitude;
        state.age = entity.forcedClientAge;
    }

    @Override
    protected void scale(StatueEntityRenderState state, MatrixStack matrices) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }
}