package dev.monogon.cue.lang.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import dev.monogon.cue.Messages;
import dev.monogon.cue.lang.CueTokenTypes;
import dev.monogon.cue.lang.highlighter.CueHighlightingColors;
import dev.monogon.cue.lang.psi.CueAttribute;
import dev.monogon.cue.lang.psi.CueElementList;
import dev.monogon.cue.lang.psi.CueLabelName;
import dev.monogon.cue.lang.psi.CueListLit;
import org.jetbrains.annotations.NotNull;

public class CueAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CueLabelName && ((CueLabelName)element).getSimpleStringLit() == null) {
            var label = (CueLabelName)element;
            if (label.isOptionalFieldName()) {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(CueHighlightingColors.FIELD_NAME_OPTIONAL)
                    .create();
            }
            else {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(CueHighlightingColors.FIELD_NAME)
                    .create();
            }
        }
        else if (element instanceof CueAttribute) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(((CueAttribute)element).getAttributeNameElement())
                .textAttributes(CueHighlightingColors.ATTRIBUTE_NAME)
                .create();
        }

        var elementType = element.getNode().getElementType();
        var parent = element.getParent();

        // warn about implicit commas in list literals
        if (elementType == CueTokenTypes.COMMA_IMPLICIT && (parent instanceof CueListLit || parent instanceof CueElementList)) {
            // \n in a list is a whitespace token, check the text content
            var next = PsiTreeUtil.nextLeaf(element);

            // only warn if it's not the implicit comma before the closing ] of the list
            if (!(parent instanceof CueListLit) || ((CueListLit)parent).getRightBracket() != PsiTreeUtil.nextVisibleLeaf(element)) {
                if (next instanceof PsiWhiteSpace && next.getText().startsWith("\n")) {
                    holder.newAnnotation(HighlightSeverity.ERROR, Messages.get("annotator.listLit.implicitCommaError"))
                        .afterEndOfLine()
                        .create();
                }
            }
        }
    }
}
