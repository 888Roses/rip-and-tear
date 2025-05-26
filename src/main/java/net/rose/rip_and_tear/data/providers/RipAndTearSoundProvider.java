package net.rose.rip_and_tear.data.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.rose.rip_and_tear.common.init.ModSoundEvents;
import net.rose.rip_and_tear.common.RipAndTear;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.data.DataOutput;

import java.util.concurrent.CompletableFuture;

public class RipAndTearSoundProvider extends FabricSoundsProvider {
    public RipAndTearSoundProvider(DataOutput output,
                                   CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, SoundExporter soundExporter) {
        soundExporter.add(
                ModSoundEvents.WARPER_WARP,
                SoundTypeBuilder.of(ModSoundEvents.WARPER_WARP)
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(RipAndTear.id("item/warper/warp")))
                        .subtitle("subtitles.rip_and_tear.item.warper.warp")
        );
    }

    @Override
    public String getName() {
        return "Sound Definitions";
    }
}
