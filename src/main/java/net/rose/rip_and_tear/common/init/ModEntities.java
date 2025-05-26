package net.rose.rip_and_tear.common.init;

import net.rose.rip_and_tear.common.entity.projectile.WarperProjectileEntity;
import moriyashiine.strawberrylib.api.module.SLibRegistries;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class ModEntities {
    public static final EntityType<WarperProjectileEntity> WARPER = SLibRegistries.registerEntityType(
            "warper",
            EntityType.Builder.create(WarperProjectileEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.25F, 0.25F)
                    .trackingTickInterval(10)
                    .makeFireImmune()
    );

    public static void init() {}
}
