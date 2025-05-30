package net.rose.rip_and_tear.data.providers;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.rose.rip_and_tear.common.init.ModBlocks;
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

        translationBuilder.add(ModItems.ORANGE_RUNE, "Orange Rune");
        translationBuilder.add("item.rip_and_tear.orange_rune.desc", "Pressing [%s] on a deepslate block engraves " +
                "orange runes in it.");

        translationBuilder.add(ModItems.RUNE_BLANK, "Rune");

        translationBuilder.add(ModItems.STATUE, "Statue");
        translationBuilder.add("item.rip_and_tear.statue.desc", "Represents a player statue with customizable pose. " +
                "Holding [%s] and pressing [%s] on a statue allows you to customize limb position and appearance of " +
                "the statue.");

        translationBuilder.add(ModItems.SOUL_GLUTTON, "Soul Glutton");
        translationBuilder.add("item.rip_and_tear.soul_glutton.desc", "Holding [%s] on an entity absorbs their soul. " +
                "Absorbing the soul of an entity reduces their max health by 1 heart.");

        translationBuilder.add(ModEntityTypes.WARPER, "Thrown Warper");
        translationBuilder.add(ModItems.FATE_CRUSHER, "Fate Crusher");
        translationBuilder.add("item.rip_and_tear.fate_crusher.desc", "Pressing [%s] slams the looked at position. " +
                "Any entity in a %s block radius will be dealt %s AOE damage every tick for %s ticks. Bypasses the " +
                "invincibility frame.");

        translationBuilder.add("block.rip_and_tear.voodoo_puppet", "Voodoo Puppet");
        translationBuilder.add("block.rip_and_tear.voodoo_puppet.desc", "Placing it in the correct altar creates a connection between merchants and you, allowing the trade of special items in exchange of sacrifices.");
    }
}
