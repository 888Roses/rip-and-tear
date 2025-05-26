package net.rose.rip_and_tear.data.providers;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.rose.rip_and_tear.common.init.ModDamageTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;

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
