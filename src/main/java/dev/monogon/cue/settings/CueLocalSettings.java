package dev.monogon.cue.settings;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Settings local to the current machine and environment.
 */
public class CueLocalSettings {
    @Nullable
    private volatile String cueExecutablePath;

    @Nullable
    public String getCueExecutablePath() {
        return StringUtil.nullize(cueExecutablePath);
    }

    public void setCueExecutablePath(@Nullable String path) {
        this.cueExecutablePath = path;
    }

    @Override
    public String toString() {
        return "CueLocalSettings{" +
               "cueExecutablePath='" + cueExecutablePath + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CueLocalSettings settings = (CueLocalSettings)o;
        return Objects.equals(cueExecutablePath, settings.cueExecutablePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cueExecutablePath);
    }

    public void applyFrom(@NotNull CueLocalSettings state) {
        this.cueExecutablePath = state.cueExecutablePath;
    }
}
