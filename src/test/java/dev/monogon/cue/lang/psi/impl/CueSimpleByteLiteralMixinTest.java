package dev.monogon.cue.lang.psi.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiLanguageInjectionHost;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.lang.psi.CueSimpleBytesLit;
import org.junit.Test;

public class CueSimpleByteLiteralMixinTest extends CueLightTest {
    @Test
    public void contentRange() {
        myFixture.configureByText("a.cue", "'content<caret>'");
        var string = findTypedElement(CueSimpleBytesLit.class);
        assertNotNull(string);
        assertEquals(TextRange.create(1, 8), string.getLiteralContentRange());
    }

    @Test
    public void contentRangePadded() {
        myFixture.configureByText("a.cue", "##'content<caret>'##");
        var literal = findTypedElement(CueSimpleBytesLit.class);
        assertNotNull(literal);
        assertEquals(TextRange.create(3, 10), literal.getLiteralContentRange());
    }

    @Test
    public void updateText() {
        myFixture.configureByText("a.cue", "'content<caret>'");
        var string = findTypedElement(CueSimpleBytesLit.class);
        assertNotNull(string);

        var newString1 = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new");
        });
        assertTrue(newString1 instanceof CueSimpleBytesLit);
        assertEquals("'new'", newString1.getText());

        var newEmptyString = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("");
        });
        assertTrue(newEmptyString instanceof CueSimpleBytesLit);
        assertEquals("''", newEmptyString.getText());
    }

    @Test
    public void updateTextEscaped() {
        myFixture.configureByText("a.cue", "'content<caret>'");
        var string = findTypedElement(CueSimpleBytesLit.class);
        assertNotNull(string);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueSimpleBytesLit);
        assertEquals("'new\t\tcontent\\nnext line'", replacement.getText());
    }

    @Test
    public void updateTextUnicode() {
        myFixture.configureByText("a.cue", "'content<caret>'");
        var string = findTypedElement(CueSimpleBytesLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("\\u65e5本\\U00008a9e");
        });
        assertTrue(replacement instanceof CueSimpleBytesLit);
        assertEquals("'\\u65e5本\\U00008a9e'", replacement.getText());
    }
}