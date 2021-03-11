package dev.monogon.cue.settings;

import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;

@State(name = "CUE local settings", storages = @Storage(value = "cue-application.xml", roamingType = RoamingType.DISABLED))
public class CueLocalSettingsService implements PersistentStateComponent<CueLocalSettings> {
    private final CueLocalSettings state = new CueLocalSettings();

    @Override
    public CueLocalSettings getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull CueLocalSettings state) {
        this.state.applyFrom(state);
    }

    public static CueLocalSettings getSettings() {
        return ServiceManager.getService(CueLocalSettingsService.class).getState();
    }
}
