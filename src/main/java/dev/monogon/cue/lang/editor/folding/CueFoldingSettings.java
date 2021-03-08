package dev.monogon.cue.lang.editor.folding;

import com.intellij.util.xmlb.annotations.OptionTag;

public class CueFoldingSettings {
    @OptionTag("foldImportGroups")
    public volatile boolean foldImportGroups = false;

    public void applyFrom(CueFoldingSettings other) {
        this.foldImportGroups = other.foldImportGroups;
    }
}
