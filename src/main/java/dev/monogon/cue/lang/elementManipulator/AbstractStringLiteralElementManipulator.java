package dev.monogon.cue.lang.elementManipulator;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.ElementManipulator;
import com.intellij.util.IncorrectOperationException;
import dev.monogon.cue.lang.psi.*;
import dev.monogon.cue.lang.util.CueEscaperUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractStringLiteralElementManipulator<T extends CueStringLiteral> implements ElementManipulator<T> {
    /**
     * Creates a new literal based on the unquoted content.
     * The caller of this method has to escape the content properly.
     *
     * @param element         element to update
     * @param unquotedContent the new content, without the quotes
     * @param paddingSize     number of # chars padding the escape prefix, e.g. 2 for \##
     * @return The new element, if available.
     */
    @Nullable
    protected abstract T createStringLiteral(@NotNull CueStringLiteral element, String unquotedContent, int paddingSize);

    @Override
    public final @NotNull TextRange getRangeInElement(@NotNull T element) {
        return element.getLiteralContentRange();
    }

    @SuppressWarnings("unchecked")
    @Override
    public final T handleContentChange(@NotNull T element,
                                       @NotNull TextRange range,
                                       String newRangeContent) throws IncorrectOperationException {
        var maxRange = getRangeInElement(element);
        var maxContent = maxRange.substring(element.getText());
        var escapedRangeContent = CueEscaperUtil.escapeCueString(newRangeContent, true, !(element instanceof CueMultilineLiteral), true,
                                                                 element instanceof CueSimpleBytesLit,
                                                                 element instanceof CueSimpleStringLit,
                                                                 element instanceof CueMultilineBytesLit,
                                                                 element instanceof CueMultilineStringLit,
                                                                 element.getEscapePaddingSize());
        // shiftLeft range to become relative to maxContent
        var updatedContent = range.shiftLeft(maxRange.getStartOffset()).replace(maxContent, escapedRangeContent);
        if (element instanceof CueMultilineLiteral && !updatedContent.isEmpty() && !updatedContent.endsWith("\n")) {
            updatedContent = updatedContent + "\n";
        }

        // replace children, keep original parent element
        // language injection in 2020.3 assumes that the host remains the same element (fixed in 2021.1?)
        var replacement = createStringLiteral(element, updatedContent, element.getEscapePaddingSize());
        if (replacement == null) {
            throw new IncorrectOperationException("unable to create simple string literal for content:" + newRangeContent);
        }
        element.getNode().replaceAllChildrenToChildrenOf(replacement.getNode());
        return element;
    }

    @Override
    public final T handleContentChange(@NotNull T element, String newContent) throws IncorrectOperationException {
        return handleContentChange(element, getRangeInElement(element), newContent);
    }
}
