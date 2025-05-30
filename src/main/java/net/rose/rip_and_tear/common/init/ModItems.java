package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerItem;

import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Rarity;
import net.rose.rip_and_tear.common.item.*;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item WARPER = registerItem(
            "warper", WarperItem::new,
            new Item.Settings().sword(ModToolMaterials.CURSED, 5f, -2f).rarity(Rarity.UNCOMMON)
    );

    public static final Item THROWN_WARPER = registerItem(
            "thrown_warper", ThrownWarperItem::new,
            new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
    );

    public static final Item DUSKS_EPITATH_SCULPTURE = registerItem(
            "dusks_epitath_sculpture", Item::new,
            new Item.Settings().sword(ToolMaterial.STONE, 0F, -3F)
    );

    public static final Item RUNE_BLANK = registerItem(
            "blank_rune", Item::new,
            new Item.Settings().maxCount(32)
    );

    public static final Item ORANGE_RUNE = registerItem(
            "orange_rune", RuneItem::new,
            new Item.Settings().maxCount(1)
    );

    public static final Item STATUE = registerItem(
            "statue", StatueItem::new,
            new Item.Settings().maxCount(16)
    );

    public static final Item SOUL_GLUTTON = registerItem(
            "soul_glutton", SoulGluttonItem::new,
            new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
    );

    public static final Item FATE_CRUSHER = registerItem(
            "fate_crusher", FateCrusherItem::new,
            new Item.Settings().sword(ModToolMaterials.CURSED, 9, -2.7F).rarity(Rarity.UNCOMMON)
    );

    public static void init() {
    }
}
