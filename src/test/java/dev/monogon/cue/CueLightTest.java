package dev.monogon.cue;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase;
import com.intellij.util.io.DigestUtil;
import dev.monogon.cue.lang.psi.CueFile;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

abstract public class CueLightTest extends LightPlatformCodeInsightFixture4TestCase {
    private final String relativeTestDataPath;

    public CueLightTest() {
        this("");
    }

    public CueLightTest(String relativeTestDataPath) {
        this.relativeTestDataPath = relativeTestDataPath;
    }

    @Override
    protected String getTestDataPath() {
        return CueTests.findTestData(relativeTestDataPath);
    }

    @NotNull
    protected CueFile createCueFile(@NotNull String content) {
        // for odd reasons following tests break when "configureByText(FileType, content)" is used, but only with 2021.3
        var filename = DigestUtil.sha1Hex(content.getBytes(StandardCharsets.UTF_8)) + ".cue";
        return (CueFile)myFixture.configureByText(filename, content);
    }

    @NotNull
    protected <T> T findTypedElement(Class<T> type) {
        var offset = myFixture.getCaretOffset();
        var e = myFixture.getFile().findElementAt(offset);
        var parent = (T)PsiTreeUtil.findFirstParent(e, false, type::isInstance);
        assertNotNull("typed parent wasn't found: " + type.getSimpleName(), parent);
        return parent;
    }
}
