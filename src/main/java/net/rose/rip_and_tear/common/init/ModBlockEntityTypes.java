package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockEntityType;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.rose.rip_and_tear.common.block.entity.EngravedDeepslateBlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static final BlockEntityType<EngravedDeepslateBlockEntity> ENGRAVED_DEEPSLATE = registerBlockEntityType(
            "engraved_deepslate",
            FabricBlockEntityTypeBuilder.create(EngravedDeepslateBlockEntity::new, ModBlocks.ENGRAVED_DEEPSLATE)
    );

    public static void init() {
    }
}
