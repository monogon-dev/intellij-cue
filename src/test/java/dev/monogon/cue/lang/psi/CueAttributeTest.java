package dev.monogon.cue.lang.psi;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CueAttributeTest extends CueLightTest {
    @Test
    public void getAttributeName() {
        createCueFile("@na<caret>me(value)");

        var attribute = findTypedElement(CueAttribute.class);
        assertNotNull(attribute.getAttributeNameElement());
        assertEquals("name", attribute.getAttributeName());
    }
}