package dev.monogon.cue.lang.lexer;

import com.intellij.lexer.FlexAdapter;

public class CueFlexLexer extends FlexAdapter {
    public CueFlexLexer() {
        super(new _CueLexer());
    }
}
