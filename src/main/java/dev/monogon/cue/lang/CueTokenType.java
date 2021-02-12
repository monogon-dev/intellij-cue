package dev.monogon.cue.lang;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CueTokenType extends IElementType {
    public CueTokenType(@NonNls @NotNull String debugName) {
        super(debugName, CueLanguage.INSTANCE);
    }
}
