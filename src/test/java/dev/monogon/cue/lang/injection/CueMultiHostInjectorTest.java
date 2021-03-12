package dev.monogon.cue.lang.injection;

import com.intellij.openapi.util.TextRange;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.lang.psi.CueSimpleBytesLit;
import dev.monogon.cue.lang.psi.CueStringLiteral;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class CueMultiHostInjectorTest extends CueLightTest {
    @Test
    public void singleRange() {
        myFixture.configureByText("a.cue", "'cont<caret>ent'");
        var literal = findTypedElement(CueSimpleBytesLit.class);
        var ranges = CueMultiHostInjector.findInjectionRanges(literal);
        assertEquals(Collections.singletonList(new InjectionData(TextRange.create(1, 8), null, null)), ranges);
    }

    @Test
    public void interpolationMiddle() {
        myFixture.configureByText("a.cue", "'a\\(123)b'");
        var literal = findTypedElement(CueStringLiteral.class);
        var ranges = CueMultiHostInjector.findInjectionRanges(literal);

        var expected = Arrays.asList(
            new InjectionData(TextRange.create(1, 2), null, null),
            new InjectionData(TextRange.create(8, 9), "\\(123)", null));
        assertEquals(expected, ranges);
    }

    @Test
    public void interpolationFirst() {
        myFixture.configureByText("a.cue", "'\\(123)b'");
        var literal = findTypedElement(CueStringLiteral.class);
        var ranges = CueMultiHostInjector.findInjectionRanges(literal);

        var expected = Arrays.asList(
            new InjectionData(TextRange.create(1, 1), null, null),
            new InjectionData(TextRange.create(7, 8), "\\(123)", null));
        assertEquals(expected, ranges);
    }

    @Test
    public void interpolationEnd() {
        myFixture.configureByText("a.cue", "'a\\(123)'");
        var literal = findTypedElement(CueStringLiteral.class);
        var ranges = CueMultiHostInjector.findInjectionRanges(literal);

        var expected = Arrays.asList(
            new InjectionData(TextRange.create(1, 2), null, null),
            new InjectionData(TextRange.create(8, 8), "\\(123)", null));
        assertEquals(expected, ranges);
    }

    @Test
    public void interpolationMultiple() {
        myFixture.configureByText("a.cue", "'a\\(1)b\\(2)b\\(3)'");
        var literal = findTypedElement(CueStringLiteral.class);
        var ranges = CueMultiHostInjector.findInjectionRanges(literal);

        var expected = Arrays.asList(
            new InjectionData(TextRange.create(1, 2), null, null),
            new InjectionData(TextRange.create(6, 7), "\\(1)", null),
            new InjectionData(TextRange.create(11, 12), "\\(2)", null),
            new InjectionData(TextRange.create(16, 16), "\\(3)", null));
        assertEquals(expected, ranges);
    }
}