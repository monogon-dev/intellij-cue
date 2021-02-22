package dev.monogon.cue.lang.psi;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CueLetClauseTest extends CueLightTest {
    @Test
    public void validComprehensionClause() {
        createCueFile("a: { for x in a l<caret>et y = x {z: y} }");
        var clause = findTypedElement(CueLetClause.class);
        assertTrue(clause.isValidBlock());
    }

    @Test
    public void invalidComprehensionClause() {
        createCueFile("l<caret>et y = 1");
        var clause = findTypedElement(CueLetClause.class);
        assertFalse(clause.isValidBlock());
    }
}