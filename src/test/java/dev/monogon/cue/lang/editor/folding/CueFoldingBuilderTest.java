package dev.monogon.cue.lang.editor.folding;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

import java.nio.file.Paths;

public class CueFoldingBuilderTest extends CueLightTest {
    public CueFoldingBuilderTest() {
        super("lang/cue/folding");
    }

    @Test
    public void structFolding() {
        testFolding();
    }

    @Test
    public void imports1() {
        testFolding();
    }

    @Test
    public void imports2() {
        testFolding();
    }

    private void testFolding() {
        myFixture.testFoldingWithCollapseStatus(resolveTestFile(getTestName(true) + ".cue"));
    }

    private String resolveTestFile(String name) {
        return Paths.get(getTestDataPath()).resolve(name).toString();
    }
}