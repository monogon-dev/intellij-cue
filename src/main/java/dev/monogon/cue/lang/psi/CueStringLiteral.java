package dev.monogon.cue.lang.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import dev.monogon.cue.lang.CueTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CueStringLiteral extends CueLiteral, PsiLanguageInjectionHost {
    boolean isMultilineLiteral();

    @NotNull
    List<CueInterpolation> getInterpolationList();

    @NotNull
    PsiElement getOpeningQuote();

    @Nullable
    PsiElement getClosingQuote();

    @Override
    default PsiLanguageInjectionHost updateText(@NotNull String text) {
        return ElementManipulators.handleContentChange(this, text);
    }

    @Override
    default boolean isValidHost() {
        return getClosingQuote() != null;
    }

    /**
     * @return range covering the content of the literal, relative to the parent's start offset.
     */
    default TextRange getLiteralContentRange() {
        var openingQuote = getOpeningQuote();
        var openingNext = openingQuote.getNextSibling();
        var closingQuote = getClosingQuote();
        var lf = isMultilineLiteral()
                 && openingNext instanceof LeafPsiElement
                 && ((LeafPsiElement)openingNext).getElementType() == CueTypes.NEWLINE
                 ? 1 : 0;

        // for multiline strings the content range is including the trailing linefeed
        // because otherwise IntelliJ will incorrectly update the content when
        // changing content "abc" to "abc\nxyz". For unknown reasons in this specific case
        // IntelliJ isn't updating the injected host ranges properly
        // Including the trailing linefeed is a workaround
        return TextRange.create(openingQuote.getStartOffsetInParent() + openingQuote.getTextLength() + lf,
                                closingQuote == null ? getTextLength() : closingQuote.getStartOffsetInParent());
    }

    /**
     * @return The number of #-chars used as escape padding. This is 0 for most literals.
     */
    default int getEscapePaddingSize() {
        return StringUtil.countChars(getOpeningQuote().getText(), '#', 0, true);
    }
}
