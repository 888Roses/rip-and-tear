package net.rose.rip_and_tear.client.event.overlays;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;

import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.rose.rip_and_tear.common.item.SoulGluttonItem;
import net.rose.rip_and_tear.common.RipAndTear;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class SoulIntegrityOverlayEvent implements HudLayerRegistrationCallback {
    private static final Identifier SOUL_INTEGRITY_BACKGROUND = RipAndTear.id("hud/soul_glouton_bar_background");
    private static final Identifier SOUL_INTEGRITY_PROGRESS = RipAndTear.id("hud/soul_glouton_bar_filled");
    private static final Identifier DEAD_HEART = RipAndTear.id("hud/hearts/dead_heart");

    @Override
    public void register(LayeredDrawerWrapper layeredDrawer) {
        layeredDrawer.attachLayerAfter(
                IdentifiedLayer.HOTBAR_AND_BARS, RipAndTear.id("dead_hearts"),
                (context, tickCounter) -> {
                    // Render hearts.
                    assert MinecraftClient.getInstance().player != null;
                    if (!MinecraftClient.getInstance().player.isCreative()) {
                        ModEntityComponents.SOUL_STATE.maybeGet(MinecraftClient.getInstance().player).ifPresent(component -> {
                            for (var i = 0; i < component.getDeadHeartCount(); i++) renderHeart(context, i);
                        });
                    }

                    // Render soul integrity bar.
                    if (SoulGluttonItem.clientLastTarget != null) {
                        ModEntityComponents.SOUL_STATE.maybeGet(SoulGluttonItem.clientLastTarget).ifPresent(targetComponent -> {
                            var clientIntegrity = targetComponent.getSoulIntegrity();
                            if (clientIntegrity < SOUL_STATE_SOUL_INTEGRITY_MAXIMUM) {
                                var x = context.getScaledWindowWidth() / 2 - SOUL_STATE_SOUL_INTEGRITY_BAR_WIDTH / 2;
                                var y = context.getScaledWindowHeight() - 29;

                                context.drawGuiTexture(
                                        RenderLayer::getGuiTextured, SOUL_INTEGRITY_BACKGROUND,
                                        x, y, SOUL_STATE_SOUL_INTEGRITY_BAR_WIDTH, SOUL_STATE_SOUL_INTEGRITY_BAR_HEIGHT
                                );

                                context.drawGuiTexture(
                                        RenderLayer::getGuiTextured, SOUL_INTEGRITY_PROGRESS,
                                        SOUL_STATE_SOUL_INTEGRITY_BAR_WIDTH, SOUL_STATE_SOUL_INTEGRITY_BAR_HEIGHT,
                                        0, 0, x, y,
                                        (int) (SOUL_STATE_SOUL_INTEGRITY_BAR_WIDTH * (1F - clientIntegrity / 100F)),
                                        SOUL_STATE_SOUL_INTEGRITY_BAR_HEIGHT
                                );
                            }
                        });
                    }
                });
    }

    private void renderHeart(DrawContext context, int index) {
        var x = context.getScaledWindowWidth() / 2 - 19 - index * 8;
        var y = context.getScaledWindowHeight() - 9 - 30;
        context.drawGuiTexture(RenderLayer::getGuiTextured, DEAD_HEART, x, y, 9, 9);
    }
}
