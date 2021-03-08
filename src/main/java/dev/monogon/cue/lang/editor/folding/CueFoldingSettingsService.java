package dev.monogon.cue.lang.editor.folding;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@State(name = "cue-folding", storages = @Storage("cue-folding.xml"))
public class CueFoldingSettingsService implements PersistentStateComponent<CueFoldingSettings> {
    @NotNull
    private final CueFoldingSettings state = new CueFoldingSettings();

    @Override
    public @NotNull CueFoldingSettings getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull CueFoldingSettings state) {
        this.state.applyFrom(state);
    }

    @NotNull
    public static CueFoldingSettings getSettings() {
        return ServiceManager.getService(CueFoldingSettingsService.class).state;
    }
}
