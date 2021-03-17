package dev.monogon.cue.lang.editor.formatter;

import com.intellij.application.options.CodeStyle;
import com.intellij.formatting.IndentInfo;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.lineIndent.LineIndentProvider;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.text.CharArrayUtil;
import dev.monogon.cue.lang.CueFileType;
import dev.monogon.cue.lang.CueLanguage;
import dev.monogon.cue.lang.CueTokenTypes;
import dev.monogon.cue.lang.CueTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueLineIndentProvider implements LineIndentProvider {
    // tokens, where the next line should have additional indent after TOKEN + NEWLINE
    private static final TokenSet EXPAND_TOKENS = TokenSet.create(
        CueTypes.LEFT_CURLY, CueTypes.LEFT_BRACKET, CueTypes.LEFT_PAREN
    );

    @Override
    public @Nullable String getLineIndent(@NotNull Project project, @NotNull Editor editor, @Nullable Language language, int offset) {
        assert isSuitableFor(language);

        if (offset == 0) {
            return DO_NOT_ADJUST;
        }

        CueSemanticEditorPosition position = getPosition(editor, offset);

        //if (position.isAt(CueTypes.RIGHT_CURLY)) {
        //    position.moveBefore();
        //    position.moveToEndOfPreviousLine();
        //    if (position.isAtAnyOf(CueTokenTypes.COMMAS)) {
        //        return getIndentString(editor, position.getStartOffset(), false);
        //    }
        //    return null;
        //}

        position.moveBefore();
        if (position.isAtAnyOf(CueTokenTypes.WHITESPACE_OR_NEWLINE)) {
            position.moveToEndOfPreviousLine();
            if (position.isAtAnyOf(EXPAND_TOKENS)) {
                return getIndentString(editor, position.getStartOffset(), true);
            }
        }

        return null;
    }

    private String getIndentString(@NotNull Editor editor, int offset, boolean expand) {
        CodeStyleSettings settings = CodeStyle.getSettings(editor);
        CommonCodeStyleSettings.IndentOptions indentOptions = settings.getIndentOptions(CueFileType.INSTANCE);
        CharSequence docChars = editor.getDocument().getCharsSequence();

        String baseIndent = "";
        if (offset > 0) {
            int indentStart = CharArrayUtil.shiftBackwardUntil(docChars, offset, "\n") + 1;
            if (indentStart >= 0) {
                int indentEnd = CharArrayUtil.shiftForward(docChars, indentStart, " \t");
                int indentSize = indentEnd - indentStart;
                if (expand && indentSize > 0) {
                    baseIndent = docChars.subSequence(indentStart, indentEnd).toString();
                }
                else if (!expand && indentSize > indentOptions.INDENT_SIZE) {
                    // fixme limit to multiple of INDENT_SIZE?
                    baseIndent = docChars.subSequence(indentStart, indentEnd - indentOptions.INDENT_SIZE).toString();
                }
            }
        }

        if (expand) {
            //fixme
            baseIndent += new IndentInfo(0, indentOptions.TAB_SIZE, 0).generateNewWhiteSpace(indentOptions);
        }
        return baseIndent;
    }

    @Override
    public boolean isSuitableFor(@Nullable Language language) {
        return language != null && language.isKindOf(CueLanguage.INSTANCE);
    }

    static CueSemanticEditorPosition getPosition(Editor editor, int offset) {
        return new CueSemanticEditorPosition((EditorEx)editor, offset);
    }
}
