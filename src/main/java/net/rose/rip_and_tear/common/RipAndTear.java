/*
 * TODO
 *  - Soul candles: mixin that makes candles have blue flames when placed on deepslate related blocks.
 */

package net.rose.rip_and_tear.common;

import moriyashiine.strawberrylib.api.SLib;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.rose.rip_and_tear.common.payload.C2S.ReloadStatueForcedStatePayload;
import net.rose.rip_and_tear.common.event.debug.DamagePreviewEvent;
import net.rose.rip_and_tear.common.init.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.receiver.global.ReloadStatueForcedStatePayloadReceiver;

public class RipAndTear implements ModInitializer {
    public static final String MOD_ID = "rip_and_tear";

    @Override
    public void onInitialize() {
        SLib.init(MOD_ID);

        ModItems.init();
        ModBlocks.init();
        ModBlockEntityTypes.init();
        ModToolMaterials.initialize();
        ModItemGroups.init();
        ModEntityTypes.init();
        ModComponents.init();
        ModSoundEvents.init();
        ModParticleTypes.init();

        initNetworking();

        DamagePreviewEvent.registerEvent();
    }

    public static void initNetworking() {
        PayloadTypeRegistry.playC2S().register(ReloadStatueForcedStatePayload.ID, ReloadStatueForcedStatePayload.CODEC);

        // Receivers
        ServerPlayNetworking.registerGlobalReceiver(ReloadStatueForcedStatePayload.ID, ReloadStatueForcedStatePayloadReceiver::receive);
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
