package net.rose.rip_and_tear.client.render.entity.mob;


import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.rose.rip_and_tear.common.entity.mob.StatueEntitySkinType;
import net.rose.rip_and_tear.common.init.ModEntityComponents;

public class StatueEntityRenderer extends BipedEntityRenderer<StatueEntity, StatueEntityRenderState,
        StatueEntityModel> {
    private static final boolean SLIM = true;
    private static final Identifier
            DEFAULT_SKIN = RipAndTear.id("textures/entity/statue.png"),
            SLIM_SKIN = RipAndTear.id("textures/entity/statue.png");

    public StatueEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new StatueEntityModel(SLIM ? ctx.getPart(StatueEntityModel.LAYER_SLIM) :
                ctx.getPart(StatueEntityModel.LAYER), SLIM), 0.5F);
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