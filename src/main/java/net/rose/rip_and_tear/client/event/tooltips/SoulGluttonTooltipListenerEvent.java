package net.rose.rip_and_tear.client.event.tooltips;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.rose.rip_and_tear.common.init.ModItems;

import java.util.List;

public class SoulGluttonTooltipListenerEvent implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType,
                           List<Text> lines) {
        if (!stack.isOf(ModItems.SOUL_GLUTTON)) return;

        var useKeyText = Text.translatable(MinecraftClient.getInstance().options.useKey.getBoundKeyTranslationKey());
        var useKeyComponent = Text.literal(useKeyText.getString()).formatted(Formatting.GOLD);

        lines.add(Text.literal("Hold [").formatted(Formatting.DARK_GRAY).append(useKeyComponent).append("] on any " +
                "entity to absorb their soul. Each time a part of their soul is absorbed and 1 heart is deducted from" +
                " their maximum health.").formatted(Formatting.DARK_GRAY));
    }
}
