package net.rose.rip_and_tear.common.init;

import moriyashiine.strawberrylib.api.module.SLibRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.rose.rip_and_tear.common.util.TextUtils;

public class ModItemGroups {
    public static final ItemGroup MAIN = SLibRegistries.registerItemGroup(
            FabricItemGroup.builder()
                    .displayName(TextUtils.toLiteral("Rip & Tear"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.WARPER);
                        entries.add(ModItems.SOUL_GLUTTON);
                        entries.add(ModItems.FATE_CRUSHER);
                        entries.add(ModItems.DUSKS_EPITATH_SCULPTURE);
                        entries.add(ModItems.RUNE_BLANK);
                        entries.add(ModItems.RUNE_MOUTHPIECE);
                        entries.add(ModItems.STATUE);
                    })
                    .icon(() -> new ItemStack(ModItems.RUNE_MOUTHPIECE))
                    .special()
                    .build()
    );

    public static void init() {
    }
}
