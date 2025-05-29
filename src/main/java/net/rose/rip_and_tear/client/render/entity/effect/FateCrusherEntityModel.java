package net.rose.rip_and_tear.client.render.entity.effect;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.rose.rip_and_tear.common.RipAndTear;

public class FateCrusherEntityModel extends EntityModel<FateCrusherEntityRenderState> {
    public static final EntityModelLayer MAIN = new EntityModelLayer(RipAndTear.id("fate_crusher"), "main");
    public final ModelPart main;

    public FateCrusherEntityModel(ModelPart root) {
        super(root);
        this.main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        var modelData = new ModelData();
        modelData.getRoot().addChild(
                "bb_main",
                ModelPartBuilder.create()
                        .uv(0, 48)
                        .cuboid(-4.0F, -48.0F, -4.0F, 8.0F, 48.0F, 8.0F, new Dilation(0.0F))
                        .uv(0, 0)
                        .cuboid(-8.0F, -60.0F, -16.0F, 16.0F, 16.0F, 32.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 24.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(FateCrusherEntityRenderState state) {
        super.setAngles(state);
    }
}
