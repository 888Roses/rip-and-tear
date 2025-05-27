package net.rose.rip_and_tear.common.item;

import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.rose.rip_and_tear.common.init.ModBlocks;
import net.rose.rip_and_tear.common.util.Mathf;
import net.rose.rip_and_tear.common.util.SoundUtil;

public class RuneItem extends Item {
    public RuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var pos = context.getBlockPos();
        var world = context.getWorld();

        var state = world.getBlockState(pos);
        if (state.isOf(Blocks.DEEPSLATE)) {
            var axis = state.get(PillarBlock.AXIS);
            world.setBlockState(pos, ModBlocks.ENGRAVED_DEEPSLATE.getDefaultState().with(PillarBlock.AXIS, axis));

            var player = context.getPlayer();
            if (player != null) {
                SoundUtil.playSound(
                        world, null, player.getPos(),
                        SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundCategory(),
                        1F, Mathf.random(world, 0.8F, 1.2F)
                );

                SoundUtil.playSound(
                        world, null, player.getPos(),
                        SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, player.getSoundCategory(),
                        1F, Mathf.random(world, 0.8F, 1.2F)
                );
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
