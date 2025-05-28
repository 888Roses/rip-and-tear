package net.rose.rip_and_tear.common.init;

import net.minecraft.entity.LivingEntity;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.components.entity.SoulStateComponent;
import net.rose.rip_and_tear.common.components.entity.StatueComponent;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<StatueComponent> STATUE = ComponentRegistry.getOrCreate(RipAndTear.id("statue"), StatueComponent.class);
    public static final ComponentKey<SoulStateComponent> SOUL_STATE = ComponentRegistry.getOrCreate(RipAndTear.id("soul_state"), SoulStateComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(StatueEntity.class, STATUE, StatueComponent::new);
        registry.registerFor(LivingEntity.class, SOUL_STATE, SoulStateComponent::new);
    }
}
