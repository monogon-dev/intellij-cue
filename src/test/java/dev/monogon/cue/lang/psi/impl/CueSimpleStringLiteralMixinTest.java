package dev.monogon.cue.lang.psi.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiLanguageInjectionHost;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.lang.psi.CueSimpleStringLit;
import org.junit.Test;

public class CueSimpleStringLiteralMixinTest extends CueLightTest {
    @Test
    public void contentRange() {
        myFixture.configureByText("a.cue", "\"content<caret>\"");
        var string = findTypedElement(CueSimpleStringLit.class);
        assertEquals(TextRange.create(1, 8), string.getLiteralContentRange());
    }

    @Test
    public void contentRangePadded() {
        myFixture.configureByText("a.cue", "##\"content<caret>\"##");
        var string = findTypedElement(CueSimpleStringLit.class);
        assertEquals(TextRange.create(3, 10), string.getLiteralContentRange());
    }

    @Test
    public void updateText() {
        myFixture.configureByText("a.cue", "\"content<caret>\"");
        var string = findTypedElement(CueSimpleStringLit.class);

        WriteCommandAction.runWriteCommandAction(getProject(), () -> {
            var newString1 = string.updateText("new");
            assertTrue(newString1 instanceof CueSimpleStringLit);
            assertEquals("\"new\"", newString1.getText());
        });

        WriteCommandAction.runWriteCommandAction(getProject(), () -> {
            var newEmptyString = string.updateText("");
            assertTrue(newEmptyString instanceof CueSimpleStringLit);
            assertEquals("\"\"", newEmptyString.getText());
        });
    }

    @Test
    public void updateTextEscaped() {
        myFixture.configureByText("a.cue", "\"content<caret>\"");
        var string = findTypedElement(CueSimpleStringLit.class);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueSimpleStringLit);
        assertEquals("\"new\t\tcontent\\nnext line\"", replacement.getText());
    }

    @Test
    public void updateTextEscapedPadded() {
        myFixture.configureByText("a.cue", "###\"content<caret>\"###");
        var string = findTypedElement(CueSimpleStringLit.class);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueSimpleStringLit);
        assertEquals("###\"new\t\tcontent\\###nnext line\"###", replacement.getText());
    }

    @Test
    public void updateTextUnicode() {
        myFixture.configureByText("a.cue", "\"content<caret>\"");
        var string = findTypedElement(CueSimpleStringLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("\\u65e5本\\U00008a9e");
        });
        assertTrue(replacement instanceof CueSimpleStringLit);
        assertEquals("\"\\\\u65e5本\\\\U00008a9e\"", replacement.getText());
    }

    @Test
    public void updateTextInterpolation() {
        myFixture.configureByText("a.cue", "\"content<caret>\"");
        var string = findTypedElement(CueSimpleStringLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("a\\(123)b");
        });
        assertTrue(replacement instanceof CueSimpleStringLit);
        assertEquals("content update with interpolation-like text must escape it", "\"a\\\\(123)b\"", replacement.getText());
    }

    @Test
    public void updateTextInterpolationPadded() {
        myFixture.configureByText("a.cue", "##\"content<caret>\"##");
        var string = findTypedElement(CueSimpleStringLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("a\\(123)\\##(123)b");
        });
        assertTrue(replacement instanceof CueSimpleStringLit);
        assertEquals("content update with interpolation-like text must escape it", "##\"a\\(123)\\##\\##(123)b\"##", replacement.getText());
    }
}