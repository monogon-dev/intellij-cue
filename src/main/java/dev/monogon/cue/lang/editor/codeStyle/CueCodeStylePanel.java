package dev.monogon.cue.lang.editor.codeStyle;

import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import dev.monogon.cue.lang.CueLanguage;

public class CueCodeStylePanel extends TabbedLanguageCodeStylePanel {
    protected CueCodeStylePanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
        super(CueLanguage.INSTANCE, currentSettings, settings);
    }

    @Override
    protected void initTabs(CodeStyleSettings settings) {
        addIndentOptionsTab(settings);
    }
}
