package net.rose.rip_and_tear.common.init;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.item.ToolMaterial;

public class ModToolMaterials {
    public static final ToolMaterial CURSED = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            4000, 9F, 2F, 14,
            ItemTags.NETHERITE_TOOL_MATERIALS
    );

    public static void initialize() {}
}
