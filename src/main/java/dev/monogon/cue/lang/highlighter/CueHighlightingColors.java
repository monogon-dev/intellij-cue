package dev.monogon.cue.lang.highlighter;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public interface CueHighlightingColors {
    TextAttributesKey COMMENT = createTextAttributesKey("cue.comment", DefaultLanguageHighlighterColors.LINE_COMMENT);
    TextAttributesKey IDENTIFIER = createTextAttributesKey("cue.identifier", DefaultLanguageHighlighterColors.IDENTIFIER);
    TextAttributesKey KEYWORD = createTextAttributesKey("cue.keyword", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey BOOL_LITERAL = createTextAttributesKey("cue.bool", DefaultLanguageHighlighterColors.KEYWORD);
    TextAttributesKey FLOAT_LITERAL = createTextAttributesKey("cue.float", DefaultLanguageHighlighterColors.NUMBER);
    TextAttributesKey INT_LITERAL = createTextAttributesKey("cue.int", DefaultLanguageHighlighterColors.NUMBER);
    TextAttributesKey NULL_LITERAL = createTextAttributesKey("cue.null", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey OPERATOR = createTextAttributesKey("cue.operator", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey PARENTHESES = createTextAttributesKey("cue.parens", DefaultLanguageHighlighterColors.PARENTHESES);
    TextAttributesKey BRACKETS = createTextAttributesKey("cue.brackets", DefaultLanguageHighlighterColors.BRACKETS);
    TextAttributesKey BRACES = createTextAttributesKey("cue.braces", DefaultLanguageHighlighterColors.BRACES);

    TextAttributesKey STRING_CONTENT = createTextAttributesKey("cue.string_content", DefaultLanguageHighlighterColors.STRING);
    //TextAttributesKey STRING_LITERAL = createTextAttributesKey("cue.string_literal", DefaultLanguageHighlighterColors.STRING);
    //TextAttributesKey STRING_LITERAL_MULTILINE = createTextAttributesKey("cue.string_multiline", STRING_LITERAL);
    //TextAttributesKey BYTE_LITERAL = createTextAttributesKey("cue.byte_literal", DefaultLanguageHighlighterColors.STRING);
    //TextAttributesKey BYTE_LITERAL_MULTILINE = createTextAttributesKey("cue.byte_multiline", BYTE_LITERAL);
}
