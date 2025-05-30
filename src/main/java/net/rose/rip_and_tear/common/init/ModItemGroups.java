package net.rose.rip_and_tear.common.init;

import moriyashiine.strawberrylib.api.module.SLibRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
                        entries.add(ModItems.ORANGE_RUNE);
                        entries.add(ModItems.STATUE);
                        entries.add(ModItems.VOODOO_PUPPET);
                    })
                    .icon(() -> new ItemStack(ModItems.ORANGE_RUNE))
                    .special()
                    .build()
    );

    public static void init() {
    }
}
