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

public class CueOperandImpl extends CueCompositeElementImpl implements CueOperand {

  public CueOperandImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CueVisitor visitor) {
    visitor.visitOperand(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CueVisitor) accept((CueVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CueExpression getExpression() {
    return findChildByClass(CueExpression.class);
  }

  @Override
  @Nullable
  public CueLiteral getLiteral() {
    return findChildByClass(CueLiteral.class);
  }

  @Override
  @Nullable
  public CueOperandName getOperandName() {
    return findChildByClass(CueOperandName.class);
  }

}
