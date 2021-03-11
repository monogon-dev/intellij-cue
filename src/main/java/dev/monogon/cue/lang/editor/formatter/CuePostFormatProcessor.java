package dev.monogon.cue.lang.editor.formatter;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.ExternalFormatProcessor;
import com.intellij.psi.impl.source.codeStyle.PostFormatProcessor;
import dev.monogon.cue.lang.psi.CueFile;
import org.jetbrains.annotations.NotNull;

/**
 * Calls our cue formatter, IntelliJ has no documentation for PostFormatProcessor. But it seems to assume that we callour own
 * external formatter.
 */
@SuppressWarnings("UnstableApiUsage")
public class CuePostFormatProcessor implements PostFormatProcessor {
    @Override
    public @NotNull PsiElement processElement(@NotNull PsiElement source, @NotNull CodeStyleSettings settings) {
        return source;
    }

    @Override
    public @NotNull TextRange processText(@NotNull PsiFile source,
                                          @NotNull TextRange rangeToReformat,
                                          @NotNull CodeStyleSettings settings) {
        if (!(source instanceof CueFile)) {
            return rangeToReformat;
        }

        var range = ExternalFormatProcessor.formatRangeInFile(source, rangeToReformat, false, false);
        return range != null ? range : rangeToReformat;
    }
}
