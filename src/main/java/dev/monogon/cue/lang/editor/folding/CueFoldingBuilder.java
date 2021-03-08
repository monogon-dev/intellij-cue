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
import dev.monogon.cue.lang.psi.CueFile;
import dev.monogon.cue.lang.psi.CueImportDecl;
import dev.monogon.cue.lang.psi.CueStructLit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CueFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        var settings = CueFoldingSettingsService.getSettings();
        List<FoldingDescriptor> result = new SmartList<>();
        SyntaxTraverser.psiTraverser(root).forEach(e -> foldElement(e, result, settings));
        return result.toArray(FoldingDescriptor.EMPTY);
    }

    private void foldElement(@NotNull PsiElement element, @NotNull List<FoldingDescriptor> result, @NotNull CueFoldingSettings settings) {
        if (element instanceof CueFile) {
            foldFileImports((CueFile)element, settings, result);
        }
        if (element instanceof CueImportDecl) {
            foldImportGroup((CueImportDecl)element, settings, result);
        }
        if (element instanceof CueStructLit) {
            foldStruct((CueStructLit)element, result);
        }
    }

    private void foldFileImports(CueFile file, @NotNull CueFoldingSettings settings, List<FoldingDescriptor> result) {
        var imports = file.getImportDeclarations();
        if (imports.size() >= 2) {
            var first = imports.get(0);
            var last = imports.get(imports.size() - 1);
            var range = TextRange.create(first.getTextRange().getStartOffset(), last.getTextRange().getEndOffset());
            result.add(new FoldingDescriptor(file.getNode(), range, null, "import ...", settings.foldImports, Collections.emptySet()));
        }
    }

    private void foldImportGroup(@NotNull CueImportDecl element, @NotNull CueFoldingSettings settings, List<FoldingDescriptor> result) {
        var imports = element.getImportSpecList();
        var left = element.getLeftParen();
        var right = element.getRightParen();
        if (!imports.isEmpty() && left != null && right != null) {
            var range = TextRange.create(left.getTextRange().getStartOffset(), right.getTextRange().getEndOffset());
            result.add(new FoldingDescriptor(element.getNode(), range, null, "(...)", settings.foldImportGroups, Collections.emptySet()));
        }
    }

    private void foldStruct(@NotNull CueStructLit struct, List<FoldingDescriptor> result) {
        var rightBrace = struct.getRightBrace();
        if (rightBrace != null) {
            var range = TextRange.create(struct.getLeftBrace().getTextRange().getStartOffset(),
                                         rightBrace.getTextRange().getEndOffset());
            result.add(new FoldingDescriptor(struct.getNode(), range, null, "{...}"));
        }
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        // we're already adding the collapsed text with the marker definitions
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        // we're already setting the default state with the marker definitions, based on the cue folding settings
        return false;
    }
}