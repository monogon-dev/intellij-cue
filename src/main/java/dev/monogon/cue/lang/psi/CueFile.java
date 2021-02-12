package dev.monogon.cue.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import dev.monogon.cue.lang.CueFileType;
import dev.monogon.cue.lang.CueLanguage;
import org.jetbrains.annotations.NotNull;

public class CueFile extends PsiFileBase {
    public CueFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CueLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return CueFileType.INSTANCE;
    }
}
