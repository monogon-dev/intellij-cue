// This is a generated file. Not intended for manual editing.
package dev.monogon.cue.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CueUnificationBinaryExpr extends CueExpression {

  @NotNull
  List<CueExpression> getExpressionList();

  @NotNull
  CueExpression getLeft();

  @Nullable
  CueExpression getRight();

}
