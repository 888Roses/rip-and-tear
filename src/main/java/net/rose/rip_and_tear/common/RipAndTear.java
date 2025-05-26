package net.rose.rip_and_tear.common;

import moriyashiine.strawberrylib.api.SLib;
import net.rose.rip_and_tear.common.init.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class RipAndTear implements ModInitializer {
    public static final String MOD_ID = "rip_and_tear";

    @Override
    public void onInitialize() {
        SLib.init(MOD_ID);

        ModItems.init();
        ModToolMaterials.initialize();
        ModItemGroups.init();
        ModEntities.init();
        ModComponents.init();
        ModSoundEvents.init();
        ModParticleTypes.init();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
