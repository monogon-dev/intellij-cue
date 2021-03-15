package dev.monogon.cue.lang.psi.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiLanguageInjectionHost;
import dev.monogon.cue.CueLightTest;
import dev.monogon.cue.lang.psi.CueMultilineBytesLit;
import org.junit.Test;

public class CueMultilineBytesLiteralMixinTest extends CueLightTest {
    @Test
    public void contentRange() {
        myFixture.configureByText("a.cue", "'''content<caret>'''");
        var string = findTypedElement(CueMultilineBytesLit.class);
        assertEquals(TextRange.create(3, 10), string.getLiteralContentRange());
    }

    @Test
    public void contentRangeEmpty() {
        myFixture.configureByText("a.cue", "'''\n'''");
        var string = findTypedElement(CueMultilineBytesLit.class);
        assertTrue(string.isValidHost());
        assertEquals(TextRange.create(4, 4), string.getLiteralContentRange());
    }

    @Test
    public void contentRangePadded() {
        myFixture.configureByText("a.cue", "##'''content<caret>'''##");
        var string = findTypedElement(CueMultilineBytesLit.class);
        assertEquals(TextRange.create(5, 12), string.getLiteralContentRange());
    }

    @Test
    public void updateText() {
        myFixture.configureByText("a.cue", "'''\ncontent<caret>\n'''");
        var string = findTypedElement(CueMultilineBytesLit.class);

        var newString1 = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new");
        });
        assertTrue(newString1 instanceof CueMultilineBytesLit);
        assertEquals("'''\nnew\n'''", newString1.getText());

        var newEmptyString = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("");
        });
        assertTrue(newEmptyString instanceof CueMultilineBytesLit);
        assertEquals("'''\n'''", newEmptyString.getText());
    }

    @Test
    public void updateTextEscaped() {
        myFixture.configureByText("a.cue", "'''\ncontent<caret>\n'''");
        var string = findTypedElement(CueMultilineBytesLit.class);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueMultilineBytesLit);
        assertEquals("'''\nnew\t\tcontent\nnext line\n'''", replacement.getText());
    }

    @Test
    public void updateTextEscapedPadded() {
        myFixture.configureByText("a.cue", "###'''\ncontent<caret>\n'''###");
        var string = findTypedElement(CueMultilineBytesLit.class);

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("new\t\tcontent\nnext line");
        });
        assertTrue(replacement instanceof CueMultilineBytesLit);
        assertEquals("###'''\nnew\t\tcontent\nnext line\n'''###", replacement.getText());
    }

    @Test
    public void updateTextEmpty() {
        myFixture.configureByText("a.cue", "###'''\n'''###");
        var string = findTypedElement(CueMultilineBytesLit.class);
        assertTrue(string.isValidHost());

        // updating with text, which requires escapes, has to insert insert escaped characters
        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("line\nline");
        });
        assertTrue(replacement instanceof CueMultilineBytesLit);
        assertTrue(replacement.isValidHost());
        assertEquals("###'''\nline\nline\n'''###", replacement.getText());
    }

    @Test
    public void updateTextUnicode() {
        myFixture.configureByText("a.cue", "'''\ncontent<caret>\n'''");
        var string = findTypedElement(CueMultilineBytesLit.class);

        var replacement = WriteCommandAction.runWriteCommandAction(getProject(), (Computable<PsiLanguageInjectionHost>)() -> {
            return string.updateText("\\u65e5本\\U00008a9e");
        });
        assertTrue(replacement instanceof CueMultilineBytesLit);
        assertEquals("'''\n\\\\u65e5本\\\\U00008a9e\n'''", replacement.getText());
    }
}