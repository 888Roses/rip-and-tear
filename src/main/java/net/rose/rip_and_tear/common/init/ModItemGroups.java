package net.rose.rip_and_tear.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItemGroups {
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
            itemGroup.add(ModItems.WARPER);
        });
    }
}
