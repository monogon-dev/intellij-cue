package dev.monogon.cue.lang.psi;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CueLabelNameTest extends CueLightTest {
    @Test
    public void isOptionalFieldName() {
        createCueFile("{ n<caret>ame?: string }");
        assertTrue(findTypedElement(CueLabelName.class).isOptionalFieldName());

        createCueFile("{ \"n<caret>ame\"?: string }");
        assertTrue(findTypedElement(CueLabelName.class).isOptionalFieldName());
    }

    @Test
    public void isNotOptionalFieldName() {
        createCueFile("{ n<caret>ame: string }");
        assertFalse(findTypedElement(CueLabelName.class).isOptionalFieldName());

        createCueFile("{ \"n<caret>ame\": string }");
        assertFalse(findTypedElement(CueLabelName.class).isOptionalFieldName());
    }
}