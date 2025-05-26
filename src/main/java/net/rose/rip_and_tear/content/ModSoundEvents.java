package net.rose.rip_and_tear.content;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.rose.rip_and_tear.RipAndTear;

public class ModSoundEvents {
    public static final SoundEvent WARPER_WARP = register("item.warper.warp");

    public static SoundEvent register(String name) {
        var id = RipAndTear.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void initialize() {}
}
