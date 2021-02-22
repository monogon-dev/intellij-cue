package dev.monogon.cue;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase;
import dev.monogon.cue.lang.CueFileType;
import dev.monogon.cue.lang.psi.CueFile;
import org.jetbrains.annotations.NotNull;

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
        return (CueFile)myFixture.configureByText(CueFileType.INSTANCE, content);
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
