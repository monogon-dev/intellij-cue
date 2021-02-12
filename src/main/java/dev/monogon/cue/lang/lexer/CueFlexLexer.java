package dev.monogon.cue.lang.lexer;

import com.intellij.lexer.FlexAdapter;

public class CueFlexLexer extends FlexAdapter {
    public CueFlexLexer() {
        super(new _CueLexer() {
            @Override
            public void reset(CharSequence buffer, int start, int end, int initialState) {
                onReset();
                super.reset(buffer, start, end, initialState);
            }
        });
    }
}
