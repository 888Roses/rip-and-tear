package net.rose.rip_and_tear.client.item;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;

import net.rose.rip_and_tear.common.init.ModConfiguration;
import net.rose.rip_and_tear.common.util.TextUtils;
import net.rose.rip_and_tear.common.init.ModItems;
import net.minecraft.client.MinecraftClient;

public class CustomItemTooltipRegistry extends AbstractCustomItemTooltipRegistry {
    public static final TooltipInfo SOUL_GLUTTON = of(
            ModItems.SOUL_GLUTTON, "item.rip_and_tear.soul_glutton.desc",
            () -> new Object[]{TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey)}
    );

    public static final TooltipInfo STATUE = of(
            ModItems.STATUE, "item.rip_and_tear.statue.desc",
            () -> new Object[]{
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.sneakKey),
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey),
            }
    );

    public static final TooltipInfo FATE_CRUSHER = of(
            ModItems.FATE_CRUSHER, "item.rip_and_tear.fate_crusher.desc",
            () -> new Object[]{
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey),
                    FATE_CRUSHER_ENTITY_AOD_BOX_SIZE + 1,
                    FATE_CRUSHER_ENTITY_DAMAGE_PER_TICK,
                    FATE_CRUSHER_ENTITY_LIFETIME - FATE_CRUSHER_ENTITY_DAMAGE_START_AGE
            }
    );

    public static final TooltipInfo WARPER = of(
            ModItems.WARPER, "item.rip_and_tear.warper.desc",
            () -> new Object[]{
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey),
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey),
                    THROWN_WARPER_RECALL_DAMAGE,
                    THROWN_WARPER_RECALL_DAMAGE_RADIUS,
            }
    );

    public static final TooltipInfo THROWN_WARPER = of(
            ModItems.THROWN_WARPER, "item.rip_and_tear.thrown_warper.desc",
            () -> new Object[]{
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey),
                    THROWN_WARPER_RECALL_DAMAGE,
                    THROWN_WARPER_RECALL_DAMAGE_RADIUS,
            }
    );

    public static final TooltipInfo ORANGE_RUNE = of(
            ModItems.ORANGE_RUNE, "item.rip_and_tear.orange_rune.desc",
            () -> new Object[]{
                    TextUtils.getKeyText(MinecraftClient.getInstance().options.useKey),
            }
    );

    public static final TooltipInfo VOODOO_PUPPET = of(ModItems.VOODOO_PUPPET, "block.rip_and_tear.voodoo_puppet.desc");

    public static void init() {}
}
