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
        translationBuilder.add("item.rip_and_tear.statue.desc", "Represents a player statue with customizable pose. " +
                "[%s] + [%s] on a statue to customize limb positions as well as the statue's appearance.");

        translationBuilder.add(ModItems.SOUL_GLUTTON, "Soul Glutton");
        translationBuilder.add("item.rip_and_tear.soul_glutton.desc", "Hold [%s] on any entity to absorb their soul. " +
                "Each time a part of their soul is absorbed and 1 heart is deducted from their maximum health.");

        translationBuilder.add(ModEntityTypes.WARPER, "Thrown Warper");
        translationBuilder.add(ModItems.FATE_CRUSHER, "Fate Crusher");
        translationBuilder.add("item.rip_and_tear.fate_crusher.desc", "Use [%s] to slam the looked position. Any " +
                "entity in a %s block radius will be dealt %s AOE damage every tick for %s ticks. Bypasses the " +
                "invincibility frame.");
    }
}
