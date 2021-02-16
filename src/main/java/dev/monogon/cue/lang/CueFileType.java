package dev.monogon.cue.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import dev.monogon.cue.Icons;
import dev.monogon.cue.Messages;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CueFileType extends LanguageFileType {
    public static final CueFileType INSTANCE = new CueFileType();

    protected CueFileType() {
        super(CueLanguage.INSTANCE);
    }

    @Override
    public @NonNls
    @NotNull String getName() {
        return "CUE";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return Messages.get("fileType.description");
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "cue";
    }

    @Override
    public @Nullable Icon getIcon() {
        return Icons.CueLogo;
    }
}
