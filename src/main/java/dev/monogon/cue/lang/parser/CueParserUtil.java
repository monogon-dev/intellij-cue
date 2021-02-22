package dev.monogon.cue.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import dev.monogon.cue.lang.CueTokenTypes;
import dev.monogon.cue.lang.CueTypes;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public class CueParserUtil extends GeneratedParserUtilBase {
    private static final TokenSet REJECTED_ATTR_TOKEN = TokenSet.create(
        CueTypes.LEFT_PAREN, CueTypes.RIGHT_PAREN,
        CueTypes.LEFT_BRACKET, CueTypes.RIGHT_BRACKET,
        CueTypes.LEFT_CURLY, CueTypes.RIGHT_CURLY
    );

    /**
     * reads a keyword token.
     * This is needed because PsiBuilder keeps remapped tokens, even if rules failed to parse later.
     * This has to handle the reverse direction of the methods below.
     * An alternative fix is to move the mapping to the lexer.
     */
    public static boolean kw(@NotNull PsiBuilder b, int level, @NotNull String name) {
        var type = b.getTokenType();
        if (type == CueTypes.KEYWORD && name.equals(b.getTokenText())) {
            b.advanceLexer();
            return true;
        }
        if (CueTokenTypes.IDENTIFIERS.contains(type) && name.equals(b.getTokenText())) {
            b.remapCurrentToken(CueTypes.KEYWORD);
            b.advanceLexer();
            return true;
        }
        return false;
    }

    // attr_token      = /* any token except '(', ')', '[', ']', '{', or '}' */
    // https://cuelang.org/docs/references/spec/#structs
    public static boolean attr_token(PsiBuilder b, int level) {
        if (REJECTED_ATTR_TOKEN.contains(b.getTokenType())) {
            return false;
        }
        // fixme do we have to remap the token as ATTR or leave as-is?
        b.advanceLexer();
        return true;
    }

    /**
     * All keywords are allowed as labels/field names.
     * https://cuelang.org/docs/references/spec/#keywords
     */
    public static boolean struct_label(PsiBuilder b, int level) {
        IElementType type = b.getTokenType();
        if (CueTokenTypes.IDENTIFIERS.contains(type)) {
            b.advanceLexer();
            return true;
        }
        if (type == CueTypes.KEYWORD) {
            b.remapCurrentToken(CueTypes.IDENTIFIER);
            b.advanceLexer();
            return true;
        }
        return false;
    }

    /**
     * Unless noted otherwise, keywords can also be used as identifiers to refer to the same name.
     * <p>
     * "null", "true", "false" can never be used to refer to a field of the same name.
     * This restriction is to ensure compatibility with JSON configuration files.
     * <p>
     * https://cuelang.org/docs/references/spec/#keywords
     */
    public static boolean identifier_ref(PsiBuilder b, int level) {
        IElementType type = b.getTokenType();
        if (CueTokenTypes.IDENTIFIERS.contains(type)) {
            b.advanceLexer();
            return true;
        }

        // null is a NULL_LIT, true and false are BOOL_LIT, all others are KEYWORD, so we can just remap KEYWORD
        if (type == CueTypes.KEYWORD) {
            b.remapCurrentToken(CueTypes.IDENTIFIER);
            b.advanceLexer();
            return true;
        }
        return false;
    }

    /**
     * Tokens which are allowed as attribute names.
     * null, true, false seem to be allowed in addition to the regular identifier rules.
     */
    public static boolean attribute_name(PsiBuilder b, int level) {
        IElementType type = b.getTokenType();
        if (CueTokenTypes.IDENTIFIERS.contains(type)) {
            b.advanceLexer();
            return true;
        }

        if (type == CueTypes.KEYWORD || type == CueTypes.NULL_LIT || type == CueTypes.BOOL_LIT) {
            b.remapCurrentToken(CueTypes.IDENTIFIER);
            b.advanceLexer();
            return true;
        }
        return false;
    }
}