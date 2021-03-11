package dev.monogon.cue.lang.elementManipulator;

import dev.monogon.cue.lang.psi.CuePsiElementFactory;
import dev.monogon.cue.lang.psi.CueSimpleStringLit;
import dev.monogon.cue.lang.psi.CueStringLiteral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueSimpleStringElementManipulator extends AbstractStringLiteralElementManipulator<CueSimpleStringLit> {
    @Override
    protected @Nullable CueSimpleStringLit createStringLiteral(@NotNull CueStringLiteral element, String unquotedContent, int paddingSize) {
        return CuePsiElementFactory.createSimpleStringLiteral(element.getProject(), unquotedContent, paddingSize);
    }
}
