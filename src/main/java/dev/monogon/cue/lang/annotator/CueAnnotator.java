package dev.monogon.cue.lang.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.DumbAware;
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

public class CueAnnotator implements Annotator, DumbAware {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CueLabelName) {
            annotateLabelName((CueLabelName)element, holder);
        }

        if (element instanceof CueAttribute) {
            annotateAttribute((CueAttribute)element, holder);
        }

        annotateImplicitComma(element, holder);
    }

    private static void annotateLabelName(@NotNull CueLabelName element, @NotNull AnnotationHolder holder) {
        var baseAttributeKey = element.isDynamicFieldName()
                               ? CueHighlightingColors.FIELD_NAME_DYNAMIC
                               : CueHighlightingColors.FIELD_NAME;

        TextAttributesKey constraintAttributeKey;
        if (element.isOptionalFieldName()) {
            constraintAttributeKey = CueHighlightingColors.FIELD_NAME_OPTIONAL;
        }
        else if (element.isRequiredFieldName()) {
            constraintAttributeKey = CueHighlightingColors.FIELD_NAME_REQUIRED;
        }
        else {
            constraintAttributeKey = null;
        }

        if (constraintAttributeKey == null) {
            // no merge needed
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION).textAttributes(baseAttributeKey).create();
        }
        else {
            // add constraint attributes on top of base attribute
            var scheme = EditorColorsManager.getInstance().getGlobalScheme();
            var base = scheme.getAttributes(baseAttributeKey);
            var constraint = scheme.getAttributes(constraintAttributeKey);
            var merged = TextAttributes.merge(base, constraint);
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION).enforcedTextAttributes(merged).create();
        }
    }

    private static void annotateAttribute(@NotNull CueAttribute element, @NotNull AnnotationHolder holder) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.getAttributeNameElement())
            .textAttributes(CueHighlightingColors.ATTRIBUTE_NAME)
            .create();
    }

    private static void annotateImplicitComma(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
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
