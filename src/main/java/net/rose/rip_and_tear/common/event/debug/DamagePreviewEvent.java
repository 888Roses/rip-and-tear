package net.rose.rip_and_tear.common.event.debug;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DamagePreviewEvent {
    public static void registerEvent() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity player) {
                player.sendMessage(Text.literal(String.valueOf(damageTaken) + " ❤").formatted(Formatting.RED), true);
            }
        });
    }
}
