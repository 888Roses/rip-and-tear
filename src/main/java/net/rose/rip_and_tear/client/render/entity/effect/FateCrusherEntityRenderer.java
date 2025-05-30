package net.rose.rip_and_tear.client.render.entity.effect;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.entity.effect.FateCrusherEntity;
import net.rose.rip_and_tear.common.util.MatrixHelper;

public class FateCrusherEntityRenderer extends EntityRenderer<FateCrusherEntity, FateCrusherEntityRenderState> {
    public static final Identifier TEXTURE = RipAndTear.id("textures/entity/fate_crusher.png");
    protected FateCrusherEntityModel model;

    public FateCrusherEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        model = new FateCrusherEntityModel(context.getPart(FateCrusherEntityModel.MAIN));
    }

    @Override
    public FateCrusherEntityRenderState createRenderState() {
        return new FateCrusherEntityRenderState();
    }

    @Override
    public void updateRenderState(FateCrusherEntity entity, FateCrusherEntityRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.yaw = entity.getYaw();
    }

    @Override
    public void render(FateCrusherEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider provider, int light) {
        matrixStack.push();
        matrixStack.multiply(MatrixHelper.eulerAngles(0, -state.yaw, 0), 0, 0, 0.25F);
        matrixStack.scale(2,2,2);
        matrixStack.translate(0,0,-2.8);
        matrixStack.multiply(MatrixHelper.eulerAngles(Math.min(270, 130 + (float) Math.pow(state.age * 1.1F, 3)), 0, 0), 0, 0.9F, 0.25F);

        var renderLayer = this.model.getLayer(TEXTURE);
        if (renderLayer != null) {
            var consumer = provider.getBuffer(renderLayer);
            var overlay = getOverlay(state, 0.0F);
            this.model.render(matrixStack, consumer, light, overlay);
        }

        matrixStack.pop();
        super.render(state, matrixStack, provider, light);
    }

    public static int getOverlay(FateCrusherEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(false));
    }
}
