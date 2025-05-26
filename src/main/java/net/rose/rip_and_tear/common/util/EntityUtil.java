package net.rose.rip_and_tear.common.util;

import net.minecraft.util.math.Vec2f;
import net.minecraft.entity.Entity;

public class EntityUtil {
    public static void setRotation(Entity entity, float yaw, float pitch) {
        entity.setYaw(yaw);
        entity.setPitch(pitch);
    }

    public static void setRotation(Entity entity, Vec2f rotation) {
        entity.setYaw(rotation.x);
        entity.setPitch(rotation.y);
    }

    public static Vec2f getRotation(Entity entity) {
        return new Vec2f(entity.getYaw(), entity.getPitch());
    }
}
