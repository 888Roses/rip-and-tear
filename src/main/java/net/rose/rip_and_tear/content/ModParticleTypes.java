package net.rose.rip_and_tear.content;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.rose.rip_and_tear.RipAndTear;

import java.util.ArrayList;
import java.util.List;

public class ModParticleTypes {
    public static final SimpleParticleType CHARTER_SKULL = register("charter_skull");
    public static final SimpleParticleType CHARTER_HIT = register("charter_hit");

    public static SimpleParticleType register(String path) {
        return register(path, FabricParticleTypes.simple());
    }

    public static <T extends ParticleType<?>> T register(String path, T particleType) {
        var id = RipAndTear.id(path);
        return Registry.register(Registries.PARTICLE_TYPE, id, particleType);
    }

    public static void initialize() {}
}
