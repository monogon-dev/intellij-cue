package dev.monogon.cue.lang.psi.escaper;

import com.intellij.openapi.util.ProperTextRange;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.LiteralTextEscaper;
import dev.monogon.cue.lang.psi.CueMultilineBytesLit;
import dev.monogon.cue.lang.psi.CueMultilineLiteral;
import dev.monogon.cue.lang.psi.CueSimpleBytesLit;
import dev.monogon.cue.lang.psi.CueStringLiteral;
import dev.monogon.cue.lang.util.TextEscaperUtil;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class CueStringLiteralEscaper extends LiteralTextEscaper<CueStringLiteral> {
    private int[] decodedOffsets = null;

    public CueStringLiteralEscaper(@NotNull CueStringLiteral host) {
        super(host);
    }

    @Override
    public boolean decode(@NotNull TextRange rangeInsideHost, @NotNull StringBuilder outChars) {
        ProperTextRange.assertProperRange(rangeInsideHost);

        var offsetsRef = new AtomicReference<int[]>();
        var result = TextEscaperUtil.parseLiteral(rangeInsideHost.substring(myHost.getText()),
                                                  outChars, offsetsRef,
                                                  myHost instanceof CueSimpleBytesLit || myHost instanceof CueMultilineBytesLit,
                                                  myHost.getEscapePaddingSize());
        decodedOffsets = offsetsRef.get();
        return result;
    }

    @Override
    public int getOffsetInHost(int offsetInDecoded, @NotNull TextRange rangeInsideHost) {
        if (offsetInDecoded >= decodedOffsets.length) {
            return -1;
        }

        var result = decodedOffsets[offsetInDecoded];
        if (result == -1) {
            return -1;
        }

        return Math.min(result, rangeInsideHost.getLength()) + rangeInsideHost.getStartOffset();
    }

    @Override
    public @NotNull TextRange getRelevantTextRange() {
        return myHost.getLiteralContentRange();
    }

    @Override
    public boolean isOneLine() {
        return !(myHost instanceof CueMultilineLiteral);
    }
}
