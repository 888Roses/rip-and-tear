package net.rose.rip_and_tear.content;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.rose.rip_and_tear.RipAndTear;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> WARPER_STING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RipAndTear.id("warper_sting"));
}
