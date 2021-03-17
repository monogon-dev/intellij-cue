package dev.monogon.cue.lang.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Helper to handle escaping of string literals.
 */
final public class CueEscaperUtil {
    private CueEscaperUtil() {
    }

    @NotNull
    public static String escapeCueString(@NotNull String content,
                                         boolean minimalEscaping, boolean escapeLinefeeds, boolean escapeInterpolation,
                                         boolean escapeSingleQuote, boolean escapeDoubleQuote,
                                         boolean tripleSingleQuoted, boolean tripleDoubleQuoted,
                                         int escapePaddingSize) {
        var backslash = "\\" + "#".repeat(escapePaddingSize);
        var out = new StringBuilder();

        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];

            if (c == '\\' && isEscapePrefix(content, i, escapePaddingSize)) {
                if (escapeInterpolation) {
                    out.append(backslash);
                }
                out.append('\\');
                continue;
            }

            if (c == '\"' && tripleSingleQuoted && hasPrefix(content, i, "'''")) {
                out.append("\\'");
                continue;
            }

            if (c == '"' && tripleDoubleQuoted && hasPrefix(content, i, "\"\"\"")) {
                out.append("\\\"");
                continue;
            }

            switch (c) {
                case (char)7: // <bell>
                    out.append(backslash).append("a");
                    break;
                case '\b': // <backspace>
                    out.append(backslash).append("b");
                    break;
                case '\f': // form feed
                    out.append(backslash).append("f");
                    break;
                case '\n': // line feed
                    if (escapeLinefeeds) {
                        out.append(backslash).append("n");
                    }
                    else {
                        out.append("\n");
                    }
                    break;
                case '\r': // carriage return
                    out.append(backslash).append("r");
                    break;
                case '\t': // tab
                    if (minimalEscaping) {
                        out.append('\t');
                    }
                    else {
                        out.append(backslash).append("t");
                    }
                    break;
                case (char)11: // vertical tab
                    if (minimalEscaping) {
                        out.append((char)11);
                    }
                    else {
                        out.append(backslash).append("v");
                    }
                    break;
                case '/':
                    if (!minimalEscaping) {
                        out.append(backslash);
                    }
                    out.append('/');
                    break;
                case '\'':
                    if (!minimalEscaping || escapeSingleQuote) {
                        out.append(backslash);
                    }
                    out.append('\'');
                    break;
                case '"':
                    if (!minimalEscaping || escapeDoubleQuote) {
                        out.append(backslash);
                    }
                    out.append('"');
                    break;
                default: // insert as-is, optionally unicode-encoded
                    if (c < 255 || minimalEscaping) {
                        out.append(c);
                    }
                    else if (c < 65535) { // 0xFFFF
                        out.append(backslash).append("u").append(String.format("%04x", (int)c));
                    }
                    else {
                        out.append(backslash).append("U").append(String.format("%08x", (int)c));
                    }
                    break;
            }
        }
        return out.toString();
    }

    /**
     * @param content           content to be decoded
     * @param out               where the decoded characters are appended
     * @param decodedOffsetsRef will be updated with the new decoded offsets
     * @param allowByteEscapes  if octal and hex byte escapes are allowed
     * @return if the decode operation was successful
     */
    // fixme handle unicode, octal, etc. escapes
    public static boolean parseLiteral(@NotNull CharSequence content,
                                       @NotNull StringBuilder out,
                                       @NotNull AtomicReference<int[]> decodedOffsetsRef,
                                       boolean allowByteEscapes,
                                       int escapePaddingSize) {
        var length = content.length();
        var offsets = new int[length + 1];
        decodedOffsetsRef.set(offsets);
        // reset to -1, i.e. out-of-range
        Arrays.fill(offsets, -1);

        // handle escapes
        final var outOffset = out.length();
        int i = 0;
        while (i < length) {
            var c = content.charAt(i);
            offsets[out.length() - outOffset] = i;
            offsets[out.length() - outOffset + 1] = i + 1;

            // not escaped
            if (!isEscapePrefix(content, i, escapePaddingSize)) {
                i++;
                out.append(c);
                continue;
            }

            // skip backslash and padding
            i++;
            i += escapePaddingSize;

            // incomplete escape
            if (i == content.length()) {
                return false;
            }

            // c is now the char after \ and padding chars
            c = content.charAt(i);
            i++;

            if (c == 'u' || c == 'U') {
                // 4-digit or 8-digit unicode escape
                var digits = c == 'u' ? 4 : 8;
                if (i + digits <= content.length()) {
                    var escaped = content.subSequence(i, i + digits);
                    try {
                        out.appendCodePoint(Integer.parseInt(escaped.toString(), 16));
                        i += digits;
                        continue;
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
            else if (allowByteEscapes && c == 'x') { // 2-digit hex escape of a byte in byte literal
                if (i + 2 <= content.length()) {
                    var escaped = content.subSequence(i, i + 2);
                    try {
                        out.append((char)Integer.parseInt(escaped.toString(), 16));
                        i += 2;
                        continue;
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
            else if (allowByteEscapes && isOctalEscapeValue(content, i - 1)) { // octal escape in byte literal
                if (i + 2 <= content.length()) {
                    var escaped = content.subSequence(i - 1, i + 2);
                    try {
                        out.append((char)Integer.parseInt(escaped.toString(), 8));
                        i += 2;
                        continue;
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                }
            }

            switch (c) {
                case 'a':
                    out.append((char)7);
                    break;
                case 'b':
                    out.append('\b');
                    break;
                case 'f':
                    out.append('\f');
                    break;
                case 'n':
                    out.append('\n');
                    break;
                case 'r':
                    out.append('\r');
                    break;
                case 't':
                    out.append('\t');
                    break;
                case 'v': // vertical tab
                    out.append((char)11);
                    break;
                // remaining inserted as-is
                case '/':
                case '\\':
                case '\'':
                case '"':
                    out.append(c);
                    break;
                default:
                    // unknown escape
                    out.append('\\');
                    out.append(c);
                    break;
            }
            offsets[out.length() - outOffset] = i;
        }
        return true;
    }

    /**
     * @return true if the next three characters are a valid value of an octal escape
     */
    private static boolean isOctalEscapeValue(@NotNull CharSequence chars, int index) {
        if (chars.length() < index + 3) {
            return false;
        }

        for (int i = 1; i < 3; i++) {
            var c = chars.charAt(index + i);
            if (c < '0' || c > '7') {
                return false;
            }
        }
        return true;
    }

    public static boolean isEscapePrefix(@NotNull CharSequence chars, int index, int paddingSize) {
        if (chars.charAt(index) != '\\') {
            return false;
        }

        for (int i = 0; i < paddingSize; i++) {
            var at = index + i + 1;
            if (at >= chars.length() || chars.charAt(at) != '#') {
                return false;
            }
        }

        // escaped char must not be a #
        return chars.length() > index + paddingSize + 1 && chars.charAt(index + paddingSize + 1) != '#';
    }

    public static boolean hasPrefix(@NotNull String chars, int index, @NotNull String expected) {
        if (chars.length() < index + expected.length()) {
            return false;
        }
        return expected.equals(chars.substring(index, index + expected.length()));
    }
}
