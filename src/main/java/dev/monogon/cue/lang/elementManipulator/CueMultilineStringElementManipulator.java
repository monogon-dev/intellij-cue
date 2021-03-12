package dev.monogon.cue.lang.elementManipulator;

import dev.monogon.cue.lang.psi.CueMultilineStringLit;
import dev.monogon.cue.lang.psi.CuePsiElementFactory;
import dev.monogon.cue.lang.psi.CueStringLiteral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueMultilineStringElementManipulator extends AbstractStringLiteralElementManipulator<CueMultilineStringLit> {
    @Override
    protected @Nullable CueMultilineStringLit createStringLiteral(@NotNull CueStringLiteral element,
                                                                  String unquotedContent,
                                                                  int paddingSize) {
        return CuePsiElementFactory.createMultilineStringLiteral(element.getProject(), unquotedContent, paddingSize);
    }
}
