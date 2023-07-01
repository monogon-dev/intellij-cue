// This is a generated file. Not intended for manual editing.
package dev.monogon.cue.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static dev.monogon.cue.lang.CueTypes.*;
import dev.monogon.cue.lang.psi.CueCompositeElementImpl;
import dev.monogon.cue.lang.psi.*;

public class CueDeclarationImpl extends CueCompositeElementImpl implements CueDeclaration {

  public CueDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CueVisitor visitor) {
    visitor.visitDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CueVisitor) accept((CueVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CueEllipsis getEllipsis() {
    return findChildByClass(CueEllipsis.class);
  }

  @Override
  @Nullable
  public CueLetClause getLetClause() {
    return findChildByClass(CueLetClause.class);
  }

  @Override
  @Nullable
  public CueAttribute getAttribute() {
    return findChildByClass(CueAttribute.class);
  }

}
