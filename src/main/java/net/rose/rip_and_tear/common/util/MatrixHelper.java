package net.rose.rip_and_tear.common.util;

import org.joml.Quaternionf;

public class MatrixHelper {
    public static Quaternionf eulerAnglesRad(float x, float y, float z) {
        return (new Quaternionf()).rotationXYZ(x, y, z);
    }

    public static Quaternionf eulerAngles(float x, float y, float z) {
        return eulerAnglesRad((float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z));
    }
}
