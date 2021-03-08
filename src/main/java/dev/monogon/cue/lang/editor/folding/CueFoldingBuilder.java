package dev.monogon.cue.lang.editor.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.SyntaxTraverser;
import com.intellij.util.SmartList;
import dev.monogon.cue.lang.psi.CueStructLit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CueFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root,
                                                          @NotNull Document document, boolean quick) {

        List<FoldingDescriptor> result = new SmartList<>();
        SyntaxTraverser.psiTraverser(root).forEach(e -> foldElement(e, result));
        return result.toArray(FoldingDescriptor.EMPTY);
    }

    private void foldElement(PsiElement element, List<FoldingDescriptor> result) {
        if (element instanceof CueStructLit) {
            foldStruct((CueStructLit)element, result);
        }
    }

    private void foldStruct(CueStructLit struct, List<FoldingDescriptor> result) {
        var rightBrace = struct.getRightBrace();
        if (rightBrace == null) {
            return;
        }

        var range = TextRange.create(struct.getLeftBrace().getTextRange().getStartOffset(),
                                     rightBrace.getTextRange().getEndOffset());
        result.add(new FoldingDescriptor(struct.getNode(), range, null, "{...}"));
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}