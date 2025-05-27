package net.rose.rip_and_tear.common.init;

import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.common.RipAndTear;

public enum ModFont {
    RUNE("rune")

    ;

    private Identifier id;

    ModFont(String path) {
        this.id = RipAndTear.id(path);
    }

    ModFont(String namespace, String path) {
        this.id = Identifier.of(namespace, path);
    }

    public Identifier getId() {
        return id;
    }
}
