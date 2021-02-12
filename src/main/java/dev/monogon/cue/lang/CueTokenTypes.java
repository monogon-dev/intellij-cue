package dev.monogon.cue.lang;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface CueTokenTypes {
    IElementType COMMENT = new CueTokenType("Comment");

    TokenSet COMMENTS = TokenSet.create(COMMENT);
    TokenSet STRING_LITERALS = TokenSet.create();
}
