package net.rose.rip_and_tear.data;

import net.rose.rip_and_tear.data.providers.RipAndTearDamageTypeProvider;
import net.rose.rip_and_tear.data.providers.RipAndTearModelProvider;
import net.rose.rip_and_tear.data.providers.RipAndTearSoundProvider;
import net.rose.rip_and_tear.data.providers.RipAndTearLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RipAndTearDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(RipAndTearModelProvider::new);
        pack.addProvider(RipAndTearDamageTypeProvider::new);
        pack.addProvider(RipAndTearLangProvider::new);
        pack.addProvider(RipAndTearSoundProvider::new);
    }
}
