package net.rose.rip_and_tear.client.event.tooltips;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.rose.rip_and_tear.common.init.ModItems;
import net.rose.rip_and_tear.common.util.TextUtils;

import java.util.List;

public class StatueTooltipListenerEvent implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType,
                           List<Text> lines) {
        if (!stack.isOf(ModItems.STATUE)) return;

        var useKeyComponent = TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey);
        lines.add(Text.translatable("item.rip_and_tear.statue.desc", useKeyComponent).formatted(Formatting.DARK_GRAY));
    }
}
