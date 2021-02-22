package dev.monogon.cue.lang;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface CueTokenTypes {
    // tokenizing newlines, which are whitespace, as separate tokens to simplify out CueCommaInsertingLexer
    IElementType WHITE_SPACE_NEWLINE = new CueTokenType("NEWLINE");
    TokenSet WHITESPACE_TOKENS = TokenSet.create(TokenType.WHITE_SPACE, WHITE_SPACE_NEWLINE);

    IElementType COMMENT = new CueTokenType("LINE_COMMENT");
    TokenSet COMMENTS = TokenSet.create(COMMENT);

    TokenSet STRING_LITERALS = TokenSet.EMPTY;

    TokenSet OPERATORS = TokenSet.create(
        CueTypes.ADD_OP,
        CueTypes.MUL_OP,
        CueTypes.REL_OP,
        CueTypes.OP_DISJUNCTION,
        CueTypes.OP_UNIFICATION,
        CueTypes.OP_AND,
        CueTypes.OP_OR,
        CueTypes.OP_EQ
    );

    TokenSet IDENTIFIERS = TokenSet.create(
        CueTypes.IDENTIFIER,
        CueTypes.IDENTIFIER_PREDECLARED);
}
