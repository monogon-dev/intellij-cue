package dev.monogon.cue.lang.injection;

import com.intellij.openapi.util.TextRange;

import java.util.Objects;

class InjectionData {
    String prefix = null;
    String suffix = null;
    TextRange range = null;

    InjectionData(TextRange range, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.range = range;
    }

    @Override
    public String toString() {
        return "InjectionData{" +
               "prefix='" + prefix + '\'' +
               ", suffix='" + suffix + '\'' +
               ", range=" + range +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InjectionData data = (InjectionData)o;
        return Objects.equals(prefix, data.prefix) &&
               Objects.equals(suffix, data.suffix) &&
               Objects.equals(range, data.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, suffix, range);
    }
}
