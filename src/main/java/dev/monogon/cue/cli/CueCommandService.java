package dev.monogon.cue.cli;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Service to interact with the cue command line tool.
 */
public interface CueCommandService {
    /**
     * Calls "cue fmt", writes the given content on STDIN and returns STDOUT on success (exit code 0) or an error in other cased.
     *
     * @return The formatted content, if available.
     */
    @Nullable
    String format(@NotNull String content, long timeout, TimeUnit unit) throws ExecutionException;

    @NotNull
    static CueCommandService getInstance() {
        return ServiceManager.getService(CueCommandService.class);
    }
}
