package net.rose.rip_and_tear.client.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractCustomItemTooltipRegistry {
    public static final List<TooltipInfo> TOOLTIPS = new ArrayList<>();

    public static TooltipInfo of(Item item, String key, Supplier<Object[]> values) {
        return of(Registries.ITEM.getId(item), key, values);
    }

    public static TooltipInfo of(Item item, String key) {
        return of(item, key, () -> new Object[0]);
    }

    public static TooltipInfo of(Identifier itemId, String key, Supplier<Object[]> values) {
        var tooltip = new TooltipInfo(itemId, key, values);
        TOOLTIPS.add(tooltip);
        return tooltip;
    }

    public static TooltipInfo of(Identifier itemId, String key) {
        return of(itemId, key, () -> new Object[0]);
    }
}
