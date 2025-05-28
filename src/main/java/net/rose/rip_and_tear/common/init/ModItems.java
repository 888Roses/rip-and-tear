package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockItem;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerItem;

import net.minecraft.item.ToolMaterial;
import net.rose.rip_and_tear.common.item.RuneItem;
import net.rose.rip_and_tear.common.item.StatueItem;
import net.rose.rip_and_tear.common.item.ThrownWarperItem;
import net.rose.rip_and_tear.common.item.WarperItem;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item WARPER = registerItem(
            "warper", WarperItem::new,
            new Item.Settings().sword(ModToolMaterials.CURSED, 3f, -2f)
    );

    public static final Item THROWN_WARPER = registerItem(
            "thrown_warper", ThrownWarperItem::new,
            new Item.Settings().maxCount(1)
    );

    public static final Item DUSKS_EPITATH_SCULPTURE = registerItem(
            "dusks_epitath_sculpture", Item::new,
            new Item.Settings().sword(ToolMaterial.STONE, 0F, -3F)
    );

    public static final Item RUNE_BLANK = registerItem(
            "blank_rune", Item::new,
            new Item.Settings().maxCount(32)
    );

    public static final Item RUNE_MOUTHPIECE = registerItem(
            "orange_rune", RuneItem::new,
            new Item.Settings().maxCount(1)
    );

    public static final Item STATUE = registerItem(
            "statue", StatueItem::new,
            new Item.Settings().maxCount(16)
    );

    public static void init() {
    }
}
