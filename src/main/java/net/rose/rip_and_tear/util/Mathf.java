package net.rose.rip_and_tear.util;

public class Mathf {
    public static float remap(float src, float srcA, float srcB, float dstA, float dstB) {
        // https://stackoverflow.com/questions/3451553/value-remapping:
        //     srcB + (src - srcA) * (dstB - srcB) / (dstA - srcA)
        return srcB + (src - srcA) * (dstB - dstA) / (dstA - srcA);
    }

    public static float remap01(float src, float dstA, float dstB) {
        return remap(src, 0, 1, dstA, dstB);
    }
}
