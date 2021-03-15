package dev.monogon.cue.lang.psi.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiLanguageInjectionHost;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.lang.psi.CueMultilineStringLit;
import org.junit.Test;

public class CueMultilineStringLiteralMixinTest extends CueLightTest {
    @Test
    public void contentRange() {
        myFixture.configureByText("a.cue", "\"\"\"\ncontent<caret>\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);
        assertTrue(string.isValidHost());
        assertEquals(TextRange.create(4, 12), string.getLiteralContentRange());
    }

    @Test
    public void contentRangeEmpty() {
        myFixture.configureByText("a.cue", "\"\"\"\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);
        assertTrue(string.isValidHost());
        assertEquals(TextRange.create(4, 4), string.getLiteralContentRange());
    }

    @Test
    public void noLineFeed() {
        myFixture.configureByText("a.cue", "\"\"\"content<caret>\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);
        assertFalse(string.isValidHost());
    }

    @Test
    public void contentRangePadded() {
        myFixture.configureByText("a.cue", "##\"\"\"\ncontent<caret>\n\"\"\"##");
        var string = findTypedElement(CueMultilineStringLit.class);
        assertTrue(string.isValidHost());
        assertEquals(TextRange.create(6, 14), string.getLiteralContentRange());
    }

    @Test
    public void updateText() {
        myFixture.configureByText("a.cue", "\"\"\"\ncontent<caret>\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);

        var newString1 = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new");
        });
        assertTrue(newString1 instanceof CueMultilineStringLit);
        assertEquals("\"\"\"\nnew\n\"\"\"", newString1.getText());

        var newEmptyString = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("");
        });
        assertTrue(newEmptyString instanceof CueMultilineStringLit);
        assertEquals("\"\"\"\n\"\"\"", newEmptyString.getText());
    }

    @Test
    public void updateLinefeedText() {
        myFixture.configureByText("a.cue", "\"\"\"\ncontent<caret>\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);

        var newString1 = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new");
        });
        assertTrue(newString1 instanceof CueMultilineStringLit);
        assertEquals("\"\"\"\nnew\n\"\"\"", newString1.getText());
    }

    @Test
    public void updateTextEscaped() {
        myFixture.configureByText("a.cue", "\"\"\"\ncontent<caret>\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueMultilineStringLit);
        assertEquals("\"\"\"\nnew\t\tcontent\nnext line\n\"\"\"", replacement.getText());
    }

    @Test
    public void updateTextEscapedPadded() {
        myFixture.configureByText("a.cue", "###\"\"\"\ncontent<caret>\n\"\"\"###");
        var string = findTypedElement(CueMultilineStringLit.class);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueMultilineStringLit);
        assertEquals("###\"\"\"\nnew\t\tcontent\nnext line\n\"\"\"###", replacement.getText());
    }

    @Test
    public void updateTextUnicode() {
        myFixture.configureByText("a.cue", "\"\"\"\ncontent<caret>\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("\\u65e5本\\U00008a9e");
        });
        assertTrue(replacement instanceof CueMultilineStringLit);
        assertEquals("\"\"\"\n\\\\u65e5本\\\\U00008a9e\n\"\"\"", replacement.getText());
    }

    @Test
    public void updateWithTripleQuote() {
        myFixture.configureByText("a.cue", "\"\"\"\ncontent<caret>\n\"\"\"");
        var string = findTypedElement(CueMultilineStringLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("a\"\"\"b");
        });
        assertTrue(replacement instanceof CueMultilineStringLit);
        assertEquals("triple quote in content must be escaped", "\"\"\"\na\\\"\"\"b\n\"\"\"", replacement.getText());
    }
}