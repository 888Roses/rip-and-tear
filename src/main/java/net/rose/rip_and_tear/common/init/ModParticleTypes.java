package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerParticleType;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;

public class ModParticleTypes {
    public static final SimpleParticleType CHARTER_SKULL = registerParticleType("charter_skull", FabricParticleTypes.simple());
    public static final SimpleParticleType CHARTER_HIT = registerParticleType("charter_hit", FabricParticleTypes.simple());

    public static void init() {}
}
