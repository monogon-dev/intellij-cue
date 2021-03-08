package dev.monogon.cue.lang.editor.folding;

import com.intellij.util.xmlb.annotations.OptionTag;

public class CueFoldingSettings {
    @OptionTag("foldFileComments")
    public volatile boolean foldFileComments = false;
    @OptionTag("foldImports")
    public volatile boolean foldImports = false;
    @OptionTag("foldImportGroups")
    public volatile boolean foldImportGroups = false;

    public void applyFrom(CueFoldingSettings other) {
        this.foldFileComments = other.foldFileComments;
        this.foldImports = other.foldImports;
        this.foldImportGroups = other.foldImportGroups;
    }
}
