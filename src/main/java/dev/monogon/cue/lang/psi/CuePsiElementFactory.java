package dev.monogon.cue.lang.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.LocalTimeCounter;
import dev.monogon.cue.lang.CueFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CuePsiElementFactory {
    private CuePsiElementFactory() {
    }

    @Nullable
    public static CueSimpleStringLit createSimpleStringLiteral(@NotNull Project project, @NotNull String unquotedContent, int paddingSize) {
        var padding = "#".repeat(paddingSize);
        var text = String.format("%s\"%s\"%s", padding, unquotedContent, padding);
        var file = PsiFileFactory.getInstance(project).createFileFromText("__.cue", CueFileType.INSTANCE,
                                                                          text, LocalTimeCounter.currentTime(), true);

        return PsiTreeUtil.getParentOfType(file.findElementAt(1), CueSimpleStringLit.class);
    }

    @Nullable
    public static CueSimpleBytesLit createSimpleBytesLiteral(@NotNull Project project, @NotNull String unquotedContent, int paddingSize) {
        var padding = "#".repeat(paddingSize);
        var text = String.format("%s'%s'%s", padding, unquotedContent, padding);
        var file = PsiFileFactory.getInstance(project).createFileFromText("__.cue", CueFileType.INSTANCE,
                                                                          text, LocalTimeCounter.currentTime(), true);

        return PsiTreeUtil.getParentOfType(file.findElementAt(1), CueSimpleBytesLit.class);
    }

    @Nullable
    public static CueMultilineStringLit createMultilineStringLiteral(@NotNull Project project,
                                                                     @NotNull String unquotedContent,
                                                                     int paddingSize) {
        assert unquotedContent.isEmpty() || unquotedContent.endsWith("\n");

        var padding = "#".repeat(paddingSize);
        var lfContent = "\n" + unquotedContent;
        var text = String.format("%s\"\"\"%s\"\"\"%s", padding, lfContent, padding);
        var file = PsiFileFactory.getInstance(project).createFileFromText("__.cue", CueFileType.INSTANCE,
                                                                          text, LocalTimeCounter.currentTime(), true);

        return PsiTreeUtil.getParentOfType(file.findElementAt(1), CueMultilineStringLit.class);
    }

    public static CueMultilineBytesLit createMultilineBytesLiteral(@NotNull Project project,
                                                                   @NotNull String unquotedContent,
                                                                   int paddingSize) {
        assert unquotedContent.isEmpty() || unquotedContent.endsWith("\n");

        var padding = "#".repeat(paddingSize);
        var lfContent = "\n" + unquotedContent;
        var text = String.format("%s'''%s'''%s", padding, lfContent, padding);
        var file = PsiFileFactory.getInstance(project).createFileFromText("__.cue", CueFileType.INSTANCE,
                                                                          text, LocalTimeCounter.currentTime(), true);

        return PsiTreeUtil.getParentOfType(file.findElementAt(1), CueMultilineBytesLit.class);
    }
}
