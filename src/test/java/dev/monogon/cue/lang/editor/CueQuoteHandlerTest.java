package dev.monogon.cue.lang.editor;

import dev.monogon.cue.CueLightTest;
import org.junit.Test;

public class CueQuoteHandlerTest extends CueLightTest {
    @Test
    public void simpleStringQuotes() {
        myFixture.configureByText("a.cue", "{key: <caret>\n}");
        myFixture.type('"');

        assertEquals("{key: \"\"\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void simpleStringQuotesTypeOver() {
        myFixture.configureByText("a.cue", "{key: \"<caret>\"}");
        myFixture.type('"');
        assertEquals("{key: \"\"}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineStringQuotesLast() {
        myFixture.configureByText("a.cue", "{key: \"\"<caret>\n}");
        myFixture.type("\"");

        assertEquals("{key: \"\"\"\"\"\"\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineStringQuotes() {
        myFixture.configureByText("a.cue", "{key: <caret>\n}");
        myFixture.type("\"\"\"");

        assertEquals("{key: \"\"\"\"\"\"\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineStringQuotesTypeOver() {
        myFixture.configureByText("a.cue", "{key: \"\"\"<caret>\"\"\"}");
        myFixture.type('"');
        // typing a quote inside a multiline string shouldn't overwrite: """ " """
        assertEquals("{key: \"\"\"\"\"\"\"}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void simpleByteQuotes() {
        myFixture.configureByText("a.cue", "{key: <caret>\n}");
        myFixture.type('\'');

        assertEquals("{key: ''\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void simpleByteQuotesTypeOver() {
        myFixture.configureByText("a.cue", "{key: '<caret>'}");
        myFixture.type('\'');
        assertEquals("{key: ''}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineByteQuotes() {
        myFixture.configureByText("a.cue", "{key: <caret>\n}");
        myFixture.type("'''");

        assertEquals("{key: ''''''\n}", myFixture.getEditor().getDocument().getText());
    }

    @Test
    public void multilineByteQuotesTypeOver() {
        myFixture.configureByText("a.cue", "{key: '''<caret>'''}");
        myFixture.type('\'');
        // typing a quote inside a multiline string shouldn't overwrite: ''' ' '''
        assertEquals("{key: '''''''}", myFixture.getEditor().getDocument().getText());
    }
}