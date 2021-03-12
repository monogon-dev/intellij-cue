package dev.monogon.cue.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiLanguageInjectionHost;
import dev.monogon.cue.lang.psi.CueSimpleStringLit;
import dev.monogon.cue.lang.psi.CueStringLiteralEscaper;
import org.jetbrains.annotations.NotNull;

abstract class CueSimpleStringLiteralMixin extends CueLiteralImpl implements CueSimpleStringLit {
    public CueSimpleStringLiteralMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @NotNull LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        return new CueStringLiteralEscaper(this);
    }

    @Override
    public boolean isMultilineLiteral() {
        return false;
    }
}
