package dev.monogon.cue.lang.editor.formatter;

import com.intellij.openapi.util.text.StringUtil;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.CueTests;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(Parameterized.class)
public class CueLineIndentProviderTest extends CueLightTest {
    private final String filePath;

    public CueLineIndentProviderTest(@NotNull String filePath) {
        this.filePath = filePath;
    }

    @Test
    public void lineIndent() throws IOException {
        myFixture.configureByFile(filePath);
        myFixture.type('\n');
        myFixture.checkResultByFile(filePath.replace(".cue", ".after.cue"));
    }

    @Override
    protected String getTestDataPath() {
        return rootPath().toString();
    }

    @Override
    protected @NotNull String getTestName(boolean lowercaseFirstLetter) {
        return StringUtil.trimEnd(filePath, ".cue");
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> files() {
        return CueTests.findTestFiles(rootPath(), s -> !s.endsWith(".after.cue"));
    }

    private static @NotNull Path rootPath() {
        return CueTests.findTestDataPath("lang", "cue", "lineIndent");
    }
}