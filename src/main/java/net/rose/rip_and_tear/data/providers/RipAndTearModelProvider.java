package net.rose.rip_and_tear.data.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.rose.rip_and_tear.common.init.ModItems;
import net.minecraft.client.data.Models;

public class RipAndTearModelProvider extends FabricModelProvider {
    public RipAndTearModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.WARPER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.THROWN_WARPER, Models.HANDHELD);

        itemModelGenerator.register(ModItems.RUNE_BLANK, Models.GENERATED);
        itemModelGenerator.register(ModItems.RUNE_MOUTHPIECE, Models.GENERATED);
    }
}