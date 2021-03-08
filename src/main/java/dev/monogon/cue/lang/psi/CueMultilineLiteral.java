package dev.monogon.cue.lang.psi;

import com.intellij.psi.PsiElement;

public interface CueMultilineLiteral extends CueLiteral {
    PsiElement getLiteralStartElement();

    PsiElement getLiteralEndElement();
}
