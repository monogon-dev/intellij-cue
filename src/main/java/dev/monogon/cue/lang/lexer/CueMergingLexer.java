package dev.monogon.cue.lang.lexer;

import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.TokenSet;
import dev.monogon.cue.lang.CueTokenTypes;
import dev.monogon.cue.lang.CueTypes;

/**
 * Lexer adapter, which merges multiple tokens of the same type into one.
 * In the grammar "unicode_value" is just a single character. We merge afterwards to keep as much of the original grammar as possible
 * and to avoid ambiguities.
 */
public class CueMergingLexer extends MergingLexerAdapter {
    public static final TokenSet MERGED_TOKENS = TokenSet.create(
        CueTypes.UNICODE_VALUE, TokenType.WHITE_SPACE, CueTokenTypes.WHITE_SPACE_NEWLINE
    );

    public CueMergingLexer() {
        super(new CueFlexLexer(), MERGED_TOKENS);
    }
}
