package dev.monogon.cue.lang.editor.folding;

import com.intellij.util.xmlb.annotations.OptionTag;

public class CueFoldingSettings {
    @OptionTag("foldFileComments")
    public volatile boolean foldFileComments = false;

    public void applyFrom(CueFoldingSettings other) {
        this.foldFileComments = other.foldFileComments;
    }
}
