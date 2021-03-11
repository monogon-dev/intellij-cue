package dev.monogon.cue.lang.psi.escaper;

import dev.monogon.cue.lang.util.TextEscaperUtil;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class TextEscaperUtilTest {
    @Test
    public void noEscaped() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("abc", out, offsetsRef, false, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]);
        assertEquals(1, offsets[1]);
        assertEquals(2, offsets[2]);
        // out-of-range of original, case handled by caller
        assertEquals(3, offsets[3]);

        assertEquals("abc", out.toString());
    }

    @Test
    public void simpleEscaped() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("a\\ac", out, offsetsRef, false, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]); //a
        assertEquals(1, offsets[1]); //<bell>
        assertEquals(3, offsets[2]); //c
        // out-of-range of original, case handled by caller
        assertEquals(4, offsets[3]);

        assertEquals("a\u0007c", out.toString());
    }

    @Test
    public void simpleEscapedPadded() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("a\\##ac", out, offsetsRef, false, 2);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]); //a
        assertEquals(1, offsets[1]); //<bell>
        assertEquals(5, offsets[2]); //c
        // out-of-range of original, case handled by caller
        assertEquals(6, offsets[3]);

        assertEquals("a\u0007c", out.toString());
    }

    @Test
    public void unicodeEscaped4Digit() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("\\u65e5\\u672c\\u8a9e", out, offsetsRef, false, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]);
        assertEquals(6, offsets[1]);
        assertEquals(12, offsets[2]);
        // out-of-range of original, case handled by caller
        assertEquals(13, offsets[3]);

        assertEquals("日本語", out.toString());
    }

    @Test
    public void unicodeEscaped8Digit() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("\\U000065e5\\U0000672c\\U00008a9e", out, offsetsRef, false, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]);
        assertEquals(10, offsets[1]);
        assertEquals(20, offsets[2]);
        // out-of-range of original, case handled by caller
        assertEquals(21, offsets[3]);

        assertEquals("日本語", out.toString());
    }

    @Test
    public void byteHexEscaping() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("\\x30\\x31\\x32", out, offsetsRef, true, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]);
        assertEquals(4, offsets[1]);
        assertEquals(8, offsets[2]);
        // out-of-range of original, case handled by caller
        assertEquals(9, offsets[3]);

        assertEquals("012", out.toString());
    }

    @Test
    public void byteOctalEscaping() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("\\060\\061\\062", out, offsetsRef, true, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]);
        assertEquals(4, offsets[1]);
        assertEquals(8, offsets[2]);
        // out-of-range of original, case handled by caller
        assertEquals(9, offsets[3]);

        assertEquals("012", out.toString());
    }

    @Test
    public void allEscaped() {
        StringBuilder out = new StringBuilder();
        var offsetsRef = new AtomicReference<int[]>();
        var ok = TextEscaperUtil.parseLiteral("\\a\\b\\'", out, offsetsRef, false, 0);
        assertTrue(ok);

        var offsets = offsetsRef.get();
        assertNotNull(offsets);
        assertEquals(0, offsets[0]); //<bell>
        assertEquals(2, offsets[1]); //<backspace>
        assertEquals(4, offsets[2]); //'
        // out-of-range of original, case handled by caller
        assertEquals(6, offsets[3]);

        assertEquals("\u0007\b'", out.toString());
    }

    @Test
    public void escapeSimpleString() {
        assertEquals("abc", TextEscaperUtil.escapeCueString("abc", false, true, true, true, false, false, 0));
        assertEquals("abc", TextEscaperUtil.escapeCueString("abc", false, true, true, true, false, false, 10));

        assertEquals("\\b", TextEscaperUtil.escapeCueString("\b", false, true, true, true, false, false, 0));
        assertEquals("\\#b", TextEscaperUtil.escapeCueString("\b", false, true, true, true, false, false, 1));
        assertEquals("\\###b", TextEscaperUtil.escapeCueString("\b", false, true, true, true, false, false, 3));

        assertEquals("a\\bc", TextEscaperUtil.escapeCueString("a\bc", false, true, true, true, false, false, 0));

        // tab
        assertEquals("a\tc", TextEscaperUtil.escapeCueString("a\tc", true, true, true, true, false, false, 0));
        assertEquals("a\\tc", TextEscaperUtil.escapeCueString("a\tc", false, true, true, true, false, false, 0));
    }

    @Test
    public void escapeUnicode() {
        assertEquals("日本語", TextEscaperUtil.escapeCueString("日本語", true, true, true, true, false, false, 0));
        assertEquals("\\u65e5\\u672c\\u8a9e", TextEscaperUtil.escapeCueString("日本語", false, true, true, true, false,
                                                                              false, 0));
    }

    @Test
    public void escapeAlreadyEscaped() {
        assertEquals("\\a", TextEscaperUtil.escapeCueString("\\a", true, true, true, true, false, false, 0));
        assertEquals("a\\ab", TextEscaperUtil.escapeCueString("a\\ab", true, true, true, true, false, false, 0));
    }

    @Test
    public void escapePrefix() {
        assertTrue(TextEscaperUtil.isEscapePrefix("a\\tbc", 1, 0));
        assertTrue(TextEscaperUtil.isEscapePrefix("a\\#tbc", 1, 1));
        assertTrue(TextEscaperUtil.isEscapePrefix("a\\##tbc", 1, 2));
        assertTrue(TextEscaperUtil.isEscapePrefix("a\\###tbc", 1, 3));

        assertFalse(TextEscaperUtil.isEscapePrefix("a\\#tbc", 1, 0));
        assertFalse(TextEscaperUtil.isEscapePrefix("a\\##tbc", 1, 1));
        assertFalse(TextEscaperUtil.isEscapePrefix("a\\###tbc", 1, 2));
        assertFalse(TextEscaperUtil.isEscapePrefix("a\\####tbc", 1, 3));
    }
}