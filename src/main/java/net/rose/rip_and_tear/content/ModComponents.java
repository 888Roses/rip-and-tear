package net.rose.rip_and_tear.content;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Uuids;
import net.rose.rip_and_tear.RipAndTear;

import java.util.UUID;

public class ModComponents {
    public static final ComponentType<UUID> THROWN_WARPER_UUID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            RipAndTear.id("thrown_warper_uuid"),
            ComponentType.<UUID>builder().codec(Uuids.CODEC).build()
    );

    public static void initialize() {}
}
