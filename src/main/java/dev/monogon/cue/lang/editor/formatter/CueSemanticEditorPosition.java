package dev.monogon.cue.lang.editor.formatter;

import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import dev.monogon.cue.lang.CueTokenTypes;
import org.jetbrains.annotations.NotNull;

final class CueSemanticEditorPosition {
    public static final @NotNull TokenSet WHITE_SPACE_SET = TokenSet.create(TokenType.WHITE_SPACE);
    private final HighlighterIterator iterator;

    CueSemanticEditorPosition(EditorEx editor, int offset) {
        iterator = editor.getHighlighter().createIterator(offset);
    }

    void moveBefore() {
        if (!iterator.atEnd()) {
            iterator.retreat();
        }
    }

    boolean isAt(@NotNull IElementType elementType) {
        return !iterator.atEnd() && iterator.getTokenType() == elementType;
    }

    void moveToEndOfPreviousLine() {
        moveBeforeOptional(WHITE_SPACE_SET);
        if (isAtAnyOf(CueTokenTypes.NEWLINES)) {
            moveBefore();
            moveBeforeOptional(WHITE_SPACE_SET);
        }
    }

    void moveBeforeOptional(@NotNull TokenSet tokens) {
        while (isAtAnyOf(tokens)) {
            iterator.retreat();
        }
    }

    boolean isAtAnyOf(TokenSet tokens) {
        return !iterator.atEnd() && tokens.contains(iterator.getTokenType());
    }

    int getStartOffset() {
        return !iterator.atEnd() ? iterator.getStart() : -1;
    }
}
