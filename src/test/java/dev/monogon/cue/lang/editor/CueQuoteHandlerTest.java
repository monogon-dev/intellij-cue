package dev.monogon.cue.lang.editor;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CueQuoteHandlerTest extends CueLightTest {
    @Test
    public void simpleStringQuotes() {
        createCueFile("{key: <caret>\n}");
        myFixture.type('"');

        assertEquals("{key: \"\"\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void simpleStringQuotesTypeOver() {
        createCueFile("{key: \"<caret>\"}");
        myFixture.type('"');
        assertEquals("{key: \"\"}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineStringQuotesLast() {
        createCueFile("{key: \"\"<caret>\n}");
        myFixture.type("\"");

        assertEquals("{key: \"\"\"\"\"\"\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineStringQuotes() {
        createCueFile("{key: <caret>\n}");
        myFixture.type("\"\"\"");

        assertEquals("{key: \"\"\"\"\"\"\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineStringQuotesTypeOver() {
        createCueFile("{key: \"\"\"<caret>\"\"\"}");
        myFixture.type('"');
        // typing a quote inside a multiline string shouldn't overwrite: """ " """
        assertEquals("{key: \"\"\"\"\"\"\"}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void simpleByteQuotes() {
        createCueFile("{key: <caret>\n}");
        myFixture.type('\'');

        assertEquals("{key: ''\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void simpleByteQuotesTypeOver() {
        createCueFile("{key: '<caret>'}");
        myFixture.type('\'');
        assertEquals("{key: ''}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineByteQuotes() {
        createCueFile("{key: <caret>\n}");
        myFixture.type("'''");

        assertEquals("{key: ''''''\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineByteQuotesTypeOver() {
        createCueFile("{key: '''<caret>'''}");
        myFixture.type('\'');
        // typing a quote inside a multiline string shouldn't overwrite: ''' ' '''
        assertEquals("{key: '''''''}", myFixture.getEditor().getDocument().getText());
    }
}