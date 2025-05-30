package net.rose.rip_and_tear.client.event.tooltips;

import net.rose.rip_and_tear.client.item.AbstractCustomItemTooltipRegistry;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Formatting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.List;

public class GenericTooltipListenerEvent implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType,
                           List<Text> lines) {
        for (var itemInfo : AbstractCustomItemTooltipRegistry.TOOLTIPS) {
            if (Registries.ITEM.getId(stack.getItem()) != itemInfo.itemId()) continue;
            lines.add(
                    1, Text.literal("- ").formatted(Formatting.GRAY).append(
                            Text.translatable(
                                    itemInfo.key(),
                                    itemInfo.values().get()
                            ).formatted(Formatting.DARK_GRAY)
                    )
            );
        }
    }
}
