package net.rose.rip_and_tear;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.content.*;

public class RipAndTear implements ModInitializer {
    public static final String MOD_ID = "rip_and_tear";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModToolMaterials.initialize();
        ModItemGroups.initialize();
        ModEntities.initialize();
        ModComponents.initialize();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
