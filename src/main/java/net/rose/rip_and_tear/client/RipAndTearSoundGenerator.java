package net.rose.rip_and_tear.client;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.rose.rip_and_tear.RipAndTear;
import net.rose.rip_and_tear.content.ModSoundEvents;

import java.util.concurrent.CompletableFuture;

public class RipAndTearSoundGenerator extends FabricSoundsProvider {
    public RipAndTearSoundGenerator(DataOutput output,
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
