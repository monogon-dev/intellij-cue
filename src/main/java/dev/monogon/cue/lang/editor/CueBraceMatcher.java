package dev.monogon.cue.lang.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import dev.monogon.cue.lang.CueTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueBraceMatcher implements PairedBraceMatcher {
    private final BracePair[] PAIRS = new BracePair[]{
        new BracePair(CueTypes.LEFT_CURLY, CueTypes.RIGHT_CURLY, true),
        new BracePair(CueTypes.LEFT_PAREN, CueTypes.RIGHT_PAREN, false),
        new BracePair(CueTypes.LEFT_BRACKET, CueTypes.RIGHT_BRACKET, false),

        new BracePair(CueTypes.INTERPOLATION_START, CueTypes.INTERPOLATION_END, true),

        new BracePair(CueTypes.DOUBLE_QUOTE, CueTypes.DOUBLE_QUOTE_END, true),
        new BracePair(CueTypes.MULTILINE_STRING_START, CueTypes.MULTILINE_STRING_END, true),

        new BracePair(CueTypes.SINGLE_QUOTE, CueTypes.SINGLE_QUOTE_END, true),
        new BracePair(CueTypes.MULTILINE_BYTES_START, CueTypes.MULTILINE_BYTES_END, true),
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType,
                                                   @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
