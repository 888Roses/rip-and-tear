package net.rose.rip_and_tear.common.entity.mob;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;

public enum StatueEntitySkinType {
    STATUE("statue", Text.literal("Statue")),
    LUXINTRUS("statue_luxintrus", Text.literal("Luxintrus").formatted(Formatting.GOLD)),
    ARATHAIN("statue_arathain", Text.literal("Arathain").formatted(Formatting.GOLD));

    private final String path;
    private final Text name;

    StatueEntitySkinType(String path, Text name) {
        this.path = path;
        this.name = name;
    }

    public Text getName() {
        return name;
    }

    public Identifier getIdentifier() {
        return RipAndTear.id("textures/entity/" + path + ".png");
    }
}
