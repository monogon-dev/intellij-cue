package dev.monogon.cue.lang.editor.formatter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.ExternalFormatProcessor;
import dev.monogon.cue.lang.psi.CueFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * The format processing (via our PostFormatProcessor) is called on the EDT in a write action (as of 2020.3).
 * But format() expects a return value with the modified range. This makes it impossible to run the formatted
 * in the calling thread without blocking the UI and still returning the modified range.
 * <p>
 * The compromise we take is to return the complete range of the file and to execute the formatter in the background.
 * As soon as "cue fmt" returned, the new formatted content is applied via invokeLater{} and attached to the last undo command.
 */
@SuppressWarnings("UnstableApiUsage")
public class ExternalCueFormatter implements ExternalFormatProcessor {
    private static final Logger LOG = Logger.getInstance("#cue.formatter");

    @Override
    public boolean activeForFile(@NotNull PsiFile source) {
        return source instanceof CueFile;
    }

    @Override
    public @NonNls
    @NotNull String getId() {
        return "cue.externalFormatter";
    }

    // compatibility with 2021.1+
    // @Override
    public @Nullable TextRange format(@NotNull PsiFile file,
                                      @NotNull TextRange range,
                                      boolean canChangeWhiteSpacesOnly,
                                      boolean keepLineBreaks, boolean enableBulkUpdate) {
        return format(file, range, canChangeWhiteSpacesOnly, keepLineBreaks);
    }

    // compatibility with 2020.3
    // @Override
    public @Nullable TextRange format(@NotNull PsiFile file,
                                      @NotNull TextRange range,
                                      boolean canChangeWhiteSpacesOnly,
                                      boolean keepLineBreaks) {
        if (!file.isValid()) {
            return null;
        }

        // cue fmt doesn't support to format parts of a file
        if (!range.equals(file.getTextRange())) {
            return null;
        }

        var document = file.getViewProvider().getDocument();
        if (document == null) {
            return null;
        }

        var app = ApplicationManager.getApplication();
        app.executeOnPooledThread(() -> {
            try {
                var newContent = CueCommandService.getInstance().format(document.getText(), 5, TimeUnit.SECONDS);
                if (newContent != null) {
                    app.invokeLater(() -> {
                        var processor = CommandProcessor.getInstance();
                        processor.runUndoTransparentAction(() -> app.runWriteAction(() -> document.setText(newContent)));
                    });
                }
            }
            catch (Exception e) {
                LOG.debug("error executing cue fmt", e);
            }
        });
        return file.getTextRange();
    }

    @Override
    public @Nullable String indent(@NotNull PsiFile source, int lineStartOffset) {
        return null;
    }
}
