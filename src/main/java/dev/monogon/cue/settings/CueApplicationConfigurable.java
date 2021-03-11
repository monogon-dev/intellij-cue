package dev.monogon.cue.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import dev.monogon.cue.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CueApplicationConfigurable implements Configurable {
    private final CueSettingsForm form = new CueSettingsForm();

    public CueApplicationConfigurable() {
        form.applyFrom(CueLocalSettingsService.getSettings());
    }

    @Override
    public String getDisplayName() {
        return Messages.get("applicationSettings.displayName");
    }

    @Override
    public @Nullable JComponent createComponent() {
        return form.getMainPanel();
    }

    @Override
    public boolean isModified() {
        CueLocalSettings settings = new CueLocalSettings();
        form.applyTo(settings);
        return !CueLocalSettingsService.getSettings().equals(settings);
    }

    @Override
    public void apply() throws ConfigurationException {
        form.applyTo(CueLocalSettingsService.getSettings());
    }
}
