package dev.monogon.cue.lang.psi;

import dev.monogon.cue.CueLightTest;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

public class CueLabelNameTest extends CueLightTest {
    @Test
    public void isOptionalFieldName() {
        createCueFile("{ n<caret>ame?: string }");
        assertField(findTypedElement(CueLabelName.class), true, false, false);

        createCueFile("{ \"n<caret>ame\"?: string }");
        assertField(findTypedElement(CueLabelName.class), true, false, false);
    }

    @Test
    public void isRequiredFieldName() {
        createCueFile("{ n<caret>ame!: string }");
        assertField(findTypedElement(CueLabelName.class), false, true, false);

        createCueFile("{ \"n<caret>ame\"!: string }");
        assertField(findTypedElement(CueLabelName.class), false, true, false);
    }


    @Test
    public void isDynamicFieldName() {
        createCueFile("{ (n<caret>ame)!: string }");
        assertField(findTypedElement(CueLabelName.class), false, true, true);

        createCueFile("{ (\"n<caret>ame\")!: string }");
        assertField(findTypedElement(CueLabelName.class), false, true, true);
    }

    @Test
    public void isRegularFieldName() {
        createCueFile("{ n<caret>ame: string }");
        assertField(findTypedElement(CueLabelName.class), false, false, false);

        createCueFile("{ \"n<caret>ame\": string }");
        assertField(findTypedElement(CueLabelName.class), false, false, false);
    }

    private void assertField(@NotNull CueLabelName labelName, boolean isOptionalField, boolean isRequiredField, boolean isDynamicField) {
        assertEquals(isOptionalField, labelName.isOptionalFieldName());
        assertEquals(isRequiredField, labelName.isRequiredFieldName());
        assertEquals(isDynamicField, labelName.isDynamicFieldName());
    }
}