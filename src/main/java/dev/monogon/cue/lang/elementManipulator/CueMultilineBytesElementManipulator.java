package dev.monogon.cue.lang.elementManipulator;

import dev.monogon.cue.lang.psi.CueMultilineBytesLit;
import dev.monogon.cue.lang.psi.CuePsiElementFactory;
import dev.monogon.cue.lang.psi.CueStringLiteral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueMultilineBytesElementManipulator extends AbstractStringLiteralElementManipulator<CueMultilineBytesLit> {
    @Override
    protected @Nullable CueMultilineBytesLit createStringLiteral(@NotNull CueStringLiteral element,
                                                                 String unquotedContent,
                                                                 int paddingSize) {
        return CuePsiElementFactory.createMultilineBytesLiteral(element.getProject(), unquotedContent, paddingSize);
    }
}
