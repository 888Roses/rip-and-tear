package net.rose.rip_and_tear.content;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.RipAndTear;
import net.rose.rip_and_tear.content.warper.ThrownWarperItem;
import net.rose.rip_and_tear.content.warper.WarperItem;

import java.util.function.Function;

public class ModItems {
    public static final Item WARPER = register("warper", WarperItem::new,
            new Item.Settings()
                    .maxCount(1)
                    .sword(ModToolMaterials.CURSED, 3F, -2F)
    );
    public static final Item THROWN_WARPER = register("thrown_warper", ThrownWarperItem::new,
            new Item.Settings()
                    .maxCount(1)
    );

    public static Item register(String name) {
        return register(name, new Item.Settings());
    }

    public static Item register(String name, Item.Settings settings) {
        return register(name, Item::new, settings);
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RipAndTear.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void initialize() {
    }
}
