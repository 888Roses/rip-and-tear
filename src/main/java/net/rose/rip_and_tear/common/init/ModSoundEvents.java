package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerSoundEvent;
import net.minecraft.sound.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent WARPER_WARP = registerSoundEvent("item.warper.warp");
    public static final SoundEvent WARPER_SWING = registerSoundEvent("item.warper.swing");

    public static void init() {}
}
