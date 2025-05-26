package net.rose.rip_and_tear.content;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.RipAndTear;
import net.rose.rip_and_tear.content.warper.WarperEntity;

public class ModEntities {
    public static final EntityType<WarperEntity> WARPER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(RipAndTear.MOD_ID, "warper"),
            EntityType.Builder.create(WarperEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.25F, 0.25F)
                    .trackingTickInterval(10)
                    .makeFireImmune()
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(RipAndTear.MOD_ID, "warper")))
    );

    public static void initialize() {}
}
