package dev.monogon.cue.lang.psi;

import com.intellij.psi.tree.IFileElementType;
import dev.monogon.cue.lang.CueLanguage;

public class CueFileElementType extends IFileElementType {
    public static final CueFileElementType INSTANCE = new CueFileElementType();

    public CueFileElementType() {
        super(CueLanguage.INSTANCE);
    }
}
