package net.rose.rip_and_tear.data.providers;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.rose.rip_and_tear.common.init.ModEntityTypes;
import net.rose.rip_and_tear.common.init.ModItems;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RipAndTearLangProvider extends FabricLanguageProvider {
    public RipAndTearLangProvider(FabricDataOutput dataOutput,
                                  CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup,
                                     TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.WARPER, "Warper");
        translationBuilder.add(ModItems.THROWN_WARPER, "Thrown Warper");
        translationBuilder.add(ModItems.DUSKS_EPITATH_SCULPTURE, "Sculpture of Dusk's Epitath");

        translationBuilder.add(ModItems.RUNE_MOUTHPIECE, "Orange Rune");
        translationBuilder.add(ModItems.RUNE_BLANK, "Rune");
        translationBuilder.add(ModItems.STATUE, "Statue");

        translationBuilder.add(ModEntityTypes.WARPER, "Thrown Warper");
    }
}
