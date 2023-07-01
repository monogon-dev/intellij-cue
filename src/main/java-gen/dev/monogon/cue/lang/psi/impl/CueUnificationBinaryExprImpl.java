// This is a generated file. Not intended for manual editing.
package dev.monogon.cue.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static dev.monogon.cue.lang.CueTypes.*;
import dev.monogon.cue.lang.psi.*;

public class CueUnificationBinaryExprImpl extends CueExpressionImpl implements CueUnificationBinaryExpr {

  public CueUnificationBinaryExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull CueVisitor visitor) {
    visitor.visitUnificationBinaryExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CueVisitor) accept((CueVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CueExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CueExpression.class);
  }

  @Override
  @NotNull
  public CueExpression getLeft() {
    List<CueExpression> p1 = getExpressionList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public CueExpression getRight() {
    List<CueExpression> p1 = getExpressionList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
