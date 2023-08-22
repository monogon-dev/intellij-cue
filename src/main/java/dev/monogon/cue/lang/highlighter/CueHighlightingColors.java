package dev.monogon.cue.lang.highlighter;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public interface CueHighlightingColors {
    TextAttributesKey COMMENT = createTextAttributesKey("cue.comment", LINE_COMMENT);
    TextAttributesKey IDENTIFIER = createTextAttributesKey("cue.identifier", DefaultLanguageHighlighterColors.IDENTIFIER);
    TextAttributesKey IDENTIFIER_PREDECLARED =
        createTextAttributesKey("cue.identifier_predeclared", PREDEFINED_SYMBOL);
    TextAttributesKey KEYWORD = createTextAttributesKey("cue.keyword", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey BOOL_LITERAL = createTextAttributesKey("cue.bool", DefaultLanguageHighlighterColors.KEYWORD);
    TextAttributesKey FLOAT_LITERAL = createTextAttributesKey("cue.float", NUMBER);
    TextAttributesKey INT_LITERAL = createTextAttributesKey("cue.int", NUMBER);
    TextAttributesKey NULL_LITERAL = createTextAttributesKey("cue.null", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey OPERATOR = createTextAttributesKey("cue.operator", DefaultLanguageHighlighterColors.KEYWORD);

    TextAttributesKey PARENTHESES = createTextAttributesKey("cue.parens", DefaultLanguageHighlighterColors.PARENTHESES);
    TextAttributesKey BRACKETS = createTextAttributesKey("cue.brackets", DefaultLanguageHighlighterColors.BRACKETS);
    TextAttributesKey BRACES = createTextAttributesKey("cue.braces", DefaultLanguageHighlighterColors.BRACES);

    TextAttributesKey COMMA = createTextAttributesKey("cue.comma", DefaultLanguageHighlighterColors.COMMA);

    TextAttributesKey STRING_CONTENT = createTextAttributesKey("cue.string_content", STRING);
    TextAttributesKey STRING_SINGLE_QUOTES = createTextAttributesKey("cue.single_quote", STRING_CONTENT);
    TextAttributesKey STRING_DOUBLE_QUOTES = createTextAttributesKey("cue.double_quote", STRING_CONTENT);
    TextAttributesKey TRIPLE_QUOTES_STRING = createTextAttributesKey("cue.string_multiline", STRING_CONTENT);
    TextAttributesKey TRIPLE_QUOTES_BYTE = createTextAttributesKey("cue.byte_multiline", STRING_CONTENT);
    TextAttributesKey STRING_ESCAPED_CHAR = createTextAttributesKey("cue.escaped_char", VALID_STRING_ESCAPE);
    TextAttributesKey BYTE_VALUE = createTextAttributesKey("cue.byte_value", VALID_STRING_ESCAPE);

    // annotator highlighting, used in CueAnnotator
    TextAttributesKey FIELD_NAME = createTextAttributesKey("cue.field_name", INSTANCE_FIELD);
    TextAttributesKey FIELD_NAME_DYNAMIC = createTextAttributesKey("cue.field_name_dynamic", INSTANCE_FIELD);

    // the annotator merges constraint attributes with FIELD_NAME/FIELD_NAME_DYNAMIC
    TextAttributesKey FIELD_NAME_OPTIONAL = createTextAttributesKey("cue.field_name_optional");
    TextAttributesKey FIELD_NAME_REQUIRED = createTextAttributesKey("cue.field_name_required");

    TextAttributesKey ATTRIBUTE_NAME = createTextAttributesKey("cue.attribute_name", MARKUP_TAG);
}
