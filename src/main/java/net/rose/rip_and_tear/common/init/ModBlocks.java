package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;

import net.rose.rip_and_tear.common.block.EngravedDeepslateBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;

public class ModBlocks {
    public static final Block ENGRAVED_DEEPSLATE = registerBlock(
            "engraved_deepslate", EngravedDeepslateBlock::new,
            AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
    );

    public static void init() {}
}
