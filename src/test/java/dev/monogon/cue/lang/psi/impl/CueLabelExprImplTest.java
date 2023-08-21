package dev.monogon.cue.lang.psi.impl;

import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.lang.psi.CueLabelExpr;
import org.junit.Test;

public class CueLabelExprImplTest extends CueLightTest {
    @Test
    public void constraintOptional() {
        createCueFile("#Person: {\n" +
                      "    <caret>name!: string\n" +
                      "    age?:  int\n" +
                      "}");

        var field = findTypedElement(CueLabelExpr.class);
        assertTrue(field.isRequiredFieldConstraint());
        assertFalse(field.isOptionalFieldConstraint());
    }

    @Test
    public void constraintRequired() {
        createCueFile("#Person: {\n" +
                      "    name!: string\n" +
                      "    <caret>age?:  int\n" +
                      "}");

        var field = findTypedElement(CueLabelExpr.class);
        assertFalse(field.isRequiredFieldConstraint());
        assertTrue(field.isOptionalFieldConstraint());
    }
}