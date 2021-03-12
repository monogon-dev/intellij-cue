package dev.monogon.cue.lang.elementManipulator;

import dev.monogon.cue.lang.psi.CuePsiElementFactory;
import dev.monogon.cue.lang.psi.CueSimpleBytesLit;
import dev.monogon.cue.lang.psi.CueStringLiteral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueSimpleBytesElementManipulator extends AbstractStringLiteralElementManipulator<CueSimpleBytesLit> {
    @Override
    protected @Nullable CueSimpleBytesLit createStringLiteral(@NotNull CueStringLiteral element, String unquotedContent, int paddingSize) {
        return CuePsiElementFactory.createSimpleBytesLiteral(element.getProject(), unquotedContent, paddingSize);
    }
}
