package dev.monogon.cue.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiLanguageInjectionHost;
import dev.monogon.cue.lang.psi.CueMultilineStringLit;
import dev.monogon.cue.lang.psi.escaper.CueStringLiteralEscaper;
import org.jetbrains.annotations.NotNull;

abstract class CueMultilineStringLiteralMixin extends CueLiteralImpl implements CueMultilineStringLit {
    public CueMultilineStringLiteralMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @NotNull LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        return new CueStringLiteralEscaper(this);
    }

    @Override
    public boolean isMultilineLiteral() {
        return true;
    }
}
