package dev.monogon.cue.lang.navigation;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.util.PsiTreeUtil;
import dev.monogon.cue.lang.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CueStructureViewElement implements StructureViewTreeElement {
    private final CueCompositeElement element;

    public CueStructureViewElement(@NotNull CueCompositeElement element) {
        assert PsiTreeUtil.instanceOf(element, CueFile.class,CueField.class);
        this.element = element;
    }

    @Override
    public CueCompositeElement getValue() {
        return element;
    }

    @Override
    public @NotNull ItemPresentation getPresentation() {
        final ItemPresentation presentation = element.getPresentation();
        if (presentation == null){
            throw new RuntimeException("" + element.getClass());
        }
        return presentation;
    }

    private void getFieldOfExpression(CueExpression expression, List<CueStructureViewElement> agg) {
        // Using aggregator since we have binary op & where each one of them can be a CueStructLit which defines new fields
        if (expression == null) {
            return;
        } else if (expression instanceof CueStructLit) {
            ((CueStructLit) expression)
                    .getDeclarationList()
                    .stream()
                    .filter(x-> x instanceof CueField)
                    .map(CueStructureViewElement::new)
                    .forEach(agg::add);
        } else if (expression instanceof CueUnificationBinaryExpr) {
            var expr =  (CueUnificationBinaryExpr) expression;
            getFieldOfExpression(expr.getLeft(), agg);
            getFieldOfExpression(expr.getRight(), agg);
        }
    }

    @Override
    public TreeElement @NotNull [] getChildren() {
        if (element instanceof CueFile) {
            return ((CueFile)element).getFields()
                    .stream()
                    .map(CueStructureViewElement::new)
                    .toArray(CueStructureViewElement[]::new);
        } else if (element instanceof CueField){
            var children = new ArrayList<CueStructureViewElement>();
            getFieldOfExpression(((CueField) element).getExpression(), children);
            return children.toArray(new CueStructureViewElement[0]);
        }else {
            return EMPTY_ARRAY;
        }
    }

    @Override
    public void navigate(boolean requestFocus) {
        element.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return element.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element.canNavigateToSource();
    }
}
