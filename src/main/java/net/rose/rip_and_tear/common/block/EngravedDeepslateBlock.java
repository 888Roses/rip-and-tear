package net.rose.rip_and_tear.common.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import net.rose.rip_and_tear.common.block.entity.EngravedDeepslateBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockPos;

public class EngravedDeepslateBlock extends BlockWithEntity {
    public static final MapCodec<BlockWithEntity> CODEC = createCodec(EngravedDeepslateBlock::new);

    public EngravedDeepslateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EngravedDeepslateBlockEntity(pos, state);
    }

    // region Pillar

    public static final EnumProperty<Direction.Axis> AXIS;

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return changeRotation(state, rotation);
    }

    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(AXIS)) {
                case X -> (BlockState) state.with(AXIS, Direction.Axis.Z);
                case Z -> (BlockState) state.with(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }

    static {
        AXIS = Properties.AXIS;
    }

    // endregion
}
