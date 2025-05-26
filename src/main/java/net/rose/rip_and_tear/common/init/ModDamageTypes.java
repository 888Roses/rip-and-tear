package net.rose.rip_and_tear.common.init;

import net.rose.rip_and_tear.common.RipAndTear;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryKey;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> WARPER_STING = register("warper_sting");

    private static RegistryKey<DamageType> register(String path) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RipAndTear.id(path));
    }
}
