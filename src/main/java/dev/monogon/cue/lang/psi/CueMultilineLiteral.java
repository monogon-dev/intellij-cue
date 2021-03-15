package dev.monogon.cue.lang.psi;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import dev.monogon.cue.lang.CueTypes;

public interface CueMultilineLiteral extends CueStringLiteral {
    @Override
    default boolean isValidHost() {
        var opening = getOpeningQuote();
        var next = opening.getNextSibling();
        if (!(next instanceof LeafPsiElement) || ((LeafPsiElement)next).getElementType() != CueTypes.NEWLINE) {
            return false;
        }
        return getClosingQuote() != null;
    }
}
