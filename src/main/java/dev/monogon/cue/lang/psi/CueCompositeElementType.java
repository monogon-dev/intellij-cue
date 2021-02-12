package dev.monogon.cue.lang.psi;

import com.intellij.psi.tree.IElementType;
import dev.monogon.cue.lang.CueLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CueCompositeElementType extends IElementType {
    public CueCompositeElementType(@NonNls @NotNull String debugName) {
        super(debugName, CueLanguage.INSTANCE);
    }
}
