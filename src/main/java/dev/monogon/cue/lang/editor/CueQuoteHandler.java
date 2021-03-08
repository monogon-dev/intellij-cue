package dev.monogon.cue.lang.editor;

import com.intellij.codeInsight.editorActions.MultiCharQuoteHandler;
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import dev.monogon.cue.lang.CueTokenTypes;
import dev.monogon.cue.lang.CueTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueQuoteHandler extends SimpleTokenSetQuoteHandler implements MultiCharQuoteHandler {
    public CueQuoteHandler() {
        super(TokenSet.orSet(CueTokenTypes.LITERALS_OPENING_QUOTES));
    }

    @Override
    public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
        return CueTokenTypes.LITERALS_OPENING_QUOTES.contains(iterator.getTokenType());
    }

    @Override
    public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
        // not accepting """ and ''' to disable typing over the closing quote
        // entering " inside of """<caret>""" should insert a single quote
        return CueTokenTypes.LITERALS_SINGLE_CLOSING_QUOTES.contains(iterator.getTokenType());
    }

    @Override
    public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
        return CueTokenTypes.LITERALS_OPENING_QUOTES.contains(iterator.getTokenType());
    }

    @Override
    public @Nullable CharSequence getClosingQuote(@NotNull HighlighterIterator iterator, int offset) {
        var type = iterator.getTokenType();
        if (CueTokenTypes.LITERALS_OPENING_QUOTES.contains(type)) {
            return getClosingQuote(type);
        }

        if (CueTokenTypes.WHITESPACE_OR_NEWLINE.contains(type)) {
            try {
                iterator.retreat();

                type = iterator.getTokenType();
                if (CueTokenTypes.LITERALS_OPENING_QUOTES.contains(type)) {
                    return getClosingQuote(type);
                }
            }
            finally {
                iterator.advance();
            }
        }

        return null;
    }

    @Override
    public boolean isInsideLiteral(HighlighterIterator iterator) {
        // seems to be only used with a com.intellij.codeInsight.editorActions.JavaLikeQuoteHandler
        return false;
    }

    @Nullable
    private static CharSequence getClosingQuote(@NotNull IElementType openingQuote) {
        if (openingQuote == CueTypes.DOUBLE_QUOTE) {
            return "\"";
        }
        if (openingQuote == CueTypes.SINGLE_QUOTE) {
            return "'";
        }
        if (openingQuote == CueTypes.MULTILINE_STRING_START) {
            return "\"\"\"";
        }
        if (openingQuote == CueTypes.MULTILINE_BYTES_START) {
            return "'''";
        }
        return null;
    }
}
