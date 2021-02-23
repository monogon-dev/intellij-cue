package dev.monogon.cue;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

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

    public static Supplier<String> lazyMessage(@NotNull @PropertyKey(resourceBundle = "messages.cuelang") String key) {
        return () -> get(key);
    }

    public static Supplier<String> lazyMessage(@NotNull @PropertyKey(resourceBundle = "messages.cuelang") String key, Object... args) {
        return () -> get(key, args);
    }
}
