package dev.monogon.cue.lang.highlighter;

import com.intellij.openapi.util.text.StringUtil;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.CueTests;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * works on all files in src/test/data/psi/highlighting
 */
@RunWith(Parameterized.class)
public class CueSyntaxHighlighterTest extends CueLightTest {
    private final String filePath;

    public CueSyntaxHighlighterTest(String filePath) {
        super("psi/highlighting");
        this.filePath = filePath;
    }

    @Test
    public void highlighting() {
        myFixture.testHighlighting(true, true, true, getTestName(true) + ".cue");
    }

    @Override
    protected @NotNull String getTestName(boolean lowercaseFirstLetter) {
        return StringUtil.trimEnd(filePath, ".cue");
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> files() {
        return CueTests.findTestFiles(CueTests.findTestDataPath("psi", "highlighting"));
    }
}