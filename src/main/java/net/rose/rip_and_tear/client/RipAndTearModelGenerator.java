package net.rose.rip_and_tear.client;


import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.rose.rip_and_tear.content.ModItems;

public class RipAndTearModelGenerator extends FabricModelProvider {
    public RipAndTearModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.WARPER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.THROWN_WARPER, Models.HANDHELD);
    }
}