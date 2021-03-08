package dev.monogon.cue.lang.editor.folding;

import com.intellij.application.options.editor.CodeFoldingOptionsProvider;
import com.intellij.openapi.options.BeanConfigurable;
import dev.monogon.cue.Messages;
import org.jetbrains.annotations.NotNull;

public class CueFoldingOptionsProvider extends BeanConfigurable<CueFoldingSettings> implements CodeFoldingOptionsProvider {
    @SuppressWarnings("unused")
    public CueFoldingOptionsProvider() {
        this(CueFoldingSettingsService.getSettings());
    }

    public CueFoldingOptionsProvider(@NotNull CueFoldingSettings settings) {
        super(settings, Messages.get("folding.settings.title"));

        checkBox(Messages.get("folding.settings.fileComments"),
                 () -> settings.foldFileComments,
                 value -> settings.foldFileComments = value);
        checkBox(Messages.get("folding.settings.imports"),
                 () -> settings.foldImports,
                 value -> settings.foldImports = value);
        checkBox(Messages.get("folding.settings.importGroups"),
                 () -> settings.foldImportGroups,
                 value -> settings.foldImportGroups = value);
    }
}
