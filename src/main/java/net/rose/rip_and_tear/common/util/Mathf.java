package net.rose.rip_and_tear.common.util;

import net.minecraft.util.math.Vec3d;
import java.util.function.Consumer;
import net.minecraft.world.World;

public class Mathf {
    public static float remap(float src, float srcA, float srcB, float dstA, float dstB) {
        return srcB + (src - srcA) * (dstB - dstA) / (dstA - srcA);
    }

    public static float remap01(float src, float dstA, float dstB) {
        return remap(src, 0, 1, dstA, dstB);
    }

    public static void inLine(
            Vec3d a, Vec3d b,
            float interval, float stepSize,
            Consumer<Vec3d> onEachStep
    ) {
        var dist = a.distanceTo(b);
        var dir = b.subtract(a).normalize();

        for (var i = 0F; i < dist / interval; i += stepSize) {
            var stepPos = a.add(dir.multiply(i));
            onEachStep.accept(stepPos);
        }
    }

    public static float random(World world, float min, float max) {
        return Mathf.remap01(world.getRandom().nextFloat(), min, max);
    }
}
