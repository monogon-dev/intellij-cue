package dev.monogon.cue.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import dev.monogon.cue.lang.psi.*;
import org.jetbrains.annotations.NotNull;

abstract class CueLetClauseMixin extends CueCompositeElementImpl implements CueLetClause {
    public CueLetClauseMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public boolean isValidBlock() {
        PsiElement parent = getParent();
        if (parent instanceof CueStartClause || parent instanceof CueClause) {
            parent = parent.getParent();
        }
        if (!(parent instanceof CueClauses)) {
            return false;
        }
        return parent.getParent() instanceof CueComprehension;
    }
}
