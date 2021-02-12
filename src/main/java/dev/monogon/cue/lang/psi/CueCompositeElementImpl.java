package dev.monogon.cue.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

abstract public class CueCompositeElementImpl extends ASTWrapperPsiElement implements CueCompositeElement {
    public CueCompositeElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
