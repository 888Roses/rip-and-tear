package net.rose.rip_and_tear.common.receiver.global;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.rose.rip_and_tear.common.payload.C2S.ReloadStatueForcedStatePayload;

public class ReloadStatueForcedStatePayloadReceiver {
    public static void receive(ReloadStatueForcedStatePayload payload, ServerPlayNetworking.Context context) {
        var entity = context.player().getWorld().getEntityById(payload.entityId());
        if (entity instanceof StatueEntity statueEntity && statueEntity.isInRange(context.player(), 10)) {
            ModEntityComponents.STATUE.maybeGet(entity).ifPresent(payload::setStatueComponent);
        }
    }
}
