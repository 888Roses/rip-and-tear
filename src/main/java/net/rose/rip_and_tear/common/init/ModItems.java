package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerItem;
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

    public static void init() {
    }
}
