package net.rose.rip_and_tear.common.util;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SoundUtil {
    public static void playSound(World world, Entity src, Vec3d pos,
                                 SoundEvent evt, SoundCategory category,
                                 float volume, float pitch) {
        world.playSound(src, pos.x, pos.y, pos.z, evt, category, volume, pitch);
    }
}
