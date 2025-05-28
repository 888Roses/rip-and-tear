package net.rose.rip_and_tear.common.payload.C2S;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;

public record SetMobPickUpLootPayload(int mobId, boolean canPickUpLoot) implements CustomPayload {
    public static final Identifier IDENTIFIER = RipAndTear.id("set_mob_pick_up_loot");
    public static final CustomPayload.Id<SetMobPickUpLootPayload> ID = new Id<>(IDENTIFIER);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, SetMobPickUpLootPayload> CODEC = PacketCodec.of(
            SetMobPickUpLootPayload::write,
            SetMobPickUpLootPayload::load
    );

    private static SetMobPickUpLootPayload load(RegistryByteBuf buf) {
        return new SetMobPickUpLootPayload(buf.readInt(), buf.readBoolean());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeInt(mobId);
        buf.writeBoolean(canPickUpLoot);
    }

    public static SetMobPickUpLootPayload fromMobEntity(MobEntity mobEntity) {
        return new SetMobPickUpLootPayload(mobEntity.getId(), mobEntity.canPickUpLoot());
    }
}
