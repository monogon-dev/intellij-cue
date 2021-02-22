package dev.monogon.cue.lang.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import dev.monogon.cue.lang.CueTokenTypes;
import dev.monogon.cue.lang.CueTypes;
import dev.monogon.cue.lang.lexer.CueCommaInsertingLexer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CueSyntaxHighlighter extends SyntaxHighlighterBase implements SyntaxHighlighter {
    private static final Map<IElementType, TextAttributesKey> map = new HashMap<>();

    static {
        map.put(CueTokenTypes.COMMENT, CueHighlightingColors.COMMENT);
        map.put(CueTypes.IDENTIFIER, CueHighlightingColors.IDENTIFIER);
        map.put(CueTypes.IDENTIFIER_PREDECLARED, CueHighlightingColors.IDENTIFIER_PREDECLARED);
        map.put(CueTypes.KEYWORD, CueHighlightingColors.KEYWORD);

        map.put(CueTypes.BOOL_LIT, CueHighlightingColors.BOOL_LITERAL);
        map.put(CueTypes.FLOAT_LIT, CueHighlightingColors.FLOAT_LITERAL);
        map.put(CueTypes.INT_LIT, CueHighlightingColors.INT_LITERAL);
        map.put(CueTypes.NULL_LIT, CueHighlightingColors.NULL_LITERAL);

        map.put(CueTypes.DOUBLE_QUOTE, CueHighlightingColors.STRING_DOUBLE_QUOTES);
        map.put(CueTypes.DOUBLE_QUOTE_END, CueHighlightingColors.STRING_DOUBLE_QUOTES);
        map.put(CueTypes.SINGLE_QUOTE, CueHighlightingColors.STRING_SINGLE_QUOTES);
        map.put(CueTypes.SINGLE_QUOTE_END, CueHighlightingColors.STRING_SINGLE_QUOTES);
        map.put(CueTypes.MULTILINE_STRING_START, CueHighlightingColors.TRIPLE_QUOTES_STRING);
        map.put(CueTypes.MULTILINE_STRING_END, CueHighlightingColors.TRIPLE_QUOTES_STRING);
        map.put(CueTypes.MULTILINE_BYTES_START, CueHighlightingColors.TRIPLE_QUOTES_BYTE);
        map.put(CueTypes.MULTILINE_BYTES_END, CueHighlightingColors.TRIPLE_QUOTES_BYTE);
        map.put(CueTypes.UNICODE_VALUE, CueHighlightingColors.STRING_CONTENT);
        map.put(CueTypes.ESCAPED_CHAR, CueHighlightingColors.STRING_ESCAPED_CHAR);
        map.put(CueTypes.BYTE_VALUE, CueHighlightingColors.BYTE_VALUE);

        map.put(CueTypes.LEFT_PAREN, CueHighlightingColors.PARENTHESES);
        map.put(CueTypes.RIGHT_PAREN, CueHighlightingColors.PARENTHESES);

        map.put(CueTypes.LEFT_BRACKET, CueHighlightingColors.BRACKETS);
        map.put(CueTypes.RIGHT_BRACKET, CueHighlightingColors.BRACKETS);

        map.put(CueTypes.LEFT_CURLY, CueHighlightingColors.BRACES);
        map.put(CueTypes.RIGHT_CURLY, CueHighlightingColors.BRACES);

        map.put(CueTypes.COMMA, CueHighlightingColors.COMMA);

        // token sets
        fillMap(map, CueTokenTypes.OPERATORS, CueHighlightingColors.OPERATOR);
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        return pack(map.get(tokenType));
    }

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new CueCommaInsertingLexer();
    }
}
