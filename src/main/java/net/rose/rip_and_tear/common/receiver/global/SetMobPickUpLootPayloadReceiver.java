package net.rose.rip_and_tear.common.receiver.global;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.mob.MobEntity;
import net.rose.rip_and_tear.common.payload.C2S.SetMobPickUpLootPayload;

public class SetMobPickUpLootPayloadReceiver {
    public static void receive(SetMobPickUpLootPayload payload, ServerPlayNetworking.Context context) {
        var world = context.player().getWorld();
        var mob = world.getEntityById(payload.mobId());
        if (mob instanceof MobEntity mobEntity && mobEntity.distanceTo(context.player()) < 7F) {
            mobEntity.setCanPickUpLoot(payload.canPickUpLoot());
        }
    }
}
