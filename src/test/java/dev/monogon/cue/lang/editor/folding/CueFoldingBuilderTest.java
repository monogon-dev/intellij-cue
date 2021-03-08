package dev.monogon.cue.lang.editor.folding;

import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.CueTests;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(Parameterized.class)
public class CueFoldingBuilderTest extends CueLightTest {
    private final String filename;

    public CueFoldingBuilderTest(@NotNull String filename) {
        super("lang/cue/folding");
        this.filename = filename;
    }

    @Test
    public void folding() {
        myFixture.testFoldingWithCollapseStatus(Paths.get(getTestDataPath()).resolve(filename).toString());
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> files() {
        return CueTests.findTestFiles(rootPath());
    }

    private static @NotNull Path rootPath() {
        return CueTests.findTestDataPath("lang", "cue", "folding");
    }
}