package dev.monogon.cue.lang.psi.impl;

import com.intellij.psi.PsiElement;
import dev.monogon.cue.lang.CueTypes;
import dev.monogon.cue.lang.psi.CueAttribute;
import org.jetbrains.annotations.NotNull;

public class CuePsiImplUtil {
    private CuePsiImplUtil() {}

    /**
     * The attribute name IDENTIFIER token of a CUE attribute element. It's the token after the initial "@"
     */
    @NotNull
    public static PsiElement getAttributeNameElement(@NotNull CueAttribute attribute) {
        PsiElement e = attribute.getFirstChild().getNextSibling();
        assert e.getNode().getElementType() == CueTypes.IDENTIFIER;
        return e;
    }

    /**
     * The string value of the attribute name element
     */
    @NotNull
    public static String getAttributeName(@NotNull CueAttribute attribute) {
        return getAttributeNameElement(attribute).getText();
    }
}
