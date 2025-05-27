package net.rose.rip_and_tear.client.render.block;

import net.minecraft.text.Style;
import net.minecraft.util.math.*;
import net.rose.rip_and_tear.common.block.entity.EngravedDeepslateBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.rose.rip_and_tear.common.init.ModFont;
import net.rose.rip_and_tear.common.util.MatrixHelper;
import org.joml.Vector3f;

import java.util.Random;

public class EngravedDeepslateBlockEntityRenderer implements BlockEntityRenderer<EngravedDeepslateBlockEntity> {
    private final TextRenderer textRenderer;

    public EngravedDeepslateBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.textRenderer = ctx.getTextRenderer();
    }

    @Override
    public void render(
            EngravedDeepslateBlockEntity entity,
            float tickProgress,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay,
            Vec3d cameraPos
    ) {
        renderText(Direction.UP, entity, matrices, vertexConsumers);
        renderText(Direction.DOWN, entity, matrices, vertexConsumers);
        renderText(Direction.NORTH, entity, matrices, vertexConsumers);
        renderText(Direction.SOUTH, entity, matrices, vertexConsumers);
        renderText(Direction.EAST, entity, matrices, vertexConsumers);
        renderText(Direction.WEST, entity, matrices, vertexConsumers);
    }

    private void rotateMatricesByDirection(Direction direction, MatrixStack matrices) {

    }

    private void renderText(
            Direction direction,
            EngravedDeepslateBlockEntity entity,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers
    ) {
        matrices.push();

        var pivot = new Vector3f(0.5f, 0.5f, 0.5f);
        Direction.Axis.X.getNegativeDirection().getRotationQuaternion();
        var rot = switch (direction) {
            case UP -> MatrixHelper.eulerAngles(-90, 0, 0);
            case DOWN -> MatrixHelper.eulerAngles(90, 0, 0);
            case NORTH -> MatrixHelper.eulerAngles(0, 180, 0);
            case SOUTH -> MatrixHelper.eulerAngles(0, 0, 0);
            case EAST -> MatrixHelper.eulerAngles(0, 90, 0);
            case WEST -> MatrixHelper.eulerAngles(0, -90, 0);
        };

        matrices.multiply(rot, pivot.x, pivot.y, pivot.z);

        final float TEXT_SIZE = 0.0235F;
        matrices.scale(TEXT_SIZE, -TEXT_SIZE, TEXT_SIZE);
        matrices.translate(0, -25, 43);

        var font = ModFont.RUNE.getId();
        var text = Text.literal("");
        var colours = new int[]{0xfdf55f, 0xfdf55f, 0xe9b115, 0xdc9613};

        for (var i = 0; i < entity.line.length(); i++) {
            var c = entity.line.charAt(i);
            var random = new Random(entity.getPos().hashCode() + i * 7844L);
            var colour = colours[(int) (random.nextDouble() * colours.length)];
            text.append(Text.literal(String.valueOf(c)).setStyle(Style.EMPTY.withColor(colour).withFont(font)));
        }

        textRenderer.draw(
                text,
                //            AQUA Hex
                1, 1, 5636095, false,
                matrices.peek().getPositionMatrix(), vertexConsumers,
                TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 150
        );

        matrices.pop();
    }
}
