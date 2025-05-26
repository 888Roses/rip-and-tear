package net.rose.rip_and_tear.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.rose.rip_and_tear.content.ModItems;

import java.util.concurrent.CompletableFuture;

public class RipAndTearLangGenerator extends FabricLanguageProvider {
    protected RipAndTearLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.WARPER, "Warper");
        translationBuilder.add(ModItems.THROWN_WARPER, "Warper (Thrown)");
    }
}
