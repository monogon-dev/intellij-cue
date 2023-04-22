package dev.monogon.cue.lang.psi.impl;

import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import dev.monogon.cue.lang.CueTypes;
import dev.monogon.cue.lang.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CuePsiImplUtil {
    private CuePsiImplUtil() {
    }

    /**
     * The attribute name IDENTIFIER token of a CUE attribute element. It's the token after the initial "@"
     */
    @NotNull
    public static PsiElement getAttributeNameElement(@NotNull CueAttribute attribute) {
        PsiElement e = attribute.getFirstChild().getNextSibling();
        assert e.getNode().getElementType() == CueTypes.IDENTIFIER || e.getNode().getElementType() == CueTypes.IDENTIFIER_PREDECLARED;
        return e;
    }

    /**
     * The string value of the attribute name element
     */
    @NotNull
    public static String getAttributeName(@NotNull CueAttribute attribute) {
        return getAttributeNameElement(attribute).getText();
    }

    public static boolean isOptionalFieldName(@NotNull CueLabelName name) {
        var parent = name.getParent();
        if (!(parent instanceof CueLabelExpr)) {
            return false;
        }

        var next = name.getNextSibling();
        return next != null && next.getNode().getElementType() == CueTypes.QMARK;
    }

    static Logger logger = Logger.getInstance(CuePsiImplUtil.class);

    public static ItemPresentation getPresentation(CueField field) {
//        logger.warn("getting representation for "+ field.getName());
        var name = field.getLabelList().stream().map(PsiElement::getText).reduce((x,y) -> x + "." + y);
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return name.orElse(null);
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                if (!name.isPresent()) {
                    return null;
                } else if (name.get().startsWith("#") || name.get().startsWith("_#")) {
                    return AllIcons.Nodes.Type;
                } else if (field.getExpression() instanceof CueStructLit){
                    //The syntax is json like
                    return AllIcons.Json.Object;
                } else if (field.getExpression() instanceof CueAliasExpr){
                    return AllIcons.Nodes.Alias;
                } else if (field.getExpression() instanceof CueListLit){
                    return AllIcons.Json.Array;
                } else if (field.getExpression() instanceof CueUnaryExpr) {
                    return AllIcons.Nodes.Function;
                } else {
                    return AllIcons.Nodes.Property;
                }
            }
        };
    }
}
