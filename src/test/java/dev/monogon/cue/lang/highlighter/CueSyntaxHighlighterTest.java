package dev.monogon.cue.lang.highlighter;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CueSyntaxHighlighterTest extends CueLightTest {
    public CueSyntaxHighlighterTest() {
        super("psi/highlighting");
    }

    @Test
    public void helloWorld() {
        check();
    }

    private void check() {
        myFixture.testHighlighting(true, true, true, getTestName(true) + ".cue");
    }
}