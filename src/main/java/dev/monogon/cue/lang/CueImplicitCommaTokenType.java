package dev.monogon.cue.lang;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.ILeafElementType;
import org.jetbrains.annotations.NotNull;

public class CueImplicitCommaTokenType extends IElementType implements ILeafElementType {
    public CueImplicitCommaTokenType() {
        super(",", CueLanguage.INSTANCE);
    }

    @Override
    public @NotNull ASTNode createLeafNode(@NotNull CharSequence leafText) {
        return new LeafPsiElement(this, leafText);
    }
}
