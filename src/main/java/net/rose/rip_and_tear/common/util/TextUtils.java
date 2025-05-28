package net.rose.rip_and_tear.common.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TextUtils {
    public static String prettify(String text) {
        var builder = new StringBuilder();
        for (var i = 0; i < text.length(); i++) {
            var character = text.charAt(i);

            if (i > 0 && Character.isUpperCase(character) && Character.isLowerCase(text.charAt(i - 1))) {
                builder.append(" ");
                builder.append(character);
                continue;
            }

            builder.append(i == 0 ? Character.toUpperCase(character) : Character.toLowerCase(character));
        }

        return builder.toString().replace("_", "");
    }

    public static Text toLiteral(String text) {
        return Text.literal(text);
    }

    public static Text getKeyText(KeyBinding binding) {
        var useKeyText = Text.translatable(binding.getBoundKeyTranslationKey());
        return Text.literal(useKeyText.getString()).formatted(Formatting.GOLD);
    }
}
