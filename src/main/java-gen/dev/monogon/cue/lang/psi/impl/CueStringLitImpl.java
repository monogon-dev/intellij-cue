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

public class CueStringLitImpl extends CueCompositeElementImpl implements CueStringLit {

  public CueStringLitImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CueVisitor visitor) {
    visitor.visitStringLit(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CueVisitor) accept((CueVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CueMultilineBytesLit getMultilineBytesLit() {
    return findChildByClass(CueMultilineBytesLit.class);
  }

  @Override
  @Nullable
  public CueMultilineStringLit getMultilineStringLit() {
    return findChildByClass(CueMultilineStringLit.class);
  }

  @Override
  @Nullable
  public CueSimpleBytesLit getSimpleBytesLit() {
    return findChildByClass(CueSimpleBytesLit.class);
  }

  @Override
  @Nullable
  public CueSimpleStringLit getSimpleStringLit() {
    return findChildByClass(CueSimpleStringLit.class);
  }

  @Override
  @Nullable
  public CueStringLit getStringLit() {
    return findChildByClass(CueStringLit.class);
  }

}
