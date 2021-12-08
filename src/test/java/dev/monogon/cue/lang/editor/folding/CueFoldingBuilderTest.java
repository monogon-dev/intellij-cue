package dev.monogon.cue.lang.editor.folding;

import com.intellij.testFramework.FileBasedTestCaseHelperEx;
import dev.monogon.cue.CueParameterizedLightTest;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.nio.file.Paths;

public class CueFoldingBuilderTest extends CueParameterizedLightTest implements FileBasedTestCaseHelperEx {
    @Test
    public void folding() {
        var file = Paths.get(getTestDataPath()).resolve(testFileName).toString();
        myFixture.testFoldingWithCollapseStatus(file);
    }

    @Override
    public String getRelativeBasePath() {
        return "lang/cue/folding";
    }

    @Override
    public @Nullable String getFileSuffix(String fileName) {
        return null;
    }
}