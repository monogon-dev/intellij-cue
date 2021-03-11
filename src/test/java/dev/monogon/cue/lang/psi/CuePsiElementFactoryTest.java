package dev.monogon.cue.lang.psi;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CuePsiElementFactoryTest extends CueLightTest {
    @Test
    public void simpleString() {
        var string = CuePsiElementFactory.createSimpleStringLiteral(getProject(), "my content", 0);
        assertEquals("\"my content\"", string.getText());

        string = CuePsiElementFactory.createSimpleStringLiteral(getProject(), "my content", 1);
        assertEquals("#\"my content\"#", string.getText());
    }

    @Test
    public void multilineString() {
        var string = CuePsiElementFactory.createMultilineStringLiteral(getProject(), "my content", 0);
        assertEquals("\"\"\"\nmy content\n\"\"\"", string.getText());

        string = CuePsiElementFactory.createMultilineStringLiteral(getProject(), "my content", 1);
        assertEquals("#\"\"\"\nmy content\n\"\"\"#", string.getText());
    }

    @Test
    public void simpleBytes() {
        var bytes = CuePsiElementFactory.createSimpleBytesLiteral(getProject(), "my content", 0);
        assertEquals("'my content'", bytes.getText());

        bytes = CuePsiElementFactory.createSimpleBytesLiteral(getProject(), "my content", 1);
        assertEquals("#'my content'#", bytes.getText());
    }

    @Test
    public void multilineBytes() {
        var bytes = CuePsiElementFactory.createMultilineBytesLiteral(getProject(), "my content", 0);
        assertEquals("'''\nmy content\n'''", bytes.getText());

        bytes = CuePsiElementFactory.createMultilineBytesLiteral(getProject(), "my content", 1);
        assertEquals("#'''\nmy content\n'''#", bytes.getText());
    }
}