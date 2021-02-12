package dev.monogon.cue.lang;

import com.intellij.lang.Language;
import com.intellij.openapi.util.NlsSafe;
import dev.monogon.cue.Messages;
import org.jetbrains.annotations.NotNull;

public class CueLanguage extends Language {
    public static final CueLanguage INSTANCE = new CueLanguage();

    private CueLanguage() {
        super("CUE", "application/x-cuelang");
    }

    @Override
    public @NotNull @NlsSafe String getDisplayName() {
        return Messages.get("lang.displayName");
    }
}
