package net.rose.rip_and_tear.client.item;

import net.minecraft.util.Identifier;
import java.util.function.Supplier;

public record TooltipInfo(Identifier itemId, String key, Supplier<Object[]> values) {
}