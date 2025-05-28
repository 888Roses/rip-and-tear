package net.rose.rip_and_tear.common.entity.mob;

import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;

public enum StatueEntitySkinType {

    STATUE("statue"),
    LUXINTRUS("statue_luxintrus"),
    ARATHAIN("statue_arathain")

    ;

    private final String name;

    StatueEntitySkinType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Identifier getIdentifier() {
        return RipAndTear.id("textures/entity/" + name + ".png");
    }
}
