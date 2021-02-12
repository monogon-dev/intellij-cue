package dev.monogon.cue.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.TokenSet;
import dev.monogon.cue.lang.CueTypes;

public class CueParserUtil extends GeneratedParserUtilBase {
    private static final TokenSet REJECTED_ATTR_TOKEN = TokenSet.create(
        CueTypes.LEFT_PAREN, CueTypes.RIGHT_PAREN,
        CueTypes.LEFT_BRACKET, CueTypes.RIGHT_BRACKET,
        CueTypes.LEFT_CURLY, CueTypes.RIGHT_CURLY
    );

    // attr_token      = /* any token except '(', ')', '[', ']', '{', or '}' */
    // https://cuelang.org/docs/references/spec/#structs
    public static boolean attr_token(PsiBuilder b, int level) {
        if (REJECTED_ATTR_TOKEN.contains(b.getTokenType())) {
            return false;
        }
        b.advanceLexer();
        return true;
    }
}