package dev.monogon.cue;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class Messages extends AbstractBundle {
    private static final Messages INSTANCE = new Messages();

    private Messages() {
        super("messages.cuelang");
    }

    public static String get(@NotNull @PropertyKey(resourceBundle = "messages.cuelang") String key) {
        return INSTANCE.getMessage(key);
    }

    public static String get(@NotNull @PropertyKey(resourceBundle = "messages.cuelang") String key, Object... args) {
        return INSTANCE.getMessage(key, args);
    }
}
