package dev.monogon.cue.lang.highlighter;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public interface CueHighlightingColors {
    TextAttributesKey COMMENT = createTextAttributesKey("cue.comment", DefaultLanguageHighlighterColors.LINE_COMMENT);
    TextAttributesKey IDENTIFIER = createTextAttributesKey("cue.identifier", DefaultLanguageHighlighterColors.IDENTIFIER);
    TextAttributesKey IDENTIFIER_PREDECLARED = createTextAttributesKey("cue.identifier_predeclared", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    TextAttributesKey KEYWORD = createTextAttributesKey("cue.keyword", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey BOOL_LITERAL = createTextAttributesKey("cue.bool", DefaultLanguageHighlighterColors.KEYWORD);
    TextAttributesKey FLOAT_LITERAL = createTextAttributesKey("cue.float", DefaultLanguageHighlighterColors.NUMBER);
    TextAttributesKey INT_LITERAL = createTextAttributesKey("cue.int", DefaultLanguageHighlighterColors.NUMBER);
    TextAttributesKey NULL_LITERAL = createTextAttributesKey("cue.null", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey OPERATOR = createTextAttributesKey("cue.operator", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey PARENTHESES = createTextAttributesKey("cue.parens", DefaultLanguageHighlighterColors.PARENTHESES);
    TextAttributesKey BRACKETS = createTextAttributesKey("cue.brackets", DefaultLanguageHighlighterColors.BRACKETS);
    TextAttributesKey BRACES = createTextAttributesKey("cue.braces", DefaultLanguageHighlighterColors.BRACES);

    TextAttributesKey COMMA = createTextAttributesKey("cue.comma", DefaultLanguageHighlighterColors.COMMA);

    TextAttributesKey STRING_CONTENT = createTextAttributesKey("cue.string_content", DefaultLanguageHighlighterColors.STRING);
    TextAttributesKey STRING_SINGLE_QUOTES = createTextAttributesKey("cue.single_quote", DefaultLanguageHighlighterColors.STRING);
    TextAttributesKey STRING_DOUBLE_QUOTES = createTextAttributesKey("cue.double_quote", DefaultLanguageHighlighterColors.STRING);
    TextAttributesKey TRIPLE_QUOTES_STRING = createTextAttributesKey("cue.string_multiline", DefaultLanguageHighlighterColors.STRING);
    TextAttributesKey TRIPLE_QUOTES_BYTE = createTextAttributesKey("cue.byte_multiline", DefaultLanguageHighlighterColors.STRING);
}