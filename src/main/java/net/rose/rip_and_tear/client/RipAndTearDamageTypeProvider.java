package net.rose.rip_and_tear.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.rose.rip_and_tear.content.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

public class RipAndTearDamageTypeProvider extends FabricTagProvider<DamageType> {
    public RipAndTearDamageTypeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .addOptional(ModDamageTypes.WARPER_STING);
    }
}
