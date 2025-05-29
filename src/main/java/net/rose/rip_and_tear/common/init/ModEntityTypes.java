package net.rose.rip_and_tear.common.init;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

import net.rose.rip_and_tear.common.entity.effect.FateCrusherEntity;
import net.rose.rip_and_tear.common.entity.projectile.WarperProjectileEntity;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;


public class ModEntityTypes {
    public static final EntityType<WarperProjectileEntity> WARPER = registerEntityType(
            "warper",
            EntityType.Builder.create(WarperProjectileEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.25F, 0.25F)
                    .trackingTickInterval(10)
                    .makeFireImmune()
    );

    public static final EntityType<StatueEntity> STATUE = registerEntityType(
            "statue",
            EntityType.Builder.create(StatueEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 1.975F)
                    .eyeHeight(1.7775F)
                    .maxTrackingRange(10),
            StatueEntity.createMobAttributes()
    );

    public static final EntityType<FateCrusherEntity> FATE_CRUSHER = registerEntityType(
            "fate_crusher",
            EntityType.Builder.create(FateCrusherEntity::new, SpawnGroup.MISC)
                    .dimensions(1,2)
                    .eyeHeight(2)
                    .makeFireImmune()
    );

    public static void init() {
    }
}
