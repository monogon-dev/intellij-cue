package dev.monogon.cue.lang.editor.codeStyle;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.*;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable.IndentOption;
import dev.monogon.cue.lang.CueLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CueCodeStyleProvider extends LanguageCodeStyleSettingsProvider {
    @Override
    public @NotNull Language getLanguage() {
        return CueLanguage.INSTANCE;
    }

    @Override
    public @NotNull String getExternalLanguageId() {
        return "cue";
    }

    @Override
    public @Nullable IndentOptionsEditor getIndentOptionsEditor() {
        return new IndentOptionsEditor(this);
    }

    @Override
    public @NotNull CodeStyleConfigurable createConfigurable(@NotNull CodeStyleSettings baseSettings,
                                                             @NotNull CodeStyleSettings modelSettings) {
        return new CodeStyleAbstractConfigurable(baseSettings, modelSettings, getLanguage().getDisplayName()) {
            @Override
            protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
                return new CueCodeStylePanel(settings, modelSettings);
            }
        };
    }

    @Override
    protected void customizeDefaults(@NotNull CommonCodeStyleSettings commonSettings,
                                     @NotNull CommonCodeStyleSettings.IndentOptions indentOptions) {
        indentOptions.USE_TAB_CHARACTER = true;
        indentOptions.TAB_SIZE = 2;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer,
                                  @NotNull SettingsType settingsType) {
        // hides the "use spaces indent" and "use tab indent" setting
        consumer.showStandardOptions(IndentOption.TAB_SIZE.toString());
    }

    @Override
    public @Nullable String getCodeSample(@NotNull SettingsType settingsType) {
        return "{\n" +
               "\t\"name\": \"value\"\n" +
               "\t\"count\": 42 \n" +
               "}";
    }
}
