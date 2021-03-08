package dev.monogon.cue.lang.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import dev.monogon.cue.lang.highlighter.CueHighlightingColors;
import dev.monogon.cue.lang.psi.CueAttribute;
import dev.monogon.cue.lang.psi.CueLabelName;
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
    }
}
