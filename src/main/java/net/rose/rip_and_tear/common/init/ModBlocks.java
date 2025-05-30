package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.rose.rip_and_tear.common.block.EngravedDeepslateBlock;
import net.rose.rip_and_tear.common.block.VoodooPuppetBlock;

public class ModBlocks {
    public static final Block ENGRAVED_DEEPSLATE = registerBlock(
            "engraved_deepslate", EngravedDeepslateBlock::new,
            AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
    );

    public static final Block VOODOO_PUPPET = registerBlock(
            "voodoo_puppet", VoodooPuppetBlock::new,
            AbstractBlock.Settings.create()
                    .blockVision(ModBlocks::never)
                    .allowsSpawning(ModBlocks::never)
                    .breakInstantly()
                    .dropsNothing()
                    .mapColor(MapColor.BROWN)
                    .nonOpaque()
                    .noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .noBlockBreakParticles()
    );

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public static void init() {}
}
