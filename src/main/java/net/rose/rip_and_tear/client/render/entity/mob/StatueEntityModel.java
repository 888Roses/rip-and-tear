package net.rose.rip_and_tear.client.render.entity.mob;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.rose.rip_and_tear.common.RipAndTear;

public class StatueEntityModel extends BipedEntityModel<StatueEntityRenderState> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(RipAndTear.id("frozen_player"), "main");
    public static final EntityModelLayer LAYER_SLIM = new EntityModelLayer(RipAndTear.id("frozen_player_slim"), "main");

    private final boolean slim;

    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;

    public StatueEntityModel(ModelPart root, boolean slim) {
        super(root);
        this.slim = slim;

        this.leftSleeve = this.leftArm.getChild("left_sleeve");
        this.rightSleeve = this.rightArm.getChild("right_sleeve");
        this.leftPants = this.leftLeg.getChild("left_pants");
        this.rightPants = this.rightLeg.getChild("right_pants");
        this.jacket = this.body.getChild(EntityModelPartNames.JACKET);
    }

    public static TexturedModelData getTexturedModelData(boolean slim) {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0);
        ModelPartData root = modelData.getRoot();
        if (slim) {
            root.addChild(
                    EntityModelPartNames.LEFT_ARM,
                    ModelPartBuilder.create().uv(32, 48).cuboid(-1, -2, -2, 3, 12, 4, Dilation.NONE),
                    ModelTransform.origin(5, 2, 0)
            );
            root.addChild(
                    EntityModelPartNames.RIGHT_ARM,
                    ModelPartBuilder.create().uv(40, 16).cuboid(-2, -2, -2, 3, 12, 4, Dilation.NONE),
                    ModelTransform.origin(-5, 2, 0)
            );

            root.getChild(EntityModelPartNames.LEFT_ARM).addChild(
                    "left_sleeve",
                    ModelPartBuilder.create().uv(48, 48).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                    ModelTransform.NONE
            );
            root.getChild(EntityModelPartNames.RIGHT_ARM).addChild(
                    "right_sleeve",
                    ModelPartBuilder.create().uv(40, 32).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                    ModelTransform.NONE
            );
        } else {
            root.getChild(EntityModelPartNames.LEFT_ARM).addChild(
                    "left_sleeve",
                    ModelPartBuilder.create().uv(48, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                    ModelTransform.NONE
            );
            root.getChild(EntityModelPartNames.RIGHT_ARM).addChild(
                    "right_sleeve",
                    ModelPartBuilder.create().uv(40, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                    ModelTransform.NONE
            );
        }

        root.getChild(EntityModelPartNames.LEFT_LEG).addChild(
                "left_pants",
                ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                ModelTransform.NONE
        );
        root.getChild(EntityModelPartNames.RIGHT_LEG).addChild(
                "right_pants",
                ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                ModelTransform.NONE
        );

        root.getChild(EntityModelPartNames.BODY).addChild(
                EntityModelPartNames.JACKET,
                ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.25F)),
                ModelTransform.NONE
        );

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        getRootPart().applyTransform(matrices);
        ModelPart armPart = getArm(arm);
        if (slim) {
            float origin = 0.5F * (arm == Arm.RIGHT ? 1 : -1);
            armPart.originX += origin;
            armPart.applyTransform(matrices);
            armPart.originX -= origin;
        } else {
            armPart.applyTransform(matrices);
        }
    }
}