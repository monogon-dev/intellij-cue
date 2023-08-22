package dev.monogon.cue.lang.highlighter;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import dev.monogon.cue.Icons;
import dev.monogon.cue.Messages;
import dev.monogon.cue.lang.CueLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static dev.monogon.cue.Messages.lazyMessage;
import static dev.monogon.cue.lang.highlighter.CueHighlightingColors.*;

public class CueColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] ATTRIBUTES = new AttributesDescriptor[]{
        new AttributesDescriptor(lazyMessage("colorSettings.comment"), COMMENT),
        new AttributesDescriptor(lazyMessage("colorSettings.identifier"), IDENTIFIER),
        new AttributesDescriptor(lazyMessage("colorSettings.identifier_predeclared"), IDENTIFIER_PREDECLARED),
        new AttributesDescriptor(lazyMessage("colorSettings.keyword"), KEYWORD),
        new AttributesDescriptor(lazyMessage("colorSettings.attributeName"), ATTRIBUTE_NAME),

        new AttributesDescriptor(lazyMessage("colorSettings.literals.bool"), BOOL_LITERAL),
        new AttributesDescriptor(lazyMessage("colorSettings.literals.float"), FLOAT_LITERAL),
        new AttributesDescriptor(lazyMessage("colorSettings.literals.int"), INT_LITERAL),
        new AttributesDescriptor(lazyMessage("colorSettings.literals.null"), NULL_LITERAL),

        new AttributesDescriptor(lazyMessage("colorSettings.operator"), OPERATOR),

        new AttributesDescriptor(lazyMessage("colorSettings.parentheses"), PARENTHESES),
        new AttributesDescriptor(lazyMessage("colorSettings.brackets"), BRACKETS),
        new AttributesDescriptor(lazyMessage("colorSettings.braces"), BRACES),

        new AttributesDescriptor(lazyMessage("colorSettings.string_content"), STRING_CONTENT),
        new AttributesDescriptor(lazyMessage("colorSettings.string_single_quote"), STRING_SINGLE_QUOTES),
        new AttributesDescriptor(lazyMessage("colorSettings.string_double_quote"), STRING_DOUBLE_QUOTES),
        new AttributesDescriptor(lazyMessage("colorSettings.string_triple_quote_string"), TRIPLE_QUOTES_STRING),
        new AttributesDescriptor(lazyMessage("colorSettings.string_triple_quote_byte"), TRIPLE_QUOTES_BYTE),
        new AttributesDescriptor(lazyMessage("colorSettings.string_escaped_char"), STRING_ESCAPED_CHAR),
        new AttributesDescriptor(lazyMessage("colorSettings.byte_value"), BYTE_VALUE),

        // annotator based
        new AttributesDescriptor(lazyMessage("colorSettings.field_name"), FIELD_NAME),
        new AttributesDescriptor(lazyMessage("colorSettings.field_name_optional"), FIELD_NAME_OPTIONAL),
        new AttributesDescriptor(lazyMessage("colorSettings.field_name_required"), FIELD_NAME_REQUIRED),
        new AttributesDescriptor(lazyMessage("colorSettings.field_name_dynamic"), FIELD_NAME_DYNAMIC),
    };

    private static final Map<String, TextAttributesKey> ADDITIONAL_DESCRIPTORS = new HashMap<>();

    static {
        ADDITIONAL_DESCRIPTORS.put("field", FIELD_NAME);
        ADDITIONAL_DESCRIPTORS.put("field_optional", FIELD_NAME_OPTIONAL);
        ADDITIONAL_DESCRIPTORS.put("field_required", FIELD_NAME_REQUIRED);
        ADDITIONAL_DESCRIPTORS.put("field_dynamic", FIELD_NAME_DYNAMIC);
        ADDITIONAL_DESCRIPTORS.put("attr", ATTRIBUTE_NAME);
    }

    @Override
    public @Nullable Icon getIcon() {
        return Icons.CueLogo;
    }

    @Override
    public @NotNull SyntaxHighlighter getHighlighter() {
        return SyntaxHighlighterFactory.getSyntaxHighlighter(CueLanguage.INSTANCE, null, null);
    }

    @Override
    public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ADDITIONAL_DESCRIPTORS;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return ATTRIBUTES;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @Override
    public @NotNull String getDisplayName() {
        return Messages.get("colorSettings.displayName");
    }

    @Override
    public @NonNls
    @NotNull String getDemoText() {
        return "@<attr>type</attr>(demo)\n" +
               "@<attr>if</attr>(this)\n" +
               "// Release notes:\n" +
               "// - You can now specify your age and your hobby!\n" +
               "#V1: {\n" +
               "    <field_required>age</field_required>!:   >=0 & <=100\n" +
               "    <field>hobby</field>: string\n" +
               "}\n" +
               "// Release notes:\n" +
               "// - People get to be older than 100, so we relaxed it.\n" +
               "// - It seems not many people have a hobby, so we made it optional.\n" +
               "#V2: {\n" +
               "    <field>age</field>:    >=0 & <=150 // people get older now\n" +
               "    <field_optional>hobby</field_optional>?: string      // some people don't have a hobby\n" +
               "}\n" +
               "// Release notes:\n" +
               "// - Actually no one seems to have a hobby nowadays anymore, so we dropped the field.\n" +
               "#V3: {\n" +
               "    <field>age</field>: >=0 & <=150\n" +
               "}\n" +
               "// Demonstrate dynamic fields\n"+
               "#V4: {\n" +
               "    <field_dynamic>(dynamicName)</field_dynamic>: >=0 & <=150\n" +
               "}\n";
    }
}
