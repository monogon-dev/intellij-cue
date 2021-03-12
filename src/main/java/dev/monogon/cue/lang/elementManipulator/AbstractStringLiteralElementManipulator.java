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
        var contentRange = getRangeInElement(element);
        var content = contentRange.substring(element.getText());
        var escapedRangeContent = CueEscaperUtil.escapeCueString(newRangeContent, true, !(element instanceof CueMultilineLiteral), true,
                                                                 element instanceof CueSimpleBytesLit,
                                                                 element instanceof CueSimpleStringLit,
                                                                 element instanceof CueMultilineBytesLit,
                                                                 element instanceof CueMultilineStringLit,
                                                                 element.getEscapePaddingSize());
        // it's possible that the current content range is smaller than the passed in range
        // this could happen when an empty multiline string is updated with non-empty content
        var fixedRange = !contentRange.contains(range) ? contentRange.intersection(range) : range;
        var updatedContent = fixedRange.shiftLeft(contentRange.getStartOffset()).replace(content, escapedRangeContent);

        var replacement = createStringLiteral(element, updatedContent, element.getEscapePaddingSize());
        if (replacement == null) {
            throw new IncorrectOperationException("unable to create simple string literal for content:" + newRangeContent);
        }

        var replaced = element.replace(replacement);
        if (replaced == null) {
            throw new IncorrectOperationException("unable to replace with new simple string literal for content:" + newRangeContent);
        }
        return (T)replaced;
    }

    @Override
    public final T handleContentChange(@NotNull T element, String newContent) throws IncorrectOperationException {
        return handleContentChange(element, getRangeInElement(element), newContent);
    }
}
