package net.rose.rip_and_tear.common.payload.C2S;

import net.minecraft.entity.EntityPose;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.components.entity.StatueComponent;

import java.util.UUID;

public record ReloadStatueForcedStatePayload(
        int entityId,
        int textureIndex,
        EntityPose forcedPose,
        float forcedHeadYaw,
        float forcedBodyYaw,
        float forcedPitch,
        float forcedLimbSwingAnimationProgress,
        float forcedLimbSwingAmplitude,
        int forcedClientAge
) implements CustomPayload {
    public static final Identifier IDENTIFIER = RipAndTear.id("reload_statue_forced_state");
    public static final CustomPayload.Id<ReloadStatueForcedStatePayload> ID = new Id<>(IDENTIFIER);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    // region Save & Load

    public static final PacketCodec<RegistryByteBuf, ReloadStatueForcedStatePayload> CODEC = PacketCodec.of(
            ReloadStatueForcedStatePayload::write,
            ReloadStatueForcedStatePayload::load
    );

    private void write(RegistryByteBuf registryByteBuf) {
        registryByteBuf.writeInt(entityId);
        registryByteBuf.writeInt(textureIndex);
        registryByteBuf.writeInt(forcedPose.getIndex());
        registryByteBuf.writeFloat(forcedHeadYaw);
        registryByteBuf.writeFloat(forcedBodyYaw);
        registryByteBuf.writeFloat(forcedPitch);
        registryByteBuf.writeFloat(forcedLimbSwingAnimationProgress);
        registryByteBuf.writeFloat(forcedLimbSwingAmplitude);
        registryByteBuf.writeInt(forcedClientAge);
    }

    private static ReloadStatueForcedStatePayload load(RegistryByteBuf buf) {
        return new ReloadStatueForcedStatePayload(
                buf.readInt(),
                buf.readInt(),
                EntityPose.INDEX_TO_VALUE.apply(buf.readInt()),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readInt()
        );
    }

    // endregion

    public void setStatueComponent(StatueComponent component) {
        component.setForcedPose(forcedPose);
        component.setForcedHeadYaw(forcedHeadYaw);
        component.setForcedBodyYaw(forcedBodyYaw);
        component.setForcedPitch(forcedPitch);
        component.setForcedLimbSwingAnimationProgress(forcedLimbSwingAnimationProgress);
        component.setForcedLimbSwingAmplitude(forcedLimbSwingAmplitude);
        component.setForcedClientAge(forcedClientAge);
        component.setTextureIndex(textureIndex);
    }
}
